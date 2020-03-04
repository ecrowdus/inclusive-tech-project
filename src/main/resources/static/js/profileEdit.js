$(document).ready(function() {
    $("#add-box").keydown(function(e) {
        if(e.which === 13) {
            e.preventDefault();
            var input = $(this).val();
            if(input !== "") {
                var $allergyList = $("#allergyList");

                $allergyList.append("<p><label><input type=\"checkbox\" name=\"allergies\" value=\"" + input + "\" checked=\"checked\" />" +
                    "<span>" + input + "</span></label></p>")
                $(this).val("");
                // $(this).next().removeClass("active");
            }
        }
    });
});