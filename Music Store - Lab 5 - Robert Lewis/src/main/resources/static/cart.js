(function() {
    'use strict';
    
    
    $(function () {
        var cart = new Vue({
            el: '#vuediv',
            data: {
              entries: []
            },
            computed: {
              totalPrice: function () {
            	var total = 0;
            	this.entries.forEach(entry => total += entry.qty * entry.product.price);
            	return total;
              }
            }
          })

        $.get("cartEntries")
        .done(function (entries) {
            cart.entries = entries;
        });

        $('body').on('submit', '.update-qty-form', function(event) {
            // Don't do normal form submission
            event.preventDefault();

            // Get productCode from row, and qty from .qty-input field in same row
            var row = $(event.target).closest("tr");
            var productCode = row.attr("productCode");
            var qty = row.find(".qty-input").val();

            $.post( "cartEntries", {
                productCode: productCode,
                qty: qty
            })
            .done(function () {
                if (qty == 0) {
                    cart.entries = cart.entries.filter(entry => entry.product.code != productCode);
                }
                else {
                	var entry = cart.entries.find(entry => entry.product.code == productCode);
                	entry.qty = qty;
                }
            })
            .fail(function (err) {
                console.log("Error", err)
            });
        });
    });
}());
