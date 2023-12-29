$(document).ready(function () {
    $('#trackingList').on("click",".link-Remove",function (e) {
        e.preventDefault();
        deleteTrack($(this));
        updateTrackCountNumber();
    });
    // add new track record
    $('#track').on("click","#linkTrackOrder",function (e) {
        e.preventDefault();
        addNewTrackRecord();
    })
    // when the <select> changes,
    // the note's content shows the selected option's value accordingly
    $('#trackingList').on("change",".dropDownStatus",function (e) {
        dropdownList=$(this);
        rowNumber=dropdownList.attr("rowNumber");
        selectedOption=$("option:selected",dropdownList);
        defaultNote=selectedOption.attr('defaultDescription');
        $('#trackNote'+rowNumber).text(defaultNote);
    })


})

function deleteTrack(link){
    rowNumber=link.attr('rowNumber');
    $('#trackList'+rowNumber).remove();
}

function addNewTrackRecord(){
    htmlCode=generateTrackCode();
    $('#trackingList').append(htmlCode);
}

function updateTrackCountNumber(){
    $('.divTrackCount').each(function (index,element) {
        element.innerHTML=""+(index+1);
    })
}

function generateTrackCode(){
    TrackCount=$('.hiddenTrackingId').length+1;
    trackNoteId='trackNote'+TrackCount;
    rowId='trackList'+TrackCount;
    //get updatedTime
    currentTime=formatDateTimeforForm();
    TrackHtml=`<div class="border rounded p-1" id="${rowId}">
                <input type="hidden" value="0" name="trackId" class="hiddenTrackingId"/>
                <div class="row mt-2" >
                    <div class="col-1 divTrackCount">
                        ${TrackCount}
                  
                            <div><a class="fas fa-trash icon-dark link-Remove" href="" rowNumber="${TrackCount}"></a></div>
                       
                    </div>
                    <div class="col-10">
                        <div class="form-group row">
                            <label class="  form-control-label">
                                Time:
                            </label>
                            <div class="col">
                                <input type="datetime-local" name="trackDate" value="${currentTime}"  class="form-control"
                                      style="max-width: 300px;">
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="form-control-label">
                                Status:
                            </label>
                            <div class="col">                     
                                    <input type="hidden" name="trackStatus" value="0" />                              
                                <select name="trackStatus"  class="form-control dropDownStatus"
                                        required rowNumber="${TrackCount}"                 
                                        style="max-width: 150px;">`;
    //clone the<select> attribute's html code(<options> value)
    TrackHtml+= $('#trackStatusOptions').clone().html();

    TrackHtml+=` </select>
                     </div>
                      </div>

                        <div class="form-group row">
                            <label class="form-control-label">
                                Note:
                            </label>
                            <div class="col mt-3">
                                <textarea  rows="2" cols="10" name="trackNotes" class="form-control"
                                           id="${trackNoteId}"                                        
                                           style="max-width: 300px;"></textarea>
                            </div>
                        </div>
                    </div>
                </div>

                </div>`;
    return TrackHtml;
}

function formatDateTimeforForm(){
    date=new Date();
    year=date.getFullYear();
    month=date.getMonth();
    day=date.getDay();
    hour=date.getHours();
    minutes=date.getMinutes();
    if(month<10){
        month="0"+month;

    }
    if(day<10){
        day="0"+day;

    }
    if(hour<10){
        hour="0"+hour;

    }
    if(minutes<10){
        minutes="0"+minutes;

    }

    return year+"-"+month+"-"+day+"T"+hour+":"+minutes;
}