
var returnModal;
var fieldNote;
var OrderId;
var divReason;
var divMessage;
var firstButton;
var LastButton;
$(document).ready(function () {
    returnModal=$('#ReturnOrderModal');
    modalTitle=$('#returnOrderModalTitle');
    fieldNote=$('#returnNote');
    divMessage=$('.div-Message');
    divReason=$('.div-Reason');
    firstButton=$('#firstButton');
    LastButton=$('#secondButton');
    handleReturnOrderLink();
});

function handleReturnOrderLink(){
    $('.linkReturnOrder').on('click',function (e) {
        e.preventDefault();
        showReturnModalMessage($(this))
    });

}

function submitReturnOrderForm(){
    reason=$("input[name='reason']:checked").val();
    Note=fieldNote.val();
   sendReturnOrderRequest(reason,Note);
    return false;
}

function sendReturnOrderRequest(reason,Note){
    requestURL='/customers/Order/return';
    requestBody={id:OrderId,reason:reason,note:Note};
    $.ajax({
        type:'POST',
        url:requestURL,
        data:JSON.stringify(requestBody),
        contentType:'application/json',
        dataType:'text',
        beforeSend:function (xhr) {
            xhr.setRequestHeader("_csrf.headerName","_csrf.token");
        }
    }).done(function (response) {
        showMessage();
        updateStatusTextandHideReturnButton(OrderId);
      console.log(response);
    }).fail(function (ex) {
       console.log(ex);
       showMessage(ex.responseText);
    })
};

function showReturnModalMessage(link){
  divMessage.hide();
  divReason.show();
  firstButton.show();
  LastButton.text('Cancel');
  fieldNote.val("");
    OrderId=link.attr('orderId');
    var Returnmodal = new bootstrap.Modal(document.getElementById('ReturnOrderModal'));
    Returnmodal.show();
    modalTitle.text('Return Order ID#'+OrderId);
}

function showMessage(){
    var Responsemodal = new bootstrap.Modal(document.getElementById('ResponsorderModal'));
    Responsemodal.show();
}
function updateStatusTextandHideReturnButton(OrderId){
    $('.textOrderStatus'+OrderId).each(function (index) {
        $(this).text('RETURN_REQUESTED');
    });
    $('.linkReturn'+orderId).each(function (index) {
        $(this).hide();
    });

}