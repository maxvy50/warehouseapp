/**
 * Created by vysokov-mg on 26.06.2018.
 */
"use strict";

update();

ajaxPost("#actionForm", {
    200/*SC_OK*/: function (data) {
        //$("#actionForm").find("div.statusDiv").html("Успех");
        console.log(data);
        fillTable("tableBody", data);
        alert("Успех");
    }
});