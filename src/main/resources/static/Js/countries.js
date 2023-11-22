var buttonLoad;
var dropDownCountries;
var buttonAddCountry;
var buttonUpdateCountry;
var buttonDeleteCountry;
var LabelCountryName;
var fieldCountryName;
var fieldCountryCode;

$(document).ready(function () {
    buttonLoad=$('#buttonLoadCountries');
    dropDownCountries=$('#dropDownCountries');
    buttonAddCountry=$('#buttonAddCountry');
    buttonUpdateCountry=$('#buttonUpdateCountry');
    buttonDeleteCountry=$('#buttonDeleteCountry');
    LabelCountryName=$('#LabelCountryName');
    fieldCountryName=$('#fieldCountryName');
    fieldCountryCode=$('#fieldCountryCode');
    buttonLoad.click(function () {
        loadCountries();

    });


    //create new country name and code
    buttonAddCountry.click(function () {
        if(buttonAddCountry.val()=="Add"){
            addCountry();
        }else{
            changeFormStatesToNew();
        }


    });

    buttonUpdateCountry.click(function () {
        updateCountry();

    });

    dropDownCountries.on('change',function () {

        changeFormToSelectedCountry();


    });
    
    buttonDeleteCountry.click(function () {
        deleteCountry();
    })




});




function changeFormStatesToNew() {
    buttonAddCountry.val("Add");
    LabelCountryName.text("Country Name:");
    buttonUpdateCountry.prop("disabled", true);
    buttonDeleteCountry.prop("disabled", true);
    fieldCountryCode.val("");
    fieldCountryName.val("").focus();
}

function validateFormCountry(){
    var formCountry=document.getElementById('CForm');
    if(!formCountry.checkValidity()){
        //prevent blank value
        formCountry.reportValidity();
        return false;
    }
    return true;
}



function updateCountry(){
    countryName=fieldCountryName.val();
    countryCode=fieldCountryCode.val();
    cId= dropDownCountries.val().split("-")[0];
    jsonData={id:cId,name:countryName,code:countryCode};
    if(!validateFormCountry()) return;
    $.ajax({
        type:'POST',
        url:'/countries/update',
        beforeSend:function (xhr) {
            xhr.setRequestHeader('_csrf.HeaderName','_csrf.token');
        },
        data:JSON.stringify(jsonData),
        dataType: 'text',
        contentType:"application/json"
    }).done(function (countryId) {
        $('#dropDownCountries option:selected').val(countryId+"-"+countryCode);
        $('#dropDownCountries option:selected').text(countryName);
        changeFormStatesToNew();
        alert("The Country has been Added!");
    });
}

function addCountry(){
    websiteurl="/countries/save";
    //this is for checking whether the input didn't being filled
    if(!validateFormCountry()) return;
    countryName=fieldCountryName.val();
    countryCode=fieldCountryCode.val();
    jsonData={name:countryName,code:countryCode};
    $.ajax({
        type:'POST',
        url:'/countries/save',
        beforeSend:function (xhr) {
            //for spring security
            xhr.setRequestHeader('_csrf.HeaderName','_csrf.token');
        },
        data:JSON.stringify(jsonData),
        dataType: 'text',
        contentType:"application/json"
    }).done(function (countryId) {
       selectNewlyAddedCountry(countryId,countryName,countryCode);
    })
};

function selectNewlyAddedCountry(countryId,countryName,countryCode){
    optionValues=countryId+'-'+countryCode;
    $('<option>').val(optionValues).text(countryName).appendTo(dropDownCountries);
    $("#dropDownCountries option [value='"+optionValues+"']").prop("selected",true);
}


function changeFormToSelectedCountry(){
    buttonAddCountry.val("New");
    buttonUpdateCountry.prop("disabled",false);
    buttonDeleteCountry.prop("disabled",false);
    LabelCountryName.text("Selected Country:");
    selectedCountries=$('#dropDownCountries option:selected').text();
    fieldCountryName.val(selectedCountries);
    countryCode=dropDownCountries.val().split("-")[1];
    fieldCountryCode.val(countryCode);
}

function deleteCountry(){

    newoptionValue=$('#dropDownCountries option:selected').val();
    countryName=$('#dropDownCountries').text();
    countryIds=newoptionValue.split("-")[0];
   deleteUrl="/countries/delete/"+countryIds;



    $.ajax({
        type: 'DELETE',
        url: deleteUrl,
        beforeSend: function (xhr) {
            //for spring security
            xhr.setRequestHeader('_csrf.HeaderName', '_csrf.token');
        }
    }).done(function (countryId) {
        $("#dropDownCountries option [value='"+newoptionValue+"']").remove();
        changeFormStatesToNew();
        alert("The Country has been deleted!");

    });
}



function getscroll() {
    var select = document.getElementById('dropDownCountries');
    if (select.scrollTop + select.offsetHeight >= select.scrollHeight - 1) {
        alert("All countries have been loaded!");
    }

};






function loadCountries(){
   url="/countries/list";
   $.get(url,function (responseJson) {
       dropDownCountries.empty();
       $.each(responseJson,function (index,country) {
           optionvalue=country.id+"-"+country.code;

           $('<option>').val(optionvalue).text(country.name).appendTo(dropDownCountries);
       });
       //done() executes after GET request has been accomplished
   }).done(function () {
       buttonLoad.val("Refresh Country list");
   }).fail(function () {
       alert("Error:Could not connect to server or server encounter an error");
   })
}