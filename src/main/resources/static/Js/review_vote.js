$(document).ready(function () {
 $('.linkVoteReview').on('click',function (e) {
     e.preventDefault();
     voteReview($(this));
 });

    function voteReview(currentLink){
        requestURL=currentLink.attr('href');
      $.ajax({
          type:'POST',
          url:requestURL,
          beforeSend:function (xhr) {
              xhr.setRequestHeader("_csrf.headerName","_csrf.token");
          }
      }).done(function (voteResult) {
          console.log(voteResult);
          showModalDialog("Vote Review",voteResult.message);
      }).fail(function () {
          showErrorModal("ERROR","Error Voting Review!");
      })
    }
})

