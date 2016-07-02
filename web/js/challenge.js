/**
 * Created by Syiml on 2015/10/10 0010.
 */
var gv={
    jd_color:"#0a0",
    jd_color2:"#ddd",
    bg_color:"#fff",
    offset:[0,-650],
    position: position
};
var challenge=function(){
    var $main=$('#challenge-main');
    $main.css("top",gv.offset[0]+"px").css("left",gv.offset[1]+"px");
    var $block=$('#challenge-block');
    var $info=$('#info');
    var isAdmin=false;
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
    }else if(href=='#ALL') {
        challenge.init2(true);
    }else if(href=='#ADMIN'){
        challenge.init2(true);
        challenge.$main.find('.block').draggable({ distance: 0 });//拖动
        $(".edit").html(HTML.a("javascript:challenge.save()","保存"));
        challenge.isAdmin=true;
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
        challenge.$main.before(HTML.center("<h3>挑战模式</h3> "+(admin?HTML.div("edit","",HTML.a("#ADMIN","编辑")):"")));
    }
    go();
});