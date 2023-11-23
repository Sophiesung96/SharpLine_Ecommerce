$(document).ready(function () {
    $('.LinkPlus').click(function (event) {
        event.preventDefault();
        increaseProductQuantity($(this));
    });

    $('.LinkMinus').click(function (event) {
        event.preventDefault();
       decreaseProductQuantity($(this));
    });

    $('#buttonAdd2Cart').click(function (event) {
        event.preventDefault();
        addToCart();
    });
});

function addToCart(){
    var productId =$('.LinkPlus').attr('pid');
    var Productquantity=$("#quantity"+productId).val();
   var  Url="/cart/add/"+productId+"/"+Productquantity;
    $.ajax({
        type:"POST",
        url:Url,
        beforeSend:function (xhr) {
            xhr.setRequestHeader("_csrf.headerName","_csrf.token");
        }
    }).done(function (response) {
        alert(response);
    }).fail(function () {
        alert("Error while adding product to shopping cart.");
    });
};

//increase product quantity from shopping cart
function increaseProductQuantity(link) {
    var productIdIncrease = link.attr('pid');
    var quantityInputIncrease = $('#quantity' + productIdIncrease);
    var updatedQuantityIncrease = parseInt(quantityInputIncrease.val()) + 1;

    if (updatedQuantityIncrease <= 5) {
        quantityInputIncrease.val(updatedQuantityIncrease);
        updateProductQuantity(productIdIncrease, updatedQuantityIncrease);
    } else {
        alert("Maximum amount is 5");
    }
}
//decrease product quantity from shopping cart
function decreaseProductQuantity(link) {
    var productIdDecrease = link.attr('pid');
    var quantityInputDecrease = $('#quantity' + productIdDecrease);
    var currentQuantityDecrease = parseInt(quantityInputDecrease.val());

    // Ensure the current quantity is greater than 1 before decrementing
    if (currentQuantityDecrease > 1) {
        var updatedQuantityDecrease = currentQuantityDecrease - 1;
        quantityInputDecrease.val(updatedQuantityDecrease);
        updateProductQuantity(productIdDecrease, updatedQuantityDecrease);
    } else {
        alert("Minimum amount is 1");
    }
}

function updateProductQuantity(productId,Quantity){
    var Url="/cart/update/"+productId+"/"+Quantity;
    $.ajax({
        type:"POST",
        url:Url,
        beforeSend:function (xhr) {
            xhr.setRequestHeader("_csrf.headerName","_csrf.token");
        }
    }).done(function (UpdatedSubtotal) {
        updateSubTotal(UpdatedSubtotal,productId);
        updateTotal();
    }).fail(function () {
        alert("Error while updating product quantity.");
    });
}


function updateSubTotal(UpdatedSubtotal, productId){
    $('#subtotal'+productId).text(UpdatedSubtotal);
}

function updateTotal(){
 total=0.0;
 $('.subTotal').each(function (index,element) {
     total+=parseFloat(element.innerHTML);
 })
    $('#total').text(total);
}








