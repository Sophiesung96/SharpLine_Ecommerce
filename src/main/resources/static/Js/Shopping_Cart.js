$(document).ready(function () {
    $('#buttonAdd2Cart').click(function () {
        addToCart();
    });
})


function addToCart(){
quantity=$("#quantity"+ProductId).val();
Url="/cart/add/"+ProductId+"/"+quantity;
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
})
}