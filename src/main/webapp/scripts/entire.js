/**
 * Created by vysokov-mg on 14.06.2018.
 */
"use strict";

//this function inserts <form> specified according to an <option> selected on the action page
$("#actionSelect").on("change", function () {
    $("#actionForm").load("/templates/actionForms.html #" +
        $("#actionSelect").find("option:selected").val());
});

//this function submits data from the #actionForm on the action page and prints the status in the #statusDiv
$("#actionForm").submit(function (e) {
    e.preventDefault();
    $.ajax({
        method: "POST",
        url: "/actions",
        data: $(this).serialize(),
        beforeSend: function (request) {
            request.setRequestHeader("isAJAX", "yep");
        },
        success: function (data) {
            if (data) {
                $("#actionForm").find("div.statusDiv").html(data);
            }
        }
    });
    return false;
});
