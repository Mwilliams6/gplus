var myVar;
$(document).ready(function ()
{
    $('#bth-search').on('click', function(event) {
        event.preventDefault();
        initiateRetrieval();
    });

    $('#bth-parse').on('click', function(event) {
        event.preventDefault();
        $.ajax({url: "/gplus/scrapePhotoData",
            success: function(){
                console.log("Scraping Photo Data");
            }
        });
    });

    $('#bth-cancel').on('click', function(event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        $( "#progressBarContainer" ).html("<div class='progress'><div class='progress-bar' role='progressbar' style='width:100%'></div></div>");
        clearInterval(myVar);
        $.ajax({url: "/gplus/cancel",
            success: function(){
                console.log("Cancelled");
            }
        });
    });
});

function initiateRetrieval(){
    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "post",
        url: "/gplus/scrapeAlbumData",
        dataType: 'json',
        cache: false,
        timeout: 600000,

        success: function (data) {

            var json = "<h4>Found "+data+" albums...</h4><br/>";
            $( "#feedback" ).append( json );
            pollForResults();
            myVar = window.setInterval(pollForResults, 2000);

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);
            $("#feedback").show();
        },
        error: function (e) {

            var json = "<h4>Error Ajax Response</h4>"
                + e.responseText;
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });
}

function pollForResults() {
    $.ajax({url: "/gplus/poller",
        success: function(result){
            console.log("Polled result: " + result);
            $( "#feedback" ).append( result );

            if (result.indexOf("XX-FINISHED-XX") !== -1)
            {
                $("#bth-cancel").prop("disabled",true);
                $("#bth-search").prop("disabled",false);
                clearInterval(myVar);
            }
        }
    });

    $.ajax({url: "/gplus/getProgress",
        success: function(result){
            console.log("Polled result: " + result);
            $( "#progressBarContainer" ).html(result);
        }
    });

    $.ajax({url: "/gplus/getProgress",
        success: function(result){
            console.log("Polled result: " + result);
            $( "#progressBarContainer" ).html(result);
        }
    });
}