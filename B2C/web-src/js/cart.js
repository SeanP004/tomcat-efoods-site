
define('Cart', ['Element', 'Elements', 'Ajax', 'FormData'], function ($, $$, $http, $form) {
    'use strict';

    var api = ContextPath + '/api/cart';

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
