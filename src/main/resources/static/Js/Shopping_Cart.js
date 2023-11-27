$(document).ready(function () {
    $('.LinkPlus').click(function (event) {
        event.preventDefault();
        increaseProductQuantity($(this));
    });

    $('.LinkMinus').click(function (event) {
        event.preventDefault();
       decreaseProductQuantity($(this));
    });


    $('.link-remove').click(function (event) {
        event.preventDefault();
          removeProduct($(this));
    });
});



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

function updateTotal() {
    total = 0.0;
    productCount = 0;

    $('.subTotal').each(function (index, element) {
        productCount++;
        total += parseFloat(element.innerHTML);
    });

    if (productCount < 1) {
        showEmptyCart();
    } else {
        $('#total').text(total);
    }

    console.log("Total updated to: ", total);
    console.log("ProductCount: ", productCount);// Add this line for debugging
}



function removeProduct(link){
    link = $(link);
    removeURL = link.attr("href");
    rowNumber = link.attr("rowNumber");
    $.ajax({
        type:"DELETE",
        url:removeURL,
        beforeSend:function (xhr) {
            xhr.setRequestHeader("_csrf.headerName","_csrf.token");
        }
    }).done(function (response) {
        removeProductHTML(rowNumber);
        updateTotal();
        updateCountNumber();
        alert(response);
    }).fail(function () {
        alert("Error while removing product from shopping cart.");
    });
}

function removeProductHTML(rowNumber){
    $('#row'+rowNumber).remove();
}

function updateCountNumber(){
    $('.div-Count').each(function (index,element) {
        element.innerHTML=' '+(index+1);
    })
}

function showEmptyCart() {
    var cartItemCount = $('.subTotal').length;
    console.log("Number of items in the cart:", cartItemCount);
    if (cartItemCount < 1) {
        console.log("Hiding cart elements");
        $('#sectionTotal').hide();
        $('#noShoppingContent').removeClass('d-none')
        $('.hiddenContent').hide();
    }
}










