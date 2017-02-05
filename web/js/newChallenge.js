/**
 * Created by syimlzhu on 2017/2/4.
 */
var gv={
    jd_color:"#0a0",
    jd_color2:"#ddd",
    bg_color:"#fff",
    offset:[50,0],
    //position: position
    position:[
        {//每个是一个界面
            name:"总览",
            block_list:[
                {//0
                    type:"set",
                    text:"基础",
                    x:0,
                    y:0,
                    to:1//点击之后跳转到哪个界面
                },{//1
                    type:"set",
                    text:"数据结构",
                    x:300,
                    y:200,
                    to:2
                },{//2
                    type:"set",
                    text:"数学",
                    x:100,
                    y:200,
                    to:3
                },{//3
                    type:"set",
                    text:"几何",
                    x:200,
                    y:400,
                    to:4
                },{//4
                    type:"set",
                    text:"图论",
                    x:400,
                    y:400,
                    to:5
                },{//5
                    type:"set",
                    text:"搜索",
                    x:300,
                    y:-200,
                    to:6
                },{//6
                    type:"set",
                    text:"动态规划",
                    x:100,
                    y:-200,
                    to:7
                },{//7
                    type:"set",
                    text:"基础进阶",
                    x:200,
                    y:0,
                    to:8
                }
            ],
            line_list:[//表示第几个block和第几个block相连
                [7,1],
                [0,2],
                [2,3],
                [1,4],
                [7,5],
                [0,6],
                [0,7]
            ]
        },{
            name:"基础",
            block_list:[
                {//0
                    type:"block",
                    x:0,
                    y:0,
                    to:1,//入门
                    size:"small"
                },{//1
                    type:"block",
                    x:80,
                    y:0,
                    to:2,//基础
                    size:"small"
                },{//2
                    type:"block",
                    x:190,
                    y:240,
                    to:3,//枚举
                    size:"middle"
                },{//3
                    type:"block",
                    x:190,
                    y:-80,
                    to:4,//排序
                    size:"middle"
                },{//4
                    type:"block",
                    x:190,
                    y:80,
                    to:5,//贪心
                    size:"middle"
                },{//5
                    type:"block",
                    x:190,
                    y:-240,
                    to:6,//递推
                    size:"middle"
                },{//6
                    type:"block",
                    x:400,
                    y:0,
                    to:7,//基础题集合
                    size:"large"
                },{//7
                    type:"block",
                    x:40,
                    y:160,
                    to:201,//数学入门
                    size:"small"
                },{//8
                    type:"set",
                    x:40,
                    y:370,
                    to:3,//数学
                    text:"数学"
                },{//9
                    type:"set",
                    x:550,
                    y:0,
                    to:8,//基础进阶
                    text:"基础进阶"
                },{//10
                    type:"set",
                    x:250,
                    y:-400,
                    to:7,//动态规划
                    text:"动态规划"
                },{//11
                    type:"block",
                    x:265,
                    y:-100,
                    to:10,//高级排序
                    size:"small"
                }
            ],
            line_list:[//表示第几个block和第几个block相连
                [0,1],[1,2],[1,3],[1,4],[1,5],[6,2],[6,3],[6,4],[6,5],[7,1],[7,8],[6,9],[5,10],[3,11]
            ]
        },{
            name:"数据结构",
            block_list:[
                {//0
                    type:"set",
                    x:0,
                    y:0,
                    to:9,//基础进阶
                    text:"基础进阶"
                },{//1
                    type:"block",
                    x:100,
                    y:0,
                    to:101,//入门
                    size:"small"
                },{//2
                    type:"block",
                    x:150,
                    y:-200,
                    to:110,//链结构
                    size:"middle"
                },{//3
                    type:"block",
                    x:180,
                    y:200,
                    to:120,//树结构
                    size:"middle"
                },{//4
                    type:"block",
                    x:300,
                    y:-280,
                    to:111,//栈
                    size:"small"
                },{//5
                    type:"block",
                    x:260,
                    y:-80,
                    to:112,//队列
                    size:"small"
                },{//6
                    type:"block",
                    x:360,
                    y:20,
                    to:121,//优先队列
                    size:"small"
                },{//7
                    type:"block",
                    x:400,
                    y:140,
                    to:126,//平衡树
                    size:"middle"
                },{//8
                    type:"block",
                    x:380,
                    y:230,
                    to:123,//线段树
                    size:"small"
                },{//9
                    type:"block",
                    x:160,
                    y:380,
                    to:122,//并查集
                    size:"small"
                },{//10
                    type:"block",
                    x:360,
                    y:310,
                    to:124,//树状数组
                    size:"small"
                },{//11
                    type:"block",
                    x:50,
                    y:360,
                    to:125,//字典树
                    size:"small"
                },{//12
                    type:"set",
                    x:330,
                    y:430,
                    to:5,//图论
                    text:"图论"
                }
            ],
            line_list:[
                [0,1],[1,2],[1,3],[2,4],[2,5],[3,6],[5,6],[3,7],[3,8],[3,9],[3,10],[3,11],[3,12]
            ]
        },{
            name:"数学",
            block_list:[
                {//0
                    type:"set",
                    x:0,
                    y:0,
                    to:1,//基础
                    text:"基础"
                },{//1
                    type:"block",
                    x:20,
                    y:-150,
                    to:241,//组合入门
                    size:"small"
                },{//2
                    type:"block",
                    x:20,
                    y:150,
                    to:231,//概率论入门
                    size:"small"
                },{//3
                    type:"block",
                    x:110,
                    y:-80,
                    to:251,//矩阵入门
                    size:"small"
                },{//4
                    type:"block",
                    x:110,
                    y:80,
                    to:211,//素数
                    size:"small"
                },{//5
                    type:"block",
                    x:180,
                    y:0,
                    to:212,//同余
                    size:"small"
                },{//6
                    type:"block",
                    x:310,
                    y:0,
                    to:217,//快速幂
                    size:"small"
                },{//7
                    type:"block",
                    x:210,
                    y:100,
                    to:214,//GCD
                    size:"small"
                },{//8
                    type:"block",
                    x:120,
                    y:170,
                    to:213,//唯一分解
                    size:"small"
                },{//9
                    type:"block",
                    x:180,
                    y:250,
                    to:215,//欧拉函数
                    size:"small"
                },{//10
                    type:"block",
                    x:310,
                    y:110,
                    to:216,//扩展GCD
                    size:"small"
                },{//11
                    type:"block",
                    x:390,
                    y:160,
                    to:218,//逆元
                    size:"small"
                },{//12
                    type:"block",
                    x:450,
                    y:250,
                    to:220,//中国剩余
                    size:"small"
                },{//13
                    type:"block",
                    x:450,
                    y:40,
                    to:219,//米勒罗宾
                    size:"small"
                },{//14
                    type:"block",
                    x:210,
                    y:-140,
                    to:252,//矩阵基本运算
                    size:"small"
                },{//15
                    type:"block",
                    x:390,
                    y:-100,
                    to:252,//矩阵快速幂
                    size:"small"
                },{//16
                    type:"block",
                    x:290,
                    y:-220,
                    to:254,//高斯消元
                    size:"small"
                }
            ],
            line_list:[//表示第几个block和第几个block相连
                [0,1],[0,2],[0,3],[0,4],[0,5],[5,6],[4,7],[4,8],[8,9],[7,10],[10,11],[11,12],[6,13],[11,13],[3,14],
                [14,15],[6,15],[14,16]
            ]
        },{
            name:"几何",
            block_list:[
                {//0
                    type:"set",
                    x:0,
                    y:0,
                    to:3,//数学
                    text:"数学"
                }
            ],
            line_list:[//表示第几个block和第几个block相连
            ]
        },{
            name:"图论",
            block_list:[
                {//0
                    type:"set",
                    x:0,
                    y:0,
                    to:2,//数据结构
                    text:"数据结构"
                }
            ],
            line_list:[//表示第几个block和第几个block相连
            ]
        }
    ]
};

