
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
