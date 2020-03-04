$(document).ready(function() {
    $(".hall-back-button").click(function(e) {
        $("#hallPane").fadeOut();
        $("#selectionPane").fadeIn();
    });

    $("#stations .station-item").click(function(e) {
        var stationName = $(this).data("name");
        $("#stations").fadeOut();
        $(".station-pane[data-name='" + stationName + "']").fadeIn();
    });

    $(".station-pane .dish-item").click(function(e) {
        var stationName = $(this).data("station");
        var dishName = $(this).data("dish");
        $(".station-pane").fadeOut();

        $(".dish-pane[data-station='" + stationName + "'][data-dish='" + dishName + "']").fadeIn();
    });

    $(".station-pane .station-back-button").click(function(e) {
        $(".station-pane").fadeOut();
        $("#stations").fadeIn();
    });

    $(".dish-pane .dish-back-button").click(function(e) {
        var stationName = $(this).data("station");
        console.log($(this));
        $(".dish-pane").fadeOut();
        $(".station-pane[data-name='" + stationName + "']").fadeIn();
    });

    $(".ingredient-show-more").click(function(e) {
        var stationName = $(this).data("station");
        var dishName = $(this).data("dish");
        var $elem = $(".all-ingredients-list[data-station='" + stationName + "'][data-dish='" + dishName + "']")
        if(!$elem.is(":visible")) {
            console.log('h3');
            $elem.slideDown();
            $(this).find("button i").text("keyboard_arrow_up");
        }
        else {
            $elem.slideUp();
            $(this).find("button i").text("keyboard_arrow_down");
        }
    });

});