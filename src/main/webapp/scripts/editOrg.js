/**
 * Created by vysokov-mg on 27.06.2018.
 */
"use strict";

$("#orgName").on("change", function () {
    $.ajax(
        {
            method: "POST",
            url: "/update",
            data: {
                orgName: $(this).find("option:selected").val(),
                whatToSend: "organization"
            },
            beforeSend: function (request) {
                request.setRequestHeader("isAJAX", "yep");
            },
            success: function (data) {
                console.log(data);
                $("#orgRegion").val(data.region);
                $("#orgAddress").val(data.address);
            }
        })
});

ajaxPost("#actionForm", {
    200/*SC_OK*/: function () {
        alert("Успех");
    }
});