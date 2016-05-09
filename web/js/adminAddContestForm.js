/**
 * Created by Syiml on 2015/6/22 0022.
 */
$("#registerendtime").hide();
$("#registerstarttime").hide();
$("#password").hide();
$('#type').bind('change',function(){
    var select = $(this).children('option:selected').val();
    if(select == "3"||select == "4"||select == "5"){
        $("#registerendtime").show();
        $("#registerstarttime").show();
    }else{
        $("#registerendtime").hide();
        $("#registerstarttime").hide();
    }
    if(select == "1"){
        $("#password").show();
    }else{
        $("#password").hide();
    }
});
var c=function(){
    var select = $('#type').children('option:selected').val();
    if(select == "3"||select == "4"||select == "5"){
        $("#registerendtime").show();
        $("#registerstarttime").show();
    }else{
        $("#registerstarttime").hide();
        $("#registerendtime").hide();
    }
    if(select == "1"){
        $("#password").show();
    }else{
        $("#password").hide();
    }

    select = $('#rank').children('option:selected').val();
    if(select == 0){
        $("#icpcrank").show();
        $("#shortcode").hide();
        $("#training").hide();
    }
    if(select == "1"){
        $("#shortcode").show();
        $("#icpcrank").hide();
        $("#training").hide();
    }
    if(select == "2"){
        $("#shortcode").hide();
        $("#icpcrank").hide();
        $("#training").show();
    }
    return 1;
}();
$('#rank').bind('change',
    function(){
        var select = $(this).children('option:selected').val();
        if(select == 0){
            $("#icpcrank").show();
            $("#shortcode").hide();
        }
        if(select == "1"){
            $("#shortcode").show();
            $("#icpcrank").hide();
        }
        if(select == "2"){
            $("#shortcode").hide();
            $("#icpcrank").hide();
            $("#training").show();
        }
    }
);
