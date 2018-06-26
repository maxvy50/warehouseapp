/**
 * Created by vysokov-mg on 26.06.2018.
 */
"use strict";

ajaxPost("#actionForm", {
    200/*SC_OK*/: function (data) {
        //$("#actionForm").find("div.statusDiv").html("Успех");
        fillTable("tableBody", data, prepareOrgRow);
        alert("Успех");
    }
});