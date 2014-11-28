
(function (global) {
	'use strict';

    // Basics
    // --------------------

	function typeOf(val) {
        var type = typeof val;
        if (type === 'object') {
            if (!val) {return 'null';}
            if (Array.isArray(val)) {return 'array';}
        }
        return type;
    };

    function forEach(obj, fun, ctx) {
        if (Array.isArray(obj)) {
            obj.forEach(fun, ctx);
        } else {
            for (var prop in obj) {
                if (!obj.hasOwnProperty(prop)) {continue;}
                fun.call(ctx, obj[prop], prop, obj);
            }
        }
    }

    // Module Loader
    // ----------------------------------------

    var define = (function ModuleLoader() {

        var modules = {};
        var depends = {};

        function init(module) {
            var b = true;
            forEach(module.deps, function (dep) {
                if (!modules[dep]) {
                    b = false;
                } else if (!modules[dep].done && !init(modules[dep])) {
                    b = false;
                }
            });
            if (b) {
                module.result = module.func.apply(null, module.deps.map(function (dep) {
                    return modules[dep].result;
                }));
                module.done = true;
            }
            return b;
        }

        function dependsOn(module, name) {
            var b = false;
            forEach(module.deps, function (dep) {
                if (name === dep) {
                    b = true;
                } else if (modules[dep] && dependsOn(modules[dep], name)) {
                    b = true;
                }
            });
            return b;
        }

        function breakCycle(name, deps) {
            return deps.filter(function (dep) {
                if (dep === name) {return false;}
                if (modules[dep] && dependsOn(modules[dep], name)) {
                    return false;
                }
                return true;
            });
        }

        function resolve(name) {
            forEach(depends[name], function (wait) {
                init(modules[wait]);
                resolve(wait);
            });
        }

        return function (name, deps, func) {
            var module = modules[name] = {
                deps: breakCycle(name, deps || {}),
                func: func,
                done: false,
            };
            forEach(module.deps, function (dep) {
                var dlist = depends[dep];
                if (!dlist) {
                    depends[dep] = [];
                }
                depends[dep].push(name);
            });
            init(module);
            resolve(name);
        };
    }());

    global.typeOf = typeOf;
    global.forEach = forEach;
    global.define = define;

}(window));

define('Ajax', ['Element'], function ($) {
	'use strict';

    function Ajax(method, url, data) {
        var xhr = new XMLHttpRequest();
        var successCallbacks = [];
        var errorCallbacks = [];

        function callCallbacks(callbacks, response) {
            while (callbacks.length) {
                callbacks.shift()(response);
            }
        }

        xhr.open((method || 'GET').toUpperCase(), url, true);
        xhr.responseType = 'text';
        xhr.onload = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    callCallbacks(successCallbacks, xhr.responseText);
                } else {
                    callCallbacks(errorCallbacks, xhr.statusText);
                }
            }
        };
        xhr.onerror = function () {
            callCallbacks(errorCallbacks, xhr.statusText);
        };

        // Force encoding to be url encoded instead of multipart/formdata
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send(data);

        return {
            onSuccess: function (callback) {
                if (xhr.readyState !== 4) {
                    successCallbacks.push(callback);
                } else {
                    callback(xhr.responseText);
                }
                return this;
            },
            onError: function (callback) {
                if (xhr.readyState !== 4) {
                    errorCallbacks.push(callback);
                } else {
                    callback(xhr.statusText);
                }
                return this;
            }
        };
    }

    Ajax.get = function (url) {
        return Ajax('GET', url);
    }

    Ajax.post = function (url, data) {
        return Ajax('POST', url, data);
    }

    return Ajax;
});

