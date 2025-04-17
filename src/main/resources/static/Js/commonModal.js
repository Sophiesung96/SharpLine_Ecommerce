function showModalDialog(title, message) {
    // Set modal title
    $("#modalTitle").text(title);
    // Set modal body content
    $("#modalBody").text(message);
    // Initialize modal
    var modal = new bootstrap.Modal(document.getElementById('commonModal'));
    // Show modal
    modal.show();
}


function showErrorModal(message) {
    showModalDialog("Error", message);
}

function showWarningModal(message) {
    showModalDialog("Warning", message);
}