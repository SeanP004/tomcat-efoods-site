
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
