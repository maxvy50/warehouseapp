/**
 * Created by vysokov-mg on 26.06.2018.
 */
"use strict";

ajaxPost("#authForm", {
    203/*SC_NON_AUTHORITATIVE_INFORMATION*/: function () {
        $("#authForm").find("div.statusDiv").html("Неверное имя пользователя или пароль");
    },
    400/*SC_BAD_REQUEST*/: function () {
        $("#authForm").find("div.statusDiv").html("Пароли не совпадают!");
    },
    409/*SC_CONFLICT*/: function () {
        $("#authForm").find("div.statusDiv").html("Выбранное имя пользователя уже занято");
    },
    200/*SC_SUCCESS*/: function () {
        window.location.href = (window.location.pathname == "/register") ? "/auth" : "/";
    }
})