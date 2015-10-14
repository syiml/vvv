/**
 * Created by Syiml on 2015/7/11 0011.
 */
//$.getJSON("module/MessageJson.jsp",function(data){
//    var table={
//        class:"table",
//        head:["statu","title","time"],
//        body:[]
//    };
//    for(var i=0;i<data.length;i++){
//        var statu=data[i].statu;
//        var title=HTML.modal({id:"message_"+i,title: "ShowMessage",body:data[i].text,btnlabel:data[i].title,btncls:"link btn-xs"});
//        table.body[i]=[(statu==0?"未读":"已读"),title,new Date(data[i].time).Format("yyyy-MM-dd hh:mm:ss")];
//        //alert(table.body[i].length);
//    }
//    var body=HTML.panelnobody("message",TableHTML(table),"primary","");
//    $('#messageMain').append(body);
//});
var pagenum=10;
var getData=function(page){
    $div=$('#messageMain');
    $.getJSON("module/MessageJson.jsp?from="+(page*pagenum)+"&num="+(pagenum+1),function(data){
        var table={
            class:"table",
            head:["#","statu","title","time"],
            body:[]
        };
        var size=data.length;
        if(size==pagenum+1) size=pagenum;
        for(var i=0;i<size;i++){
            var statu=data[i].statu;
            var title=HTML.modal({id:"message_"+i,title: "查看系统消息",body:data[i].text,btnlabel:data[i].title,btncls:"link btn-xs"});
            table.body[i]=[page*pagenum+i+1,(statu==0?HTML.textb("未读","red"):HTML.textb("已读","green")),title,new Date(data[i].time).Format("yyyy-MM-dd hh:mm:ss")];
            //alert(table.body[i].length);
        }
        var pages="";
        if(page!=0) pages+=HTML.a("javascript:getData("+(page-1)+")","<span class='badge'>上一页</span>");
        if(data.length==pagenum+1) pages+="  "+HTML.a("javascript:getData("+(page+1)+")","<span class='badge'>下一页</span>");
        else pages+="　　　　";
        title="系统消息"+HTML.floatRight(pages);
        var body=HTML.panelnobody(title,TableHTML(table),"primary","");
        $('#messageMain').html(body);
    });
};
getData(0);