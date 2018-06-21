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
var fillTable = function (tableId, prepareRow, data) {
    var tbody = document.getElementById(tableId);
    tbody.innerHTML = "";
    var n = data.length;
    for (var i = 0; i < n; i++) {
        tbody.innerHTML += prepareRow(data[i]);
    }
}

/*it builds <tr> from OrgHasItem obj*/
var prepareOrgHasItemRow = function (record) {
    var tr = "<tr>";
    tr += "<td>" + record.org.name + "</td>";
    tr += "<td>" + record.item.name + "</td>";
    tr += "<td>" + record.amount + "</td>";
    return tr + "</tr>";
}

var prepareOrgRow = function (record) {
    var tr = "<tr>";
    tr += "<td>" + record.name + "</td>";
    tr += "<td>" + record.region + "</td>";
    tr += "<td>" + record.address + "</td>";
    return tr + "</tr>";
}