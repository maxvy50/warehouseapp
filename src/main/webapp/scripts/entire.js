/**
 * Created by vysokov-mg on 14.06.2018.
 */
"use strict";

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