/**
 * Created by Administrator on 2015/11/26 0026.
 */
var rankDynameick=function(){
    var rowNum=0;
    var height=25;
    var padding=2;
    var offset_x=10;
    var offset_y=10;
    var width;
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
        $('#chat-submit').click(function(){
            match.send($('#chat-text').val());
            $('#chat-text').val("");
        });
    }
    init();
    function buildHead(pnum){
        width=30+150+30+50+pnum*70+(pnum+4)*padding;
        var $row=$("<div id='head' class='row head' style='left:"+offset_x+"px;top:"+(offset_y)+"px'></div>");
        $row.append("<div class='col rank'>#</div>")
            .append("<div class='col username'>username</div>")
            .append("<div class='col S'>S</div>")
            .append("<div class='col W'>W</div>");
        for(var i=0;i<pnum;i++){
            $row.append("<div class='col pro'>"+String.fromCharCode(i+65)+"</div>")
        }
        $row.css("width",width+"px");
        $(".main").append($row);
    }
    function newRow(data){
        //{rank,username,nick,name,gender,faculty,major,cla,no,S,W,score[[{rid,result,time}],[{rid,result,time}]]}
        rowNum++;
        var $row=$("<div id='row-"+data.rank+"' class='row row-"+data.username+"' style='left:"+offset_x+"px;top:"+(offset_y+(height+padding)*rowNum)+"px'></div>");
        $row.append("<div class='col rank'>"+data.rank+"</div>");
        $row.append("<div class='col username'>"+data.username+"</div>");
        //$row.append("<div class='col nick'>"+data.nick+"</div>");

        $row.append("<div class='col S'>"+data.S+"</div>");
        $row.append("<div class='col W'>"+parseInt(data.W/60)+"</div>");
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
        $(".chat-body").append("<b>"+user+":</b>"+text+"<br>").scrollTop(10000);
    }
    function chat_log(text){
        $(".chat-body").append(text);
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
        chat_log:chat_log
    }
}();