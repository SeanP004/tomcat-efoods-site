
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
