$(document).ready(function () {
   

    $('#submitButton').on('click', function (fm) {
        fm.preventDefault();  //prevent form from submitting
        var attrs = $("#resForm").serialize();
        $.get("Servlet1", attrs,
                function (data, status) {
                    $("#result").html(data);
                    $("#modButton").show();
                });
    });
    
    $('#modButton').on('click', function (fm) {
        fm.preventDefault();  //prevent form from submitting
        var attrs = $("#resForm").serialize();
        $.post("Servlet2", attrs,
                function (data, status) {
                    $("#result2").html(data);
                });
    });
});