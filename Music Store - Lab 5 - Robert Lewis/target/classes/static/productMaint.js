(function() {
    'use strict';

    
    $(function () {
        var app = new Vue({
              el: '#vuediv',
              data: {
                products: []
              }
            })

        $.get("productMaintenance")
        .done(function (products) {
            products.forEach(function (product) {
                product.editLink = "editProduct?productCode=" + product.code;
            });

            app.products = products;
        });

        $('body').on('click', '.delete-product-link', function(event) {
            // Don't do normal form submission
            event.preventDefault();

            // Get productCode from row
            var row = $(event.target).closest("tr");
            var productCode = row.attr("productCode");
            var confirmed = window.confirm(
                    "Are you sure you want to delete product " + productCode + "?");

            if (!confirmed) {
                return;
            }

            $.post( "deleteProduct", {
                productCode: productCode
            })
            .done(function () {
                app.products = app.products.filter(product => product.code != productCode);
            })
            .fail(function (err) {
                console.log("Error", err)
            });
        });
    });
}());
