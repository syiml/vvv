/**
 * Created by Syiml on 2015/7/25 0025.
 */
var tagadmin=function(pid){
    var _body='';
    $('#tagadminmodal').html('').append(HTML.modal({id:'_tag',title:"题目标签",body:_body,btnlabel:'',btncls:'link btn-xs'}));
    $('#_tag').modal('show');
    load(pid);
};
var load=function(pid){
    var $tagbody=$('#tagadminmodal').find('.modal-body').text('');
    $.getJSON("module/problemtag/tag.jsp",function(data){
        var s="";
        s+='<div class="right-block form-inline"><div class="input-group input-group-sm"><span class="input-group-addon">添加标签</span><select class="form-control" id="_addtag" name="type">';
        for(var i=0;i<data.length;i++){
            s+='<option value="'+data[i].id+'">'+data[i].name+'</option>';
        }
        s+='</select></div><button onclick="add('+pid+')" class="btn btn-default btn-sm">添加</button>';
        $tagbody.append(s);
        $.getJSON("module/problemtag/tag.jsp?pid="+pid, function (data) {
            //data=[{tagid,tagname,rating},...]
            //show table
            var table={};
            table.class='table';
            table.head=['标签名','积分','操作'];
            table.body=[];
            for(var i=0;i<data.length;i++){
                table.body[i]=[data[i].tagname,data[i].rating-500,HTML.a("javascript:del("+data[i].tagid+","+pid+")","删除")];
            }
            $tagbody.append(TableHTML(table));
            $tagbody.append("如果缺少您所需要的标签，请到"+HTML.a("Discuss.jsp?id=6","BUG反馈")+"告诉我们~");
        });
    });
};
var del=function(tagid,pid){
    $.post("module/problemtag/update.jsp?type=del&pid="+pid+"&tagid="+tagid,load(pid));
};
var add=function(pid){
    var tagid=$('#_addtag').val();
    $.post("module/problemtag/update.jsp?type=add&pid="+pid+"&tagid="+tagid,load(pid));
};
/*
var sample={
    class:"table",
    head:["#","message","time"],
    body:[
        ["1","消息1","时间1"],
        ["2","消息2","时间2"]
    ]
};*/