var challenge=function(){
    var $main=$('#challenge-main');
    $main.css("top",gv.offset[0]+"px").css("left",gv.offset[1]+"px");
    var $block=$('#challenge-block');
    var $info=$('#info');
    var isAdmin=false;
    var svg = document.getElementById('challenge-svg');
    function line(x1,y1,x2,y2){
        var c = document.createElementNS('http://www.w3.org/2000/svg','line');
        c.setAttribute('x1', y1+500);
        c.setAttribute('y1', x1);
        c.setAttribute('x2', y2+500);
        c.setAttribute('y2', x2);
        c.setAttribute('style', "stroke:#000;stroke-width:2");
        //c.r.baseVal.value = 7;
        c.setAttribute('fill', 'green');
        svg.appendChild(c);
    }
    function cleanLine() {
        $(svg).find("line").remove();
    }

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

    var init2=function(show_id,showAll){
        var data=blockList;
        var width={
            small:60,
            middle:80,
            large:100,
            set:100
        };
        $main.find("a").remove();
        for(var i = 0 ; i< gv.position[show_id].block_list.length; i++){
            var block = gv.position[show_id].block_list[i];
            if(block.type == "block"){
                var id=block.to;
                var j;
                for(j = 0 ; j< data.blockList.length; j++){
                    if(data.blockList[j].id == id) break;
                }
                var name=data.blockList[j].name;
                var jd=data.blockList[j].score==0?0:data.blockList[i].userScore/data.blockList[j].score;
                //isopen = 0、关闭  1、先决已经开启 2、可开启 3、已开启
                var isOpen=data.blockList[j].isOpen;
                var div="<div class='block'><div class='title'>"+name+"</div></div>";

                if(isOpen==3){
                    $main.append((data.isSelf?HTML.a("#B"+id,div):div))
                        .find(".block").last()
                        .addClass("block-"+block.size).attr('id',id)
                        .css({top:block.x - width[block.size]/2,left:block.y- width[block.size]/2})
                        .css({backgroundImage:get_jd(jd)});
                }else if(isOpen==2){
                    $main.append((data.isSelf?HTML.a("#B"+id,div):div))
                        .find(".block").last()
                        .addClass("block-"+block.size).attr('id',id)
                        .css({top:block.x - width[block.size]/2,left:block.y- width[block.size]/2})
                        .css({backgroundImage:get_jd(jd)});
                }else if(isOpen==1){
                    $main.append(HTML.a("javascript:challenge.showBlockClose("+id+")","<div class='block block-close'><div class='title'>"+name+"</div></div>"))
                        .find(".block").last()
                        .addClass("block-"+block.size).attr('id',id)
                        .css({top:block.x - width[block.size]/2,left:block.y- width[block.size]/2})
                        .css({backgroundImage:get_jd(0)});
                }else{
                    //if(showAll){
                        $main.append(HTML.a("javascript:challenge.showBlockClose("+id+")","<div class='block block-close'><div class='title'>"+name+"</div></div>"))
                            .find(".block").last()
                            .addClass("block-"+block.size).attr('id',id)
                            .css({top:block.x - width[block.size]/2,left:block.y- width[block.size]/2})
                            .css({backgroundImage:get_jd(0)});
                    //}
                }
            }else{
                name = block.text;
                div="<div class='block'><div class='title'>"+name+"</div></div>";
                $main.append(HTML.a("#T"+block.to,div))
                    .find(".block").last()
                    .addClass("block-set").attr('id',"T"+show_id)
                    .css({top:block.x  - width.set/2 ,left:block.y - width.set/2,backgroundColor:'#fff',border:"solid 2px #000"});
            }
        }
        cleanLine();
        for(i = 0 ; i< gv.position[show_id].line_list.length; i++) {
            var lineData = gv.position[show_id].line_list[i];
            var from = gv.position[show_id].block_list[lineData[0]];
            var to = gv.position[show_id].block_list[lineData[1]];
            var offset_from,offset_to;
            if(from.type == "set"){
                offset_from = width.set;
            }else{
                offset_from = width[from.size];
            }
            if(to.type == "set"){
                offset_to = width.set;
            }else{
                offset_to = width[to.size];
            }
            line(from.x ,from.y,to.x,to.y);
        }
        $main.show();
        $block.hide();
    };
    /*var init2=function(showAll){
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
            if(!(id in gv.position)){
                gv.position[id]=[];
                gv.position[id][0]="0px";
                gv.position[id][1]="0px";
                gv.position[id][2]="small";
            }
            if(isOpen==3){
                $main.append((data.isSelf?HTML.a("#B"+id,div):div))
                    .find(".block").last()
                    .addClass("block-"+gv.position[id][2]).attr('id',id)
                    .css({top:gv.position[id][0],left:gv.position[id][1]})
                    .css({backgroundImage:get_jd(jd)});
            }else if(isOpen==2){
                $main.append((data.isSelf?HTML.a("#B"+id,div):div))
                    .find(".block").last()
                    .addClass("block-"+gv.position[id][2]).attr('id',id)
                    .css({top:gv.position[id][0],left:gv.position[id][1]})
                    .css({backgroundImage:get_jd(jd)});
            }else if(isOpen==1){
                $main.append(HTML.a("javascript:challenge.showBlockClose("+id+")","<div class='block block-close'><div class='title'>"+name+"</div></div>"))
                    .find(".block").last()
                    .addClass("block-"+gv.position[id][2]).attr('id',id)
                    .css({top:gv.position[id][0],left:gv.position[id][1]})
                    .css({backgroundImage:get_jd(0)});
            }else{
                if(showAll){
                    $main.append(HTML.a("javascript:challenge.showBlockClose("+id+")","<div class='block block-close'><div class='title'>"+name+"</div></div>"))
                        .find(".block").last()
                        .addClass("block-"+gv.position[id][2]).attr('id',id)
                        .css({top:gv.position[id][0],left:gv.position[id][1]})
                        .css({backgroundImage:get_jd(0)});
                }
            }
        }
        $main.show();
        $block.hide();
    };*/
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
    function save(){
        isAdmin=false;
        challenge.$main.find('.block').draggable("disable");//拖动
        $(".edit").html(HTML.a("#ADMIN","编辑"));
        var fuck={};
        $main.find('.block').each(function () {
            var $this=$(this);
            var size;
            if($this.hasClass("block-large")) size="large";
            else if($this.hasClass("block-middle")) size="middle";
            else size="small";
            fuck[$this.attr('id')]=[$this.css("top"),$this.css("left"),size];
        });
        $.post("editChallenge.action",{text:JSON.stringify(fuck)},function(){
            alert("保存成功");
            location.href="Challenge.jsp";
        });

        //alert(JSON.stringify(fuck));
    }
    return{
        $main:$main,
        $block:$block,
        $info:$info,
        init2:init2,
        showBlock:showBlock,
        showBlockClose:showBlockClose,
        isAdmin:isAdmin,
        save:save
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
    }/*else if(href=='#ALL') {
        challenge.init2(true);
    }else if(href=='#ADMIN'){
        challenge.init2(true);
        challenge.$main.find('.block').draggable({ distance: 0 });//拖动
        $(".edit").html(HTML.a("javascript:challenge.save()","保存"));
        challenge.isAdmin=true;
    }*/else if(href.charAt(1)=='T'){
        id=parseInt(href.substr(2,20));
        if(!id) id = 0;
        challenge.init2(id,false);
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
        challenge.$main.before(HTML.center("<h3>挑战模式</h3> "+(admin?HTML.div("edit","",HTML.a("#ADMIN","编辑")):"")));
    }
    go();
});