define('Cart', ['Element', 'Elements', 'Ajax', 'FormData'], function ($, $$, $http, $form) {
    'use strict';

    var api = '/eFoods/api/cart';

    function showError(error) {
        $('.error')
            .html(error)
            .addClass('alert')
            .addClass('alert-danger')
            .removeClass('hidden');
    }

    function updateView(request) {
        request
            .onError(showError)
            .onSuccess(function (data) {
                var doc = (new DOMParser()).parseFromString(data, "application/xml");
                var error = $('error', doc);

                if (!error.elem()) {
                    $('.navbar .cart .badge').html($('cart', doc).attr('size'));
                    $$('item', doc).each(function (item) {
                        var number = item.attr('number');
                        var quantity = item.parent().attr('quantity');
                        var itemCard = $('.cart-btn[data-item="' + number + '"] .badge');
                        if (!!itemCard.elem()) {
                            itemCard.html(quantity);
                        }
                    });
                } else {
                    showError(error.elem().childNodes[0].nodeValue);
                }
            });
    }

    return {
        updateView: function () {
            updateView($http.get(api));
        }
    };
});

define('CartBtn', ['Element', 'Elements', 'Ajax', 'FormData', 'Cart', 'CartTable'],
    function ($, $$, $http, $form, $cart, $ct) {
        'use strict';

        var api = '/eFoods/api/cart';

        function addToCart(number) {
            var form = $form();
            var request;
            form.append('action', 'add');
            form.append('number', number);
            request = $http.post(api, form.encode());
            request.onSuccess(function () {
                $ct.updateView();
            });
            $cart.updateView(request);
        }

        function attachEvents() {
            $$('.cart-btn').on('click', function (ev) {
                addToCart(this.attr('data-item'));
            });
        }

        $(document).onReady(attachEvents);

        return {
            updataEventHandlers: attachEvents
        };
    }
);

define('CartTable', ['Element', 'Elements', 'Ajax', 'FormData', 'Cart'], function ($, $$, $http, $form, $cart) {
    'use strict';

    var api = '/eFoods/api/cart';

    function showError(error) {
        $('.error')
            .html(error)
            .addClass('alert')
            .addClass('alert-danger')
            .removeClass('hidden');
    }

    function bulkRequest() {
        var bulk = {};
        return {
            setQuantity: function (num, qty) {
                bulk[num] = qty;
                return this;
            },
            getForm: function () {
                var form = $form();
                var number = [];
                var quantity = [];

                forEach(bulk, function (qty, num) {
                    number.push(num);
                    quantity.push(qty);
                });

                form.append('action',   'bulk');
                form.append('number',   number.join(';'));
                form.append('quantity', quantity.join(';'));
                return form;
            }
        };
    }

    function updateView() {
        $http
            .get(location.href)
            .onError(showError)
            .onSuccess(function (data) {
                var doc = (new DOMParser()).parseFromString(data, "text/html");
                var error = $('.error', doc).html();

                if (!error) {
                    $('.cart-view').html($('.cart-view', doc).html());
                    $('.error').html('').addClass('hidden');
                    attachEvents();
                } else {
                    showError(error);
                    $('.success').html('').addClass('hidden');
                }
            });
    }

    function doBulkUpdate(form) {
        $http
            .post(api, form.encode())
            .onError(showError)
            .onSuccess(function (data) {
                var doc = (new DOMParser()).parseFromString(data, "application/xml");
                var error = $('error', doc);

                if (!error.elem()) {
                    var status = $('status', doc).elem().childNodes[0].nodeValue;
                    $('.navbar .cart .badge').html($('cart', doc).attr('size'));
                    $('.success').html(status).removeClass('hidden');
                    updateView();
                    $cart.updateView();
                } else {
                    showError(error.elem().childNodes[0].nodeValue);
                    $('.success').html('').addClass('hidden');
                }
            });
    }

    function attachEvents() {
        $$('.cart-table .remove-btn').on('click', function () {
            doBulkUpdate(bulkRequest().setQuantity(this.attr('data-id'), 0).getForm());
        });
        $(".cart-update-btn").on('click', function () {
            doBulkUpdate((function (bulk) {
                $$('.cart-table .quantity-field').each(function (elem) {
                    bulk.setQuantity(elem.attr('data-id'), elem.val());
                });
                return bulk.getForm();
            })(bulkRequest()));
        });
    }

    $(document).onReady(attachEvents);

    return {
        updateView: updateView
    };
});

