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
    getshippingCost(productId);
};

function getshippingCost(productId){
    selectedCountry=$('#selectCountry option:selected');
    CountryId=selectedCountry.val().split('-')[0];
    console.log(CountryId)
    state=$('#state').val();
    console.log(state)
    if(state.length==0){
        state=$('#city').val();
    }
    params={productId:productId,country_id:CountryId,state:state}
    $.ajax({
        type:'POST',
        url:"/get_shipping_cost",
        data: JSON.stringify(params),
        dataType: 'text',
        contentType: "application/json"

    }).done(function (shippingCost) {
        getProductInfo(productId,shippingCost)
    }).fail(function (exception) {
        alert(exception)
    });
}

function getProductInfo(productId,shippingCost){
    url="/products/get/"+productId;
  $.get(url,function (productJson) {
      console.log(productJson);
     productName=productJson.name;
     MainImage=productJson.mainImagePath;
     productCost=productJson.cost;
     productPrice=productJson.price;
    console.log(productName+" "+MainImage+productCost+productPrice+shippingCost);
   html=insertnewProduct(productId,productName,MainImage,productCost,productPrice,shippingCost);
   $('#productList').append(html);

  }).fail(function () {
      alert("The server has encountered an error! Please try again");
  })
}

function insertnewProduct(productId,productName,mainImagePath,productCost,productPrice,shippingCost){
    nextcount=$('.hiddenProductId').length+1;
    qunantityId='quantity'+nextcount;
    priceId='price'+nextcount;
    SubtotalId='subtotal'+nextcount;
    htmlCode=`<div class="border rounded p-1">
                <input type="hidden" value="${productId}" name="productId" class="hiddenProductId"/>
                <div class="row" mt-2>
                    <div class="col-1">
                        ${nextcount}
                    </div>
                    <div class="col-3">
                        <img src="${mainImagePath}" class="img-fluid"/>
                    </div>
                </div>

                <div class="row m-2">
                    <b>${productName}</b>
                </div>

                <div class="row mt-2">
                    <div class="col mt-2">
                        <table>
                            <tr>
                                <td>Product Cost:</td>
                                <td>
                                    <input type="text" required class="form-control cost-input"
                                    value="${productCost}"rowNumber="${nextcount}" style="max-width: 140px;"/>
                                </td>

                            </tr>

                            <tr>
                                <td>Quantity:</td>
                                <td>
                                    <input type="number" step="1" min="1" max="5" required class="form-control quantity-input"
                                    id="${qunantityId}" rowNumber="${nextcount}" value="1" style="max-width: 140px;"/>
                                </td>

                            </tr>

                            <tr>
                                <td>Unit Price:</td>
                                <td>
                                    <input type="text"  required class="form-control price-input"
                                        id="${priceId}" rowNumber="${nextcount}" value="${productPrice}" style="max-width: 140px;"/>
                                </td>

                            </tr>

                            <tr>
                                <td>SubTotal:</td>
                                <td>
                                    <input type="text"  readonly class="form-control subtotal-input"
                                      id="${SubtotalId}" value="${productPrice}" style="max-width: 140px;"/>
                                </td>

                            </tr>

                            <tr>
                                <td>Shipping Cost:</td>
                                <td>
                                    <input type="text"  required class="form-control ship-input"
                                         value="${shippingCost}" style="max-width: 140px;"/>
                                </td>

                            </tr>



                        </table>
                    </div>

                </div>
            </div>`
    return htmlCode;
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




