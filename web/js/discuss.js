/**
 * Created by QAQ on 2016/10/12.
 */

function replyreply(did,rid,rrid){
    var formHTML = formToHTML({
        col:[0,12],
        //action:"xx.action",
        method:"post",
        onSubmit:"return onReplyreply()",
        list:[
            {
                type:"textarea",
                name:"text",
                id:"text",
                rows:7,
                placeholder:"输入回复内容",
                id:"text"
            },{
                type:"hidden",
                name:"id",
                value:did,
                id:"did"
            },{
                type:"hidden",
                name:"rid",
                value:rid,
                id:"rid"
            },{
                type:"hidden",
                name:"rrid",
                value:rrid,
                id:"rrid"
            },{
                type:"submit",
                label:"确定"
            }
        ]
    });
    $("#modal").html(HTML.modal({id:"replyreplymodal",title:"回复",body:formHTML,btnlabel:"确定",btncls:"link btn-xs",foot:false}));
    $("#replyreplymodal").modal("show");
}
function onReplyreply(){
    //ajax
    var text = $("#text").val();
    var did = $("#did").val();
    var rid = $("#rid").val();
    var rrid = $("#rrid").val();
    $.post("replyReply.action",{text:text,id:did,rid:rid,rrid:rrid},function (e) {
        e = eval(e);
        if(e == "SUCCESS") {
            $("#replyreplymodal").modal("hide");
            getReplyReply(did, rid, -1);
        }else{
            alert(e);
        }
    });
    return false;
}
function getReplyReply(did,rid,page){
    var $div = $(".replylist-"+rid);
    $.getJSON("getReplyReply.action",{id:did,rid:rid,rrid:page},function (e) {
        //alert(e);
        if(e.totalNum != 0){
            $div.html("");
            $div.removeClass("replylistnone");
            var i;
            for(i = 0 ; i < e.list.length ; i++){
                $("<div class='replyreply' id='replyreply-"+e.list[i].rid+"-"+e.list[i].did+"'></div>")
                    .append(HTML.floatLeft(HTML.a("UserInfo.jsp?username=admin","<img src='pic/head/"+e.list[i].username+".jpg' class='head-0' onerror='this.src=\"pic/defaulthead.jpg\"'>")))
                    .append($("<div class='replyreplytext'></div>").append(e.list[i].usernameHTML+e.list[i].reply).append("<p>"+e.list[i].text+"</p>"))
                    .append($("<div class='replyreplytime'></div>").append(e.list[i].time).append(" "+HTML.a("javascript:replyreply("+e.list[i].did+","+e.list[i].rid+","+e.list[i].rrid+")","回复")))
                    .appendTo($div);
            }
            if(e.totalPage>1) {
                var $pagediv = $("<div class='replyreplypage'></div>");
                if (e.page == 1) {
                    $pagediv.append("首页" + "　");
                    $pagediv.append("上一页" + "　");
                } else {
                    $pagediv.append(HTML.a("javascript:getReplyReply(" + e.did + "," + e.rid + "," + 1 + ")", "首页") + "　");
                    $pagediv.append(HTML.a("javascript:getReplyReply(" + e.did + "," + e.rid + "," + (e.page - 1) + ")", "上一页") + "　");
                }
                for(i=(e.page-5) ; i<=e.page+5; i++){
                    if(i<1) continue;
                    if(i>e.totalPage) continue;
                    if(i != e.page){
                        $pagediv.append(HTML.a("javascript:getReplyReply("+e.did+","+e.rid+","+i+")",i)+"　");
                    }else{
                        $pagediv.append(i+"　");
                    }
                }
                if(e.page == e.totalPage){
                    $pagediv.append("下一页　");
                    $pagediv.append("尾页");

                }else{
                    $pagediv.append(HTML.a("javascript:getReplyReply("+e.did+","+e.rid+","+(e.page+1)+")","下一页")+"　");
                    $pagediv.append(HTML.a("javascript:getReplyReply("+e.did+","+e.rid+","+e.totalPage+")","尾页"));
                }
                $pagediv.appendTo($div);
            }
        }
    })
}
