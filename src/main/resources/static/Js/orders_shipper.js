var confirmText;
var yesButton;
var noButton;
var iconNames={
    'PICKED':'fa-people-carry',
    'SHIPPING':'fa-solid fa-truck-fast',
    'DELIVERED':'fa-box-open',
    'RETURNED':'fa-undo',
    'PACKAGED':'fa-solid fa-cube'
}
$(document).ready(function () {
    confirmText=$('#confirm-text');
    yesButton=$('#yes-Button');
    noButton=$('#noButton');
 $('.linkUpdateStatus').on('click',function (e) {
     e.preventDefault();
     link=$(this);
    showdateConfirmModal(link);
 });
 addEventHandlerforYesButton();
});

function showdateConfirmModal(link){
    noButton.text("NO");
    yesButton.show();
    orderId=link.attr('orderId');
    Modalstatus=link.attr('status');
    yesButton.attr('href',link.attr('href'))
    var modal = new bootstrap.Modal(document.getElementById('ConfirmModal'));
    $('#ConfirmModal').find('.modal-body').text('Are you sure you want to update status ' +
        'of the Order ID'+"#"+" "+orderId
    +"to"+" "+Modalstatus+"?");
    modal.show();
}

function addEventHandlerforYesButton(){
    yesButton.on('click',function (e) {
        e.preventDefault();
        sendRequesttoOrderStatus($(this));
    })
}

function sendRequesttoOrderStatus(button){
    requestUrl=button.attr('href');
    $.ajax({
        type:'POST',
        url:requestUrl,
        beforeSend:function (xhr) {
            xhr.setRequestHeader("_csrf.headerName","_csrf.token");
        },
    }).done(function (response) {
        showMessageModal("Order Updated Successfully!");
        updateStatusColor(response.orderId,response.status);
       console.log(response);
    }).fail(function (err) {
      showMessageModal("Error occurred while updating Order Status");
    });
}

function showMessageModal(message){
    noButton.text("Close");
    yesButton.hide();
    confirmText.text(message);
}

function updateStatusColor(orderId,status){
    link=$('#link'+status+orderId);

    link.replaceWith(" <i class='fas"+iconNames[status]+"fa-2x icon-green'></i>");
}