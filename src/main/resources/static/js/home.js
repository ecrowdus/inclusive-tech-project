$(document).ready(function() {
    $(".dropdown-trigger").dropdown({
        hover: false,
        coverTrigger: false
    });
    $('.datepicker').datepicker({
        autoClose: true,
        defaultDate: new Date(Date.now()),
        setDefaultDate: true,
        disableDayFn: function(date) {
            var d = new Date(Date.now());
            d.setDate(d.getDate() + 1);
            return date > d;
        }
    });
    $('select').formSelect();

    $("tr").click(function(e) {
        $("#selectionPane").fadeOut();
        $("#spinnerPane").fadeIn();

        $.get("/home/meal", {
                meal: $(this).data("value"),
                diningHall: $("#diningHallSelect").val(),
                date: $("#date").val()
            }, function(result) {
                $("#hallPane").html(result);
                $("#spinnerPane").fadeOut();
                $("#hallPane").fadeIn();
        });
    });


});
