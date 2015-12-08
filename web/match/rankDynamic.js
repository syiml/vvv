/**
 * Created by Administrator on 2015/11/26 0026.
 */
var rankDynameick=function(){
    var rowNum=0;
    var height=25;
    var padding=2;
    var offset_x=10;
    var offset_y=10;
    function init(){
        $('.debug-switch').click(function(){
            if($(this).hasClass("open")){
                $('.debug').hide();
                $(this).removeClass("open").addClass("close");
            }else{
                $('.debug').show();
                $(this).removeClass("close").addClass("open");
            }
        });
        $('.chat-switch').click(function(){
            if($(this).hasClass("open")){
                $('.chat').hide();
                $(this).removeClass("open").addClass("close");
            }else{
                $('.chat').show();
                $(this).removeClass("close").addClass("open");
            }
        });
        $('.setting-switch').click(function(){
            if($(this).hasClass("open")){
                $('.setting').hide();
                $(this).removeClass("open").addClass("close");
            }else{
                $('.setting').show();
                $(this).removeClass("close").addClass("open");
            }
        });
        $('#chat-submit').click(function(){
            var $chatText=$('#chat-text');
            if($chatText.val().length>0){
                match.send($chatText.val());
                $chatText.val("");
            }
        });
        $('#setting-submit').click(function(){
            match.refresh();
        });
        $("#setting-autowidth").click(function(){
            autoWidth();
        });
    }
    init();
    function buildHead(pnum,head){
        rowNum=0;
        var $row=$("<div id='head' class='row head' style='left:"+offset_x+"px;top:"+(offset_y)+"px'></div>");
        for(var i=0;i<head.length;i++){
            if(head[i]=="rank"){
                $row.append("<div class='col rank'>#</div>");
            }else if(head[i]=="username"){
                $row.append("<div class='col username'>用户名</div>");
            }else if(head[i]=="S"){
                $row.append("<div class='col S'>S</div>");
            }else if(head[i]=="W"){
                $row.append("<div class='col W'>T</div>");
            }else if(head[i]=="nick"){
                $row.append("<div class='col nick'>昵称</div>");
            }else if(head[i]=="name"){
                $row.append("<div class='col name'>姓名</div>");
            }else if(head[i]=="gender"){
                $row.append("<div class='col gender'>性别</div>");
            }else if(head[i]=="faculty"){
                $row.append("<div class='col faculty'>学院</div>");
            }else if(head[i]=="major"){
                $row.append("<div class='col major'>专业</div>");
            }else if(head[i]=="cla"){
                $row.append("<div class='col cla'>班级</div>");
            }else if(head[i]=="no"){
                $row.append("<div class='col no'>学号</div>");
            }
        }
        for(var i=0;i<pnum;i++){
            $row.append("<div class='col pro'>"+String.fromCharCode(i+65)+"</div>")
        }
        var width=0;
        $(".main").append($row).find(".col").each(function(){
            width+=$(this).width();
            //alert($(this).css("width"));
            width+=padding;
        });
        $row.css("width",width+"px");
        return width;
    }
    function newRow(data,head,userinfo,width){
        //{rank,username,nick,name,gender,faculty,major,cla,no,S,W,score[[{rid,result,time}],[{rid,result,time}]]}
        rowNum++;
        var $row=$("<div id='row-"+data.rank+"' class='row row-"+data.username+"' style='left:"+offset_x+"px;top:"+(offset_y+(height+padding)*rowNum)+"px'></div>");
        var user;
        if(userinfo.hasOwnProperty(data.username))
            user=userinfo[data.username];
        else{
            user=null;
        }
        if(user==null){
            user={nick:"",name:"",gender:"",faculty:"",major:"",cla:"",no:""};
        }
        for(var i=0;i<head.length;i++){
            if(head[i]=="rank"){
                $row.append("<div class='col rank'>"+data.rank+"</div>");
            }else if(head[i]=="username"){
                $row.append("<div class='col username'>"+data.username+"</div>");
            }else if(head[i]=="S"){
                $row.append("<div class='col S'>"+data.S+"</div>");
            }else if(head[i]=="W"){
                $row.append("<div class='col W'>"+parseInt(data.W/60)+"</div>");
            }else if(head[i]=="nick"){
                $row.append("<div class='col nick'>"+user.nick+"</div>");
            }else if(head[i]=="name"){
                $row.append("<div class='col name'>"+user.name+"</div>");
            }else if(head[i]=="gender"){
                var text="";
                if(user.gender==1){
                    text="男"
                }else if(user.gender==2){
                    text="女";
                }
                $row.append("<div class='col gender'>"+text+"</div>");
            }else if(head[i]=="faculty"){
                $row.append("<div class='col faculty'>"+user.faculty+"</div>");
            }else if(head[i]=="major"){
                $row.append("<div class='col major'>"+user.major+"</div>");
            }else if(head[i]=="cla"){
                $row.append("<div class='col cla'>"+user.cla+"</div>");
            }else if(head[i]=="no"){
                $row.append("<div class='col no'>"+user.no+"</div>");
            }
        }

        for(var i=0;i<data.score.length;i++){
            var res=data.score[i];
            //[{rid,result,time}],[{rid,result,time}]
            var solvedTime=-1;
            var errorTime=0;
            var noResultTime=0;
            for(var j=0;j<res.length;j++){
                if(res[j].result==-1){
                    noResultTime++;
                }else if(res[j].result==1){
                    solvedTime=res[j].time;
                    break;
                }else if(res[j].result==0){
                    errorTime++;
                }
            }
            if(solvedTime!=-1){
                if(errorTime==0){
                    $row.append("<div class='col pro ac pro-"+i+"'>"+parseInt(solvedTime/60)+"</div>");
                }else{
                    $row.append("<div class='col pro ac pro-"+i+"'>"+parseInt(solvedTime/60)+"(-"+errorTime+")"+"</div>");
                }
            }else if(errorTime+noResultTime>0){
                if(noResultTime>0){
                    $row.append("<div class='col pro nores pro-"+i+"'>"+(-errorTime)+"(+"+noResultTime+")"+"</div>")
                }else{
                    $row.append("<div class='col pro wa pro-"+i+"'>"+(-errorTime)+"</div>");
                }
            }else{
                $row.append("<div class='col pro pro-"+i+"'></div>");
            }
        }
        $row.css("width",width+"px");
        $(".main").append($row).css("height",(rowNum+3)*(25+padding)+"px");
    }
    function editSW(username,S,W){
        var $row=$(".row-"+username);
        $row.find(".S").text(S);
        $row.find(".W").text(parseInt(W/60000));
    }
    function editCell(username,pid,data){
        //data=[{rid,result,time},{rid,result,time}]
        var $row=$(".row-"+username);

        var solvedTime=-1;
        var errorTime=0;
        var noResultTime=0;
        for(var j=0;j<data.length;j++){
            if(data[j].result==-1){
                noResultTime++;
            }else if(data[j].result==1){
                solvedTime=data[j].time;
                break;
            }else if(data[j].result==0){
                errorTime++;
            }
        }
        var $cell=$row.find(".pro-"+pid);
        if(solvedTime!=-1){
            if(errorTime==0){
                $cell.addClass("ac").removeClass("wa").removeClass("nores").text(parseInt(solvedTime/60000));
            }else{
                $cell.addClass("ac").removeClass("wa").removeClass("nores").text(parseInt(solvedTime/60000)+"(-"+errorTime+")");
            }
        }else if(errorTime+noResultTime>0){
            if(noResultTime>0){
                $cell.addClass("nores").removeClass("wa").removeClass("ac").text((-errorTime)+"(+"+noResultTime+")");
            }else{
                $cell.addClass("wa").removeClass("nores").removeClass("ac").text((-errorTime));
            }
        }
    }
    function moveRow(i,j){
        //i->j i>j
        //alert(i+"->"+j);
        var $div = $("#row-" + i);
        $div.animate({top:(offset_y+(height+padding)*j)},1000,function(){
            $div.css({zIndex:1});
        }).css({zIndex:100});
        for(var k=i-1;k>=j;k--){
            move(k,k+1);
        }
        $div.attr("id","row-"+j).find(".rank").text(j);
    }
    function move(i,j){
        $("#row-"+i).animate({top:(offset_y+(height+padding)*j)},1000).attr("id","row-"+j).find(".rank").text(j);
    }
    function log(s){
        $(".debug .body").append(s+"<br>");
    }
    function chat(user,text){
        $(".chat-body").append("<b>"+user+":</b>"+text+"<br>").scrollTop(1000000000);
    }
    function chat_log(text){
        $(".chat-body").append(text).scrollTop(1000000000);
    }
    function addOnline(user){
        var $ol=$(".online");
        if($ol.find("#online-"+user).length<=0){
            $ol.append("<div id='online-"+user+"'>"+user+"</div>");
        }
    }
    function delOnline(user){
        $(".online").find("#online-"+user).remove();
    }
    function autoWidth(){
        $(".col").css("width","auto");
        var widths=[];
        $(".row").each(function(i){
            $(this).find(".col").each(function(j){
                if(i==0){
                    widths[j]=$(this).width();
                }else{
                    widths[j]=$(this).width()>widths[j]?$(this).width():widths[j];
                }
            })
        });
        $(".row").each(function(i){
            $(this).find(".col").each(function(j){
                $(this).css("width",(widths[j]+5)+"px");
            })
        });

        var width=0;
        $(".head").find(".col").each(function(){
            width+=$(this).width();
            //alert($(this).css("width"));
            width+=padding;
        });
        $(".row").css("width",width+"px");
    }
    return {
        editSW:editSW,
        editCell:editCell,
        moveRow:moveRow,
        bulidHead:buildHead,
        newRow:newRow,
        log:log,
        chat:chat,
        addOnline:addOnline,
        delOnline:delOnline,
        chat_log:chat_log,
        autoWidth:autoWidth
    }
}();