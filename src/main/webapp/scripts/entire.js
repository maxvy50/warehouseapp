/**
 * Created by vysokov-mg on 14.06.2018.
 */
"use strict";

/*a convenient wrapper function for ajax requests*/
var ajaxPost = function (formId, behaviour) {
    $(formId).submit(function (event) {
        event.preventDefault();
        var uri = window.location.pathname;
        console.log("ajax request sent by" + formId + "@" + uri);
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

/*function fillTable accepts following arguments:
* tableId -- obvious
* prepareRow -- function to build <tr> from js-object properly
* data -- array of structure-similar js-objects, which MUST fit prepareRow*/
var fillTable = function (tableId, data) {
    var tbody = document.getElementById(tableId);
    tbody.innerHTML = "";
    var n = data.length;
    for (var i = 0; i < n; i++) {
        tbody.innerHTML += prepareTableRow(data[i].content);
    }
}

/*it builds <tr> from Record obj*/
var prepareTableRow = function (record) {
    var tr = "<tr>";
    for (var i = 0; i < record.length; i++) {
        tr += "<td>" + record[i] + "</td>";
    }
    return tr + "</tr>";
}

/*
var prepareOrgRow = function (record) {
    var tr = "<tr>";
    tr += "<td>" + record.name + "</td>";
    tr += "<td>" + record.region + "</td>";
    tr += "<td>" + record.address + "</td>";
    return tr + "</tr>";
}*/
