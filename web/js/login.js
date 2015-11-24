/**
 * Created by Syiml on 2015/10/2 0002.
 */
var login={
    init:function(){
        $('#loginform').attr("onsubmit","return login.login()");
    },
    login:function(){
        var loginsuccess=function () {
            $('#loginsubmit').val("登录成功，正在跳转回登录前页面...");
            location.href="return.jsp";
        };
        var loginerror=function(s){
            alert(s);
            $('#loginsubmit').removeAttr("disabled").val("登录");
        };
        var form={};
        form.user=$('#username').val();
        form.pass=$('#password').val();
        $('#loginsubmit').attr("disabled","disabled").val("请稍等...");
        $.post("login.action",form,function(data){
            if(data.ret=="LoginSuccess"){
                loginsuccess();
            }else if(data.ret=='WrongPassword'){
                loginerror("密码错误");
            }else if(data.ret=='NoSuchUser'){
                loginerror("用户名不存在");
            }else{
                loginerror("登录失败");
            }
        },"json");
        return false;
    }
};
login.init();