define('Element', [], function () {
	'use strict';

    return function $(query, docNode) {
        var target;
        var events = {};

        if (query === document) {
            target = document;
        } else if (query instanceof Element || query instanceof Node) {
            target = query;
        } else {
            target = (docNode || document).querySelector(query);
        }

        return {

            //
            // DOM
            // --------------------

            elem: function () {
                return target;
            },

            parent: function () {
                return !!target ? $(target.parentNode) : null;
            },

            html: function (html) {
                if (typeOf(html) === 'string') {
                    if (!!target) {
                        target.innerHTML = html;
                    }
                    return this;
                }
                return !!target ? target.innerHTML : null;
            },

            append: function (o) {
                if (!!target) {
                    if (typeOf(o) === 'string') {
                        target.innerHTML = target.innerHTML + o;
                    } else if (o instanceof Node || o instanceof Element) {
                        target.appendChild(o);
                    } else {
                        target.innerHTML = target.innerHTML + o.html();
                    }
                }
                return this;
            },

            attr: function (attr, value) {
                if (value !== undefined) {
                    if (!!target) {
                        target.setAttribute(attr, value);
                    }
                    return this;
                }
                return !!target ? target.getAttribute(attr) : null;
            },

            val: function (value) {
                if (value !== undefined) {
                    if (!!target) {
                        target.value = value;
                    }
                    return this;
                }
                return !!target ? target.value : null;
            },

            //
            // Events
            // --------------------

            on: function(event, fn) {
                if (target) {
                    var that = this;
                    var listener = function (ev) {fn.call(that, ev);};
                    (events[event] = events[event] || []).push(listener);
                    target.addEventListener(event, listener);
                }
                return that;
            },

            off: function(event) {
                if (target) {
                    events[event].forEach(function (ev) {
                        target.removeEventListener(event, ev);
                    });
                    delete events[event];
                }
                return this;
            },

            onReady: function(readyFn) {
                if (target) {
                    if (target.readyState !== "loading") {
                        readyFn(target);
                    } else {
                        target.addEventListener("DOMContentLoaded", function () {
                            readyFn(target);
                        });
                    }
                }
                return this;
            },

            //
            // CSS Classes
            // --------------------

            hasClass: function (className) {
                return !!target ? (new RegExp('(^|\\s)' + className + '(\\s|$)')).test(target.className) : false;
            },

            addClass: function (className) {
                if (target) {
                    if (!target.className) {
                        target.className = className;
                    } else if (!this.hasClass(className)) {
                        target.className += ' ' + className;
                    }
                }
                return this;
            },

            removeClass: function (className) {
                var re, m, cn;
                if (target) {
                    cn = target.className;
                    if (cn) {
                        if (cn === className) {
                            target.className = '';
                        } else {
                            re = new RegExp('(^|\\s)' + className + '(\\s|$)');
                            m = cn.match(re);
                            if (m && m.length == 3) {
                                target.className = cn.replace(re, (m[1] && m[2]) ? ' ' : '');
                            }
                        }
                    }
                }
                return this;
            }

        }; // return object
    }; // constructor
}); // define Element

