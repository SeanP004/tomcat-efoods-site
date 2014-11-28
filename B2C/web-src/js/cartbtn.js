
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
