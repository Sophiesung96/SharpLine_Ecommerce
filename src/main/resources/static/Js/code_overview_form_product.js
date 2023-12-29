


$(document).ready(function () {
 $('#productList').on('change','.quantity-input',function () {
     updateSubtotalWhenQuantityChanged($(this));
     updateOrderAmount();
 });

    $('#productList').on('change','.price-input',function () {
        updateSubtotalWhenUnitPriceChanged($(this));
    });
    $('#productList').on('change','.cost-input',function () {
        updateOrderAmount();
    });
    $('#productList').on('change','.ship-input',function () {
        updateOrderAmount();
    });

});


function updateSubtotalWhenQuantityChanged(input){
quantityValue=input.val();
rowNumber=input.attr('rowNumber');
priceField=$('#price'+rowNumber);
priceValue=parseFloat(priceField.val());
newSubtotal=parseFloat(quantityValue)*priceValue.toFixed(2);
subTotal=$('#subtotal'+rowNumber);
subTotal.val(newSubtotal);
}

function updateSubtotalWhenUnitPriceChanged(input){
    priceValue=input.val();
    rowNumber=input.attr('rowNumber');
    quantityField=$('#quantity'+rowNumber);
    quantityValue=quantityField.val();
    newSubtotal=parseFloat(quantityValue)*parseFloat(priceValue.toFixed(2));
    subTotal=$('#subtotal'+rowNumber);
    subTotal.val(newSubtotal);
}
//this function is to integrate the changes made in product html with overview html page
function updateOrderAmount() {
    totalCost = 0.0;
    //Product Cost changed
    $('.cost-input').each(function () {
        var costInputField = $(this);
        var rowNumber = costInputField.attr('rowNumber');
        var quantityValue = $('#quantity' + rowNumber).val();
        var productCost = costInputField.val();
        totalCost += productCost * parseFloat(quantityValue);
        totalCost = parseFloat(totalCost.toFixed(2));
        $('#ProductCost').val(totalCost);
        console.log('Final totalCost:', totalCost);
    });

    //Subtotal changed
    orderSubtotal = 0.0;
    Subtotal=0.0;

    $('.subtotal-input').each(function () {
        var productSubTotal = $(this);
        var subtotalValue = parseFloat(productSubTotal.val());
        if (!isNaN(subtotalValue)) {
            Subtotal += subtotalValue;
            orderSubtotal=Subtotal.toFixed(2);
        }
    });
    console.log("SubTotal=:"+orderSubtotal)
    $('#SubTotal').val(orderSubtotal);

   //Shipping Cost changed
    OrderShipping=0.0;
    $('.ship-input').each(function () {
        var productShipping = $(this);
        var subtotalValue = parseFloat(productShipping.val());
        if (!isNaN(subtotalValue)) {
            OrderShipping+= subtotalValue;
        }
    });

    $('#ShippingCost').val(OrderShipping);

    //Total changed
    OrderTax=0.0;
    tax=$('#Tax').val();
    OrderTax=parseFloat(tax);
    Total=parseFloat(orderSubtotal+OrderShipping+OrderTax);
    orderTotal=Total.toFixed(2);
    $('#Total').val(orderTotal);
};








