/**
 * Created by vysokov-mg on 26.06.2018.
 */
"use strict";

var mapSelectIdToTable = function (id) {
    switch (id) {
        case "#supplierName": return "supplierTableBody";
        case "#consumerName": return "consumerTableBody";
    }
};

var restrictSelectAndUpdateTable = function (one/*update one*/, another/*restrict another*/) {
    $(one).on("change", function () {
        /*it forces user to choose distinct supplier and consumer*/
        var opt = $(this).find("option:selected").val();
        $(another).find("option[value!=\"dontTouchMe\"]").each(function () {
            $(this).removeAttr("disabled")
        });
        /*it updates corresponding to 'one' table with available items*/
        $(another).find("option[value=\"" + opt + "\"]").attr("disabled", "disabled");
        $.ajax(
            {
                method: "POST",
                url: "/update",
                data: {orgName: $(this).find("option:selected").val()},
                beforeSend: function (request) {
                    request.setRequestHeader("isAJAX", "yep");
                },
                success: function (data) {
                    console.log(data);
                    fillTable(mapSelectIdToTable(one), data);
                }
            }
        );
    });
};

restrictSelectAndUpdateTable("#supplierName", "#consumerName");
restrictSelectAndUpdateTable("#consumerName", "#supplierName");
ajaxPost("#actionForm", {
    200/*SC_OK*/: function (data) {
        //$("#actionForm").find("div.statusDiv").html("Успех");
        console.log(data);
        fillTable("supplierTableBody", data[0]);
        fillTable("consumerTableBody", data[1]);
        alert("Успех");
    }
});