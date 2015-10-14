/**
 * Created by Syiml on 2015/9/27 0027.
 */
$('body').append('<div id="retry_modal"></div>');
function retry_body(username,info,cid){
    return formToHTML({
        col:[2,10],
        action:"setregistercontest.action",
        method:"post",
        list:[
            {
                type:"hidden",
                name:"statu",
                value:"3"
            },{
                type:"hidden",
                name:"username",
                value:username
            },{
                type:"hidden",
                name:"cid",
                value:cid
            },{
                type:"text",
                name:"info",
                value:info,
                label:"备注信息"
            },{
                type:"submit",
                label:"确定"
            }
        ]
    });
}
function retry(username,info,cid){
    $('#retry_modal').html("").append(HTML.modal({
        id:"_retry",
        title:"重试",
        body:retry_body(username,info,cid),
        foot:false
    }));

    $('#_retry').modal('toggle');
    //s={id:"id",title:"title",body:"body",btnlabel:"ok",btncls:"link btn-xs"}
}
function retry_body2(username,info,cid,bo){
    return "备注信息："+info+
        (bo?"<br>修改确认无误后["+HTML.a("setregistercontest.action?cid="+cid+"&username="+username+"&statu=0","重试")+"]":"");
}
function retry_show(user,info,cid,bo){
    $('#retry_modal').html("").append(HTML.modal({
        id:"_retry",
        title:"需修改",
        body:retry_body2(user,info,cid,bo),
        foot:false
    }));
    $('#_retry').modal('toggle');
    //s={id:"id",title:"title",body:"body",btnlabel:"ok",btncls:"link btn-xs"}
}