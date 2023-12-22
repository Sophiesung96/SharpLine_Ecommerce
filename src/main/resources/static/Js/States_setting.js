var buttonLoadCountriesForStates;
var dropDownCountriesForStates;
var dropDownStates2;
var LabelStatesName;
var fieldStatesName;
var buttonAddState;
var buttonUpdateState;
var buttonDeleteState;

$(document).ready(function () {
buttonLoadCountriesForStates=$('#buttonLoadCountriesForStates');
dropDownCountriesForStates=$('#dropDownCountriesForStates');
dropDownStates2=$('#dropDownStates2');
LabelStatesName=$('#LabelStatesName');
fieldStatesName=$('#fieldStatesName');
buttonAddState=$('#buttonAddState');
buttonDeleteState=$('#buttonDeleteState');
buttonUpdateState=$('#buttonUpdateState');
buttonLoadCountriesForStates.click(function () {
    buttonLoadCountriesForStates.prop("value","Refresh Country List");
    loadCountries4States();
});

dropDownCountriesForStates.change(function () {
  loadStates4Country();
});

buttonAddState.click(function () {
    addState();
});

buttonUpdateState.click(function () {
    updateState();
});

buttonDeleteState.click(function () {
deleteState();
})

    dropDownStates2.on('change',function () {
        changeFormToSelectedStates();
    })


})



function loadCountries4States(){
    var url="/states/list";
    $.get(url,function (responseJson) {

        dropDownCountriesForStates.empty();
        $.each(responseJson,function (index,country) {
            optionvalue=country.id+"-"+country.code;
           $('<option>').val(optionvalue).text(country.name).appendTo(dropDownCountriesForStates);
        })
    });
}


function loadStates4Country(){
   selectedCountry=$('#dropDownCountriesForStates option:selected');
    countryID=selectedCountry.val().split("-")[0];
    var url='/states/list_by_country/'+countryID;
    $.get(url,function (responseJson) {
        $('#selectCountry').empty();
        $.each(responseJson,function (index,state) {
            $('<option>').text(state.name).val(state.id).appendTo(dropDownStates2);
        })
    }).done(function () {
        changeFormStatetoNew();
    })
}

function changeFormStatetoNew(){
  buttonAddState.prop("value","Add");
  buttonDeleteState.prop("disabled",true);
  buttonUpdateState.prop("disabled",true);
  fieldStatesName.val("").focus();
}

function changeFormToSelectedStates(){
    buttonAddState.val("New");
    buttonDeleteState.prop("disabled",false);
    buttonUpdateState.prop("disabled",false);
    LabelStatesName.text("Selected States:");
    SelectedStates=$('#dropDownStates2 option:selected').text();
    fieldStatesName.val(SelectedStates);
}

function validateFormState(){
    var state=document.getElementById('formState');
    // check if the form inputs are valid
    if(!state.checkValidity()){
        return false;
    }
    return true;
}

function  addState() {
    var weburl = "/states/save";
    StateName = $('#fieldStatesName').text();
    jsonData = {name: StateName, countryId: countryID};
    if(!validateFormState()){
        return;
    }
    $.ajax({
        type: 'POST',
        url: weburl,
        beforeSend: function (xhr) {
            //for spring security
            xhr.setRequestHeader('_csrf.HeaderName', '_csrf.token');
        },
        data: JSON.stringify(jsonData),
        dataType: 'text',
        contentType: "application/json"
    }).done(function (stateId) {
        selectNewlyAddedState(stateId,StateName);
        alert("You have successfully added a new state!");
    });
}

function  updateState(){
  webUrl="/states/update";
    StateName = $('#fieldStatesName').val();
    sid=$('#dropDownStates2 option:selected').val();
    jsonData = {id:sid,name: StateName, countryId: countryID};
    if(!validateFormState()){
        return;
    }
    $.ajax({
        type: 'POST',
        url: webUrl,
        beforeSend: function (xhr,) {
            //for spring security
            xhr.setRequestHeader('_csrf.HeaderName', '_csrf.token');
        },
        data: JSON.stringify(jsonData),
        dataType: 'text',
        contentType: "application/json"
    }).done(function () {
        $('#dropDownStates2 option:selected').text(StateName);
        alert("You have successfully updated a state!");
    });
}

function deleteState() {
    newoptionValue=$('#dropDownStates2').val();
    StateName = $('#fieldStatesName').text();
    sid = $('#dropDownStates2').val();
    Weburl="states/delete/"+sid;

    $.ajax({
        type: 'DELETE',
        url: Weburl,
        beforeSend: function (xhr) {
            //for spring security
            xhr.setRequestHeader('_csrf.HeaderName', '_csrf.token');
        }
    }).done(function (countryId) {
        $("#dropDownStates2 option [value='"+newoptionValue+"']").text(StateName);
        alert("You have successfully updated a state!");
    });
}


function  selectNewlyAddedState(stateId,stateName){
    optionValues=stateId;
    $('<option>').val(optionValues).text(stateName).appendTo(dropDownStates2);
    $("#dropDownStates2 option [value='"+optionValues+"']").prop("selected",true);
}



function getscroll(){
    if(dropDownStates2.scrollTop+dropDownStates2.offsetHeight>=dropDownStates2.scrollHeight-1){
        alert("All states/provinces have been loaded!");
    }

}