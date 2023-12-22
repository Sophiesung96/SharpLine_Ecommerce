$(document).ready(function () {
 $('#products').on('click','#linkAddProduct',function (e) {
     e.preventDefault();
     link=$(this);
     url=link.attr('href');
    $('#addProductModal').on('shown.bs.modal',function () {
        $(this).find("iframe").attr('src',url);
     })
     var modal = new bootstrap.Modal(document.getElementById('addProductModal'));
     modal.show();


 });
})

function addProduct(productId,productName){
    var myModal = bootstrap.Modal.getOrCreateInstance(document.getElementById('addProductModal'));
    myModal.hide();
   // alert("The product is not added!")
    getShippingCost(productId);
};

function getShippingCost(productId){
  var selectedcountry=$('#country option:selected');
  countryId=selectedcountry.val();
  var state=$('#state').val();
  //there's no state value
  if(state.length==0){
      state=$('#city').val();
  };
  alert("State:"+state);
}


//check for the existence of a product
//before it's being added
function  isProductAlreadyAdded(productId){
    ProductExits=false;
    $('.hiddenProductId').each(function (e) {
        aProductId=$(this).val();
        if(aProductId==productId){
            ProductExits=true;
            return;
        }
    })
    return ProductExits;
}




