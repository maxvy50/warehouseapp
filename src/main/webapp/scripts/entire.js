/**
 * Created by vysokov-mg on 14.06.2018.
 */
"use strict";
/*
//this function inserts <div> specified with an <option> selected on the action page
$("#actionSelect").on("change", function () {
    $("#actionForm").load("/templates/actionForms.html #" +
            $("#actionSelect").find("option:selected").val())
});


$("#addOrg").on("click", function () {
    $("#actionForm").load("/templates/actionForms.html #addOrgDiv");
});*/


var ajaxPost = function(formId, behaviour) {
    $(formId).submit(function (event) {
        event.preventDefault();
        var uri = window.location.pathname;
        console.log(uri + ":" + formId);
        $.ajax({
            method: "POST",
            url: uri,
            data: $(formId).serialize(),
            beforeSend: function (request) {
                request.setRequestHeader("isAJAX", "yep");
            },
            statusCode: behaviour // DO NOT USE CODE 401 -- IT IS FOR SESSION HANDLING!!!11
        });
        return false;
    });
};