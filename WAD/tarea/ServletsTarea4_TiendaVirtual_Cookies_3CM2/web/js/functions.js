$(document).ready(function () {
    // Get the modal
    var modal = document.getElementById('myModal');

    // Get the button that opens the modal
    var btn = document.getElementById("myBtn");

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        modal.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    $('#myBtn').on('click', function (fm) {
        fm.preventDefault();  //prevent form from submitting
        var attrs = $("#prodForm").serialize();
        $.get("ServletCarrito", attrs,
                function (data, status) {
                    $("#result").html(data);
                });
        modal.style.display = "block";
    });

    $('#borrarBtn').on('click', function (fm) {
        fm.preventDefault();  //prevent form from submitting
        var attrs = $("#prodForm").serialize();
        $.get("ServletBorrar", attrs,
                function (data, status) {
                    $("#result").html(data);
                });
    });
});