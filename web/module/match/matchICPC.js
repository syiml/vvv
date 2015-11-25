/**
 * 观战模式,不考虑重判
 * result-> yes/no/new -> 1/0/-1
 * user={
 *     username:{
 *          nick,name,gender,faculty,major,cla,no
 *     },
 *     username:{
 *          ...
 *     }
 * }
 * rank:[
 *     {rank,username,nick,S,W,score[[{rid,result,time}],[{rid,result,time}]]},
 *     {...}
 * ]
 * Created by Administrator on 2015/11/25 0025.
 */
var matchICPC=function(){
    var status;
    var user;
    var rank;
    var init=function(){//刚刚启动时做的初始化工作

    };
    var putStatus=function(s){
        //有一个新的status状态改变，可能是一个新的提交，或者是提交结果有变化。s:{rid,username,result}
        //同步改变rank和网页布局
    };
};