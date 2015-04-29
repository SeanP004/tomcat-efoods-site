
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
            if (b && !module.done) {
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
