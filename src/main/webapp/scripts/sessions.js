/**
 * Created by vysokov-mg on 14.06.2018.
 */
//setting up ajax behaviour for a response with specified status
$.ajaxSetup({
    statusCode: {
        401: function(){
            window.location.href = "/auth"; //redirecting unauthorized user
        }
    }
});
