
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
