/**
 * Created by Syiml on 2015/10/10 0010.
 */
var challenge=function(){
    var $main=$('#challenge-main');
    var init=function(){
        $.getJSON("challenge/blocklist.action",function(data){
            var table={};
            table.class="table";
            table.head=["#","模块名","组","进度"];
            table.body=[];
            for(var i=0;i<data.blockList.length;i++){
                table.body[i]=[data.blockList[i].id,data.blockList[i].name,data.blockList[i].group];
                if(data.blockList[i].isOpen) table.body[i][3]=data.blockList[i].userScore/data.blockList[i].score;
                else data.body[i][3]="未开启";
            }
            $main.html(HTML.panelnobody("模块列表",TableHTML(table),"default",""));
        });
    };
    return{
        $main:$main,
        init:init
    }
}();
challenge.init();
//样例
//var sample={
//    class:"table",
//    head:["#","message","time"],
//    body:[
//        ["1","消息1","时间1"],
//        ["2","消息2","时间2"]
//    ]
//};