
define('Search', ['Element', 'Elements', 'Ajax', 'FormData', 'Template'],
    function ($, $$, $http, $form, $tmpl) {
        'use strict';

        if (!$('.search-filters').elem()) {return;}

        var searchTabs    = $$('.search-filters .nav-tabs li a')
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
          ;

        function toggleAdvancedSearch(toggle) {
            if (!toggle || searchMore.hasClass('active')) {
                searchMore.removeClass('active');
                searchOpts.addClass('hidden');
            } else if (toggle) {
                searchMore.addClass('active');
                searchOpts.removeClass('hidden');
            }
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
            }
        });

        minPrice.on('change', function () {
            if (minPrice.val() >= maxPrice.val()) {
                // TODO show error
            }
            minPriceLabel.html(minPrice.val());
        });
        maxPrice.on('change', function () {
            if (minPrice.val() >= maxPrice.val()) {
                // TODO show error
            }
            maxPriceLabel.html(maxPrice.val());
        });



    }
);