define('Elements', ['Element'], function ($) {
	'use strict';

    return function (query, docNode) {
        var targets;
        var elements;

        function toArray(o) {
            var a = [];
            var i = o.length;
            a.length = i;
            while (i--) {a[i] = o[i];}
            return a;
        }

        if (query === document) {
            targets = [document];
        } else if (query instanceof Element) {
            targets = [query];
        } else if (query instanceof HTMLCollection || query instanceof NodeList) {
            targets = toArray(query);
        } else {
            targets = toArray((docNode || document).querySelectorAll(query));
        }

        elements = targets.map(function (target) {
            return $(target);
        });

        return {

            elements: function () {
                return elements;
            },

            concat: function (elems) {
                elements.concat(elems.elements());
                return this;
            },

            each: function (callback) {
                elements.forEach(callback);
                return this;
            },

            //
            // DOM
            // --------------------

            elems: function () {
                return targets;
            },

            html: function (html) {
                if (typeOf(html) === 'string') {
                    return this.each(function (elem) {
                        elem.html(html);
                    });
                }
                return elements.map(function (elem) {
                    return elem.html();
                });
            },

            append: function (o) {
                return this.each(function (elem) {
                    elem.append(o);
                });
            },

            attr: function (attr, value) {
                if (value !== undefined) {
                    return this.each(function (elem) {
                        elem.attr(attr, value);
                    });
                }
                return elements.map(function (elem) {
                    return elem.attr(attr);
                });
            },

            //
            // Events
            // --------------------

            on: function(event, fn) {
                return this.each(function (elem) {
                    elem.on(event, fn);
                });
            },

            off: function(event) {
                return this.each(function (elem) {
                    elem.off(event);
                });
            },

            onReady: function() {
                return this.each(function (elem, i) {
                    elem.onReady(arguments[i]);
                });
            },

            //
            // CSS Classes
            // --------------------

            hasClass: function (className) {
                return elements.map(function (elem) {
                    return elem.hasClass(className);
                });
            },

            addClass: function (className) {
                return this.each(function (elem) {
                    elem.addClass(className);
                });
            },

            removeClass: function (className) {
                return this.each(function (elem) {
                    elem.removeClass(className);
                });
            }

        }; // return object
    }; // constructor
}); // define Elements

//
// Implementation of the FormData-like interface
// for handling WWW URL Encoded Form data.
//
define('FormData', ['Element', 'Elements'], function ($, $$) {
    return function (form) {
        'use strict';

        var data = {};

        function append(name, value) {
            data[name] = value;
        }

        if (form) {
            form = $(form);
            $$('select, input, submit', form.elem())
                .each(function (input) {
                    append(input.attr('name'), input.val());
                });
        }

        return {
            append: append,
            encode: function () {
                var out = [];
                forEach(data, function (value, name) {
                    out.push(encodeURIComponent(name)
                         + '=' + encodeURIComponent(value));
                })
                return out.join('&');
            }
        };
    };
});

