$(document).ready(function () {
    $('.LinkMinus').on('click', function (event) {
        event.preventDefault();
        var productId = $(this).attr('pid');
        // input value
        var quantityInput = $("#quantity" + productId);
        var newQuantity = parseInt(quantityInput.val()) - 1;
        if (newQuantity > 0) {
            quantityInput.val(newQuantity);
        } else {
            alert("Minimum amount is 1");
        }
    });

    $('.LinkPlus').on('click', function (event) {
        event.preventDefault();
        var productId = $(this).attr('pid');
        var quantityInput = $("#quantity" + productId);
        var newQuantity = parseInt(quantityInput.val()) + 1;
        if (newQuantity <= 5) {
            quantityInput.val(newQuantity);
        } else {
            alert("Maximum amount is 5");
        }
    });
});
