/**
 * Created by Administrator on 2015/11/26 0026.
 */
var rankDynameick=function(){
    var rowNum=0;
    var height=25;
    var padding=3;
    var offset_x=10;
    var offset_y=10;
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
                    $row.append("<div class='col pro ac'>"+parseInt(solvedTime/60)+"</div>");
                }else{
                    $row.append("<div class='col pro ac'>"+parseInt(solvedTime/60)+"(-"+errorTime+")"+"</div>");
                }
            }else if(errorTime+noResultTime>0){
                if(noResultTime>0){
                    $row.append("<div class='col pro nores'>"+(-errorTime)+"(+"+noResultTime+")"+"</div>")
                }else{
                    $row.append("<div class='col pro wa'>"+(-errorTime)+"</div>");
                }
            }else{
                $row.append("<div class='col pro'></div>");
            }
        }
        $(".main").append($row);
    }
    function moveRow(i,j){//i->j i>j
        var $div = $("#row-" + i);
        $div.animate({top:(offset_y+(height+padding)*j)},1000,function(){
            $div.css({css:1});
        }).css({top:100});
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
    return {
        moveRow:moveRow,
        newRow:newRow,
        log:log
    }
}();