define('Search', ['Element', 'Elements', 'Ajax', 'FormData', 'Template', 'CartBtn'],
    function ($, $$, $http, $form, $tmpl, $cartbtn) {
        'use strict';

        if (!$('.search-filters').elem()) {return;}

        var searchUri     = '/eFoods/browse?'
          , searchTabs    = $$('.search-filters .nav-tabs li a')
          , searchMore    = $('.search-filters .nav-tabs li a[data-id="more"]').parent()
          , searchOpts    = $('.search-options')
          , searchTerm    = $('input[name="searchTerm"]')
          , category      = $('select[name="catId"]')
          , orderBy       = $('select[name="orderBy"]')
          , minPrice      = $('input[name="minPrice"]')
          , maxPrice      = $('input[name="maxPrice"]')
          , minPriceLabel = $('input[name="minPrice"] + span')
          , maxPriceLabel = $('input[name="maxPrice"] + span')
          , closeBtn      = $('.search-options .close-btn .btn')
          , submit        = $('.search-box button[type="submit"]')
          , searchForm    = $('form[role="search"]')
          , searchResults = $('.catalog')
          ;

        function toggleAdvancedSearch(toggle) {
            if (!toggle || searchMore.hasClass('active')) {
                searchMore.removeClass('active');
                searchOpts.addClass('hidden');
                if (toggle) {
                    searchTabs.each(function (el) {
                        if (el.attr('data-id') === category.val()) {
                            el.parent().addClass('active');
                        }
                    });
                }
            } else if (toggle) {
                searchMore.addClass('active');
                searchOpts.removeClass('hidden');
            }
        }

        function showError(error) {
            $('.error')
                .html(error)
                .addClass('alert')
                .addClass('alert-danger')
                .removeClass('hidden');
        }

        function updateSearchResults() {
            var form = $form(searchForm.elem());
            window.history.pushState(null, $('title').html(), searchUri + form.encode());
            $http
                .get(searchUri + form.encode())
                .onError(showError)
                .onSuccess(function (data) {
                    var doc = (new DOMParser()).parseFromString(data, "text/html");
                    var error = $('.error', doc).html();

                    if (!error) {
                        searchResults.html($('.catalog', doc).html());
                        $cartbtn.updataEventHandlers();
                        $('.error').html('').addClass('hidden');
                    } else {
                        showError(error);
                    }
                });
        }

        closeBtn.on('click', function () {
            toggleAdvancedSearch();
        });

        searchTabs.on('click', function () {
            var parent = this.parent();
            searchTabs.each(function (el) {
                if (el.attr('data-id') === 'more') {return;}
                el.parent().removeClass('active');
            });
            toggleAdvancedSearch(this.attr('data-id') === 'more');
            if (this.attr('data-id') !== 'more') {
                parent.addClass('active');
                category.val(this.attr('data-id'));
                updateSearchResults();
            }
        });

        searchTerm.on('keyup', updateSearchResults);
        category.on('change', updateSearchResults);
        orderBy.on('change', updateSearchResults);
        minPrice.on('change', function () {
            minPriceLabel.html(minPrice.val());
            updateSearchResults();
        });
        maxPrice.on('change', function () {
            maxPriceLabel.html(maxPrice.val());
            updateSearchResults();
        });

    }
);

define('Template', ['Ajax', 'Element', 'Elements'], function (Ajax, $, $$) {
    'use strict';

    var compiler;
    var templates   = {};
    var paramRegExp = /\{\{([^\{\}]*)\}\}/g; // {{variable}}
    var inclRegExp  = /\[\[([^\[\]]*)\]\]/g; // [[urladdress]]

    function Templater(regexp) {
        function canReplace(repl) {
            var type = typeOf(repl);
            return type === 'string' || type === 'number' || type === 'boolean';
        }
        return function (str, obj, empty) {
            str = str.replace(regexp, function (a, b) {
                return canReplace(obj[b]) ? obj[b] : a;
            });
            return canReplace(empty) ? str.replace(regexp, empty) : str;
        };
    }

    function Compiler(templates) {
        var strTemplater  = Templater(paramRegExp);
        var ajaxTemplater = Templater(inclRegExp);

        function getDependencies(str) {
            var matches = str.match(inclRegExp);
            if (!matches) {return [];}
            return matches.map(function (match) {
                return match.replace(inclRegExp, '$1');
            });
        }
        function getTemplates(str) {
            var templateSet = {};
            getDependencies(str).forEach(function (name) {
                templateSet[name] = templates[name];
            })
            return templateSet;
        }
        return function (str, obj) {
            var newstr = ajaxTemplater(str, getTemplates(str));
            while (newstr !== str) {
                str = newstr;
                newstr = ajaxTemplater(str, getTemplates(str));
            }
            str = ajaxTemplater(str, getTemplates(str), '');
            return strTemplater(str, obj, '');
        };
    }

    $(document).onReady(function () {
        $$('script[type="text/template"]').each(function (el) {
            templates[el.attr('src')] = el.html();
        });
    });

    compiler = Compiler(templates);

    return {
        compile: compiler,
        compileTemplate: function (url, obj) {
            return compiler(templates[url], obj);
        },
        addTemplate: function (url, data) {
            templates[url] = data;
        },
        addTemplateSrc: function (url, callback) {
            var that = this;
            Ajax.get(url).onSucesss(function (data) {
                that.addTemplate(url, data);
                callback(data);
            });
        },
        getTemplate: function (url) {
            return templates[url];
        }
    };
});
