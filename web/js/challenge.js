/**
 * Created by Syiml on 2015/10/10 0010.
 */
var gv={
    jd_color:"#0a0",
    jd_color2:"#ddd",
    bg_color:"#fff",
    offset:[0,-650],
    position: {//[x,y,size] size=small/middle/large
        1:[201, 614, "small"],//入门
        2:[263, 583, "small"],//基础
        3:[292, 493, "middle"],//暴力
        4:[277, 657, "middle"],//排序
        5:[338, 577, "middle"],//贪心
        6:[196, 496, "middle"],//迭代
        7:[165, 695, "large"],//基础合集
        8:[244, 409, "middle"],//递归
        9:[123, 793, "middle"],//优化
        10:[347, 747, "small"],//高级排序
        11:[90, 732, "small"],//二分
        12:[45, 788, "small"],//三分

        13:[270,753,"small"],//分治
        14:[119,558,"middle"],//模拟
        15:[216,803,"small"],//位运算
        50:[113,652,"small"],//字符串
        51:[44,683,"small"],//KMP
        52:[50,608,"small"],//马拉车

        101:[90,873,"small"],//数据结构入门
        110:[34,937,"middle"],//链结构
        111:[18,1027,"small"],//栈
        112:[91,1015,"small"],//队列
        120:[165,874,"middle"],//树结构
        121:[156,975,"small"],//优先队列
        122:[226,958,"small"],//并查集
        123:[294,951,"small"],//线段树
        124:[260,871,"small"],//树状数组
        125:[373,982,"small"],//字典树
        126:[345,890,"middle"],//平衡树
        102:[317,819,"small"],//哈希表

        201:[373,670,"small"],//数学入门
        211:[416,736,"small"],//素数
        212:[489,723,"small"],//同余
        213:[393,807,"small"],//唯一分解
        214:[463,791,"small"],//GCD
        215:[432,871,"small"],//欧拉函数
        216:[500,852,"small"],//扩展GCD
        217:[538,780,"small"],//快速幂
        218:[527,921,"small"],//逆元
        219:[570,854,"small"],//米勒罗宾
        220:[459,940,"small"],//中国剩余
        231:[518,656,"small"],//概率论入门
        241:[445,662,"small"],//组合入门
        251:[591,645,"small"],//矩阵入门
        252:[562,710,"small"],//矩阵的基本运算
        253:[609,771,"small"],//矩阵快速幂
        254:[632,701,"small"],//高斯消元

        501:[336,422,"small"],//搜索入门
        502:[297,330,"middle"],//DFS
        503:[395,355,"middle"],//BFS
        504:[368,262,"middle"],//搜索剪枝
        505:[259,208,"large"],//搜索合集

        601:[140,440,"small"],//DP入门
        602:[72,400,"small"],//线性DP
        603:[72,480,"small"],//背包问题
        604:[4,355,"small"],//树状DP
        605:[143,360,"small"],//区间DP
        606:[72,320,"small"],//概率DP
        607:[4,440,"small"],//状压DP

        999:[]
    },
    draggable:false
};
var challenge=function(){
    var $main=$('#challenge-main');
    $main.css("top",gv.offset[0]+"px").css("left",gv.offset[1]+"px");
    var $block=$('#challenge-block');
    var $info=$('#info');
    var get_jd=function(jd){
        //alert(jd);
        if(jd<=0.5){
            return "radial-gradient("+gv.bg_color+" 60%,"+gv.bg_color+" 60%,rgba(0,0,0,0) 63%)," +
                "linear-gradient(90deg, "+gv.jd_color2+" 50%, transparent 50%, transparent)," +
                "linear-gradient("+(jd*360+90)+"deg, "+gv.jd_color+" 50%, "+gv.jd_color2+" 50%, "+gv.jd_color2+")";
            /*
            linear-gradient(90deg, #5d6771 50%, transparent 50%, transparent),
            linear-gradient(194.4deg, #fffde8 50%, #5d6771 50%, #5d6771);*/
        }else{
            return "radial-gradient("+gv.bg_color+" 60%,"+gv.bg_color+" 60%,rgba(0,0,0,0) 63%)," +
                "linear-gradient(270deg, "+gv.jd_color+" 50%, transparent 50%, transparent)," +
                "linear-gradient("+((jd-0.5)*360+270)+"deg, "+gv.jd_color+" 50%, "+gv.jd_color2+" 50%, "+gv.jd_color2+")";
        }
    };
    var init2=function(showAll){
        var data=blockList;
        $main.html("");
        for(var i=0;i<data.blockList.length;i++){
            var id=data.blockList[i].id;
            //alert(id+" "+"("+gv.position[id][0]+","+gv.position[id][1]+")");
            var name=data.blockList[i].name;
            var jd=data.blockList[i].score==0?0:data.blockList[i].userScore/data.blockList[i].score;
            //isopen = 0、关闭  1、先决已经开启 2、可开启 3、已开启
            var isOpen=data.blockList[i].isOpen;
            var div="<div class='block'><div class='title'>"+name+"</div></div>";
            if(isOpen==3){
                $main.append((data.isSelf?HTML.a("#B"+id,div):div))
                    .find(".block").last()
                    .addClass("block-"+gv.position[id][2])
                    .css({top:gv.position[id][0]+"px",left:gv.position[id][1]+"px"})
                    .css({backgroundImage:get_jd(jd)});
            }else if(isOpen==2){
                $main.append((data.isSelf?HTML.a("#B"+id,div):div))
                    .find(".block").last()
                    .addClass("block-"+gv.position[id][2])
                    .css({top:gv.position[id][0]+"px",left:gv.position[id][1]+"px"})
                    .css({backgroundImage:get_jd(jd)});
            }else if(isOpen==1){
                $main.append(HTML.a("javascript:challenge.showBlockClose("+id+")","<div class='block block-close'><div class='title'>"+name+"</div></div>"))
                    .find(".block").last()
                    .addClass("block-"+gv.position[id][2])
                    .css({top:gv.position[id][0]+"px",left:gv.position[id][1]+"px"})
                    .css({backgroundImage:get_jd(0)});
            }else{
                if(showAll){
                    $main.append(HTML.a("javascript:challenge.showBlockClose("+id+")","<div class='block block-close'><div class='title'>"+name+"</div></div>"))
                        .find(".block").last()
                        .addClass("block-"+gv.position[id][2])
                        .css({top:gv.position[id][0]+"px",left:gv.position[id][1]+"px"})
                        .css({backgroundImage:get_jd(0)});
                }
            }
        }
        if(gv.draggable) $main.find('.block').draggable();//拖动
        $main.show();
        $block.hide();
    };
    var condition=function(s){
        //[{type,par,blockName,num}]
        var ret="";
        if(s.length==0){
            return "无";
        }
        for(var i=0;i< s.length;i++){
            ret+="在模块【"+ s[i].blockName+"】中获得【"+ s[i].num+"】积分<br>";
        }
        return ret;
    };
    var problem=function(s){
        var table={
            class:"table",
            head:["S","#","标题","积分"],
            body:[]
        };
        for(var i=0;i< s.length;i++){
            table.body[i]=[];
            table.body[i][0]=(s[i].solved==0?"":(s[i].solved==2?HTML.textb("✔","green"):HTML.textb("✘","red")));
            table.body[i][1]=s[i].tpid;
            table.body[i][2]=HTML.a("Problem.jsp?pid="+s[i].tpid,s[i].title);
            table.body[i][3]=s[i].score;
        }
        return TableHTML(table);
    };
    var showBlock=function(id){
        $main.hide();
        $block.show()
            .html(HTML.loader_h("600px"));
        //getJson
        $.getJSON("block.action?id="+id,function(data){
            //{id,name,group,text,score,userScore,conditions:[{type,par,blockName,num}],problemList:[{solved,pid,tpid,title,score}]}
            $block.html("")
                .append("<h2 style='text-align:center'>"+data.name+"</h2>")
                .append("<div class='col-xs-10 col-xs-offset-1'></div>").find('div').last()
                .append("<h4>完成进度：("+data.userScore+"/"+data.score+")</h4>"+"<p class='paragraph'>"+HTML.progress({jd:(data.score==0?0:data.userScore/data.score)})+"</p>")
                .append("<h4>模块说明：</h4>"+"<div class='paragraph'>"+data.text+"</div>")
                .append("<h4>开启条件：</h4>"+"<p class='paragraph'>"+condition(data.conditions)+"</p>")
                .append("<h4>题目列表：</h4>"+problem(data.problemList));
        });
    };
    var showBlockClose=function(id){
        var blockName="";
        for(var i=0;i<blockList.blockList.length;i++){
            if(blockList.blockList[i].id==id){
                blockName=blockList.blockList[i].name;
            }
        }
        var $modalbody=$info.html("").append(
            '<div class="modal fade" id="showblockclose" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">'+
            '<div class="modal-dialog" role="document">'+
            '<div class="modal-content">'+
            '<div class="modal-header">'+
            '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
            '<span aria-hidden="true">&times;</span>' +
            '</button>'+
            '<h4 class="modal-title" id="myModalLabel">模块【'+blockName+'】开启条件</h4>'+
            '</div>'+
            '<div class="modal-body">'+
            'Loading...'+
            '</div>'+
            '<div class="modal-footer">'+
            '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>'+
            '</div>'+
            '</div>'+
            '</div>'+
            '</div>'
        ).find('.modal-body');
        $.getJSON("blockCondition.action?id="+id,function(data){
            $modalbody.html(condition(data));
        });
        $('#showblockclose').modal();
    };
    return{
        $main:$main,
        $block:$block,
        $info:$info,
        init2:init2,
        showBlock:showBlock,
        showBlockClose:showBlockClose
    }
}();
var href=location.hash;
function go(){
    if(href.charAt(1)=='B'){
        var id=parseInt(href.substr(2,20));
        for(var i=0;i<blockList.blockList.length;i++){
            if(blockList.blockList[i].id==id){
                if(blockList.blockList[i].isOpen>=2){
                    challenge.showBlock(id);
                }else{
                    challenge.init2();
                    //challenge.showBlockClose(id);
                }
                break;
            }
        }
    }else if(href=='#ALL'){
        challenge.init2(true);
    }else{
        challenge.init2(false);
    }
}
$(window).on('hashchange', function() {
    href=location.hash;
    go();
});
$(function(){
    if(!blockList.isSelf){
        challenge.$main.before(HTML.center("<h3>"+blockList.user+"的挑战模式"+"</h3>"));
    }else{
        challenge.$main.before(HTML.center("<h3>挑战模式</h3>"));
    }
    go();
});