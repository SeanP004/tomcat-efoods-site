
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
                return $(target.parentNode);
            },

            html: function (html) {
                if (typeOf(html) === 'string') {
                    target.innerHTML = html;
                    return this;
                }
                return target.innerHTML;
            },

            append: function (o) {
                if (typeOf(o) === 'string') {
                    target.innerHTML = target.innerHTML + o;
                } else if (o instanceof Node || o instanceof Element) {
                    target.appendChild(o);
                } else {
                    target.innerHTML = target.innerHTML + o.html();
                }
                return this;
            },

            attr: function (attr, value) {
                if (value !== undefined) {
                    target.setAttribute(attr, value);
                    return this;
                }
                return target.getAttribute(attr);
            },

            val: function (value) {
                if (value !== undefined) {
                    target.value = value;
                    return this;
                }
                return target.value;
            },

            //
            // Events
            // --------------------

            on: function(event, fn) {
                var that = this;
                var listener = function (ev) {fn.call(that, ev);};
                (events[event] = events[event] || []).push(listener);
                target.addEventListener(event, listener);
                return that;
            },

            off: function(event) {
                events[event].forEach(function (ev) {
                    target.removeEventListener(event, ev);
                });
                delete events[event];
                return this;
            },

            onReady: function(readyFn) {
                if (target.readyState !== "loading") {
                    readyFn(target);
                } else {
                    target.addEventListener("DOMContentLoaded", function () {
                        readyFn(target);
                    });
                }
                return this;
            },

            //
            // CSS Classes
            // --------------------

            hasClass: function (className) {
                return (new RegExp('(^|\\s)' + className + '(\\s|$)')).test(target.className);
            },

            addClass: function (className) {
                if (!target.className) {
                    target.className = className;
                } else if (!this.hasClass(className)) {
                    target.className += ' ' + className;
                }
                return this;
            },

            removeClass: function (className) {
                var re, m, cn = target.className;
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
                return this;
            }

        }; // return object
    }; // constructor
}); // define Element
