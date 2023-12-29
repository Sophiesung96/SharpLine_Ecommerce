$(document).ready(function () {
    $('#productList').on('click','.link-Remove',function (e) {
        e.preventDefault();
        if(DoesOrderHaveOnlyOneProduct()){
                var modal = new bootstrap.Modal(document.getElementById('ModalW'));
                 $('#ModalW').find('.modal-body').text('Could not remove the product. The order must have at least one!');
                modal.show();
        }else{
            removeProduct($(this));
            updateOrderAmount();
        }
    })
})

function removeProduct(link){
    rowNumber=link.attr('rowNumber');
    $('#list'+rowNumber).remove();
    $('.divCount').each(function (index,element) {
        element.innerHTML=""+(index+1);
    });
}

function DoesOrderHaveOnlyOneProduct(){
 productCount=$('.hiddenProductId').length;
 return productCount==1;
}

