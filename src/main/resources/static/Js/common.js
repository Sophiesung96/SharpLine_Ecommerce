// enable the user change category images from local files
$("#fileImage").change(function () {
        var filesize = this.files[0].size;

        if (filesize > 1048576) {
            //html5 customize validation message
            this.setCustomValidity('You must choose an image less than 1 MB!');
            //browser will prevent the form from being submitted to the server
            this.reportValidity();
        } else {
            showImage(this);
        }
    }
);

function showImage(result) {
    var preview = document.getElementById('de');
    var file = result.files[0];
    var reader = new FileReader();
    reader.onload = function (event) {
        preview.src = reader.result;
        //if user chooses more than one pic
        // preview.appendChild(image);

    };
    reader.readAsDataURL(file);
}