
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
