/**
 * Created by vysokov-mg on 14.06.2018.
 */
"use strict";

/*a convenient wrapper function for ajax requests*/
var ajaxPost = function (formId, behaviour) {
    $(formId).submit(function (event) {
        event.preventDefault();
        var uri = window.location.pathname;
        console.log("ajax request sent by " + formId + " @ " + uri);
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
 * data -- json*/
var fillTable = function (tableId, data, prepareTableData) {
    var tbody = document.getElementById(tableId);
    tbody.innerHTML = "";
    var n = data.length;
    if (prepareTableData == undefined) {
        prepareTableData = prepareTableDataDefault;
    }
    for (var i = 0; i < n; i++) {
        tbody.innerHTML +=
            "<tr>" +
            "<td>" + (i + 1) + "</td>" + prepareTableData(data[i]) +
            "</tr>";
    }

};

/*it builds <tr> from Record obj {content: []}*/
var prepareTableDataDefault = function (record) {
    var tr = "";
    for (var i = 0; i < record.content.length; i++) {
        tr += "<td>" + record.content[i] + "</td>";
    }
    return tr;
};


var prepareOrgRow = function (record) {
    var tr = "";
    tr += "<td>" + record.name + "</td>";
    tr += "<td>" + record.region + "</td>";
    tr += "<td>" + record.address + "</td>";
    return tr;
};


var prepareActionRow = function (record) {
    var tr = "";
    tr += "<td>" + record.date + "</td>";
    tr += "<td>" + record.type + "</td>";
    tr += "<td>" + record.supplier + "</td>";
    tr += "<td>" + record.consumer + "</td>";
    tr += "<td>" + record.item + "</td>";
    tr += "<td>" + record.amount + "</td>";
    tr += "<td>" + record.user + "</td>";
    return tr;
};