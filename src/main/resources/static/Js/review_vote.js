$(document).ready(function () {
    $('.linkVoteReview').on('click', function (e) {
        e.preventDefault();
        voteReview($(this));
    });

    function voteReview(currentLink) {
        var requestURL = currentLink.attr('href');
        $.ajax({
            type: 'POST',
            url: requestURL,
            beforeSend: function (xhr) {
                xhr.setRequestHeader("_csrf.headerName", "_csrf.token");
            }
        }).done(function (voteResult) {
            console.log(voteResult);
            showModalDialog("Vote Review", voteResult.message);
            $('#commonModal').on('hidden.bs.modal', function () {
                updateVoteCountAndIcon(currentLink, voteResult);
            }).modal('hide');
        }).fail(function () {
            showErrorModal("ERROR", "Error Voting Review!");
        });
    }

    function updateVoteCountAndIcon(currentLink, voteResult) {
        var reviewId = currentLink.attr('reviewId');
        var voteUpLink = $('#thumbsUp' + reviewId);
        var voteDownLink = $('#thumbsDown' + reviewId);
        $('#voteCount' + reviewId).text(voteResult.voteCount + ' Votes');
        var message = voteResult.message;
        if (message.includes('successfully voted up')) {
            highlightVoteUpIcon(voteUpLink, voteDownLink);
        } else if (message.includes('successfully voted down')) {
            highlightVoteDownIcon(voteUpLink, voteDownLink); // Pass voteUpLink and voteDownLink separately
        }
    }

    function highlightVoteUpIcon(voteUpLink, voteDownLink) {
        console.log("Vote Up Link:", voteUpLink); // Check if the voteUpLink is correctly selected
        console.log("Vote Down Link:", voteDownLink); // Check if the voteDownLink is correctly selected
        voteUpLink.removeClass('far').addClass('fas');
        voteDownLink.removeClass('fas').addClass('far');
    }


    // Making the original voted up becomes voted down and vice versa
    function highlightVoteDownIcon(voteUpLink, voteDownLink) {
        voteUpLink.removeClass('fas').addClass('far');
        voteDownLink.removeClass('far').addClass('fas');
    }
});
