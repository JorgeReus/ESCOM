$(document).ready(function () {
    $('#send').on('click', function (fm) {
        fm.preventDefault();  //prevent form from submitting
        var attrs = $("#loginForm").serialize();
        $.post("ServletLogin", attrs,
                function (data, status) {
                    $("#result").html(data);
                });
    });
});