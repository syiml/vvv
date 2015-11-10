/**
 * Created by Syiml on 2015/11/3 0003.
 */
var autoRefreshTable=function(url,sel,sel2){
    var time=3000;
    var maxLength=20;
    var getTable=function(url,sel,$table){
        var $buff=$("<div></div>");
        $buff.load(url+" "+sel,function(){
            var newRows=[];
            $buff.find('tr').each(function(index,data){
                var $newRow = $(data);
                var isNew=true;
                $table.find('tr').each(function(index,data){
                    if($(data).find('td').first().text()==$newRow.find('td').first().text()){
                        isNew=false;
                        for(var i=0;i<=8;i++){
                            var $td=$(data).find('td').eq(i);
                            var phtml=$td.html();
                            var nhtml=$newRow.find('td').eq(i).html();
                            if(phtml!=nhtml){
                                if($td.text()!=$newRow.find('td').eq(i).text()){
                                    var pbc=$td.css("backgroundColor");
                                    $td.css({backgroundColor:"#f44"}).animate({backgroundColor:pbc},800);
                                }
                                $td.html($newRow.find('td').eq(i).html());
                            }
                        }
                    }
                });
                if(isNew){
                    newRows.push($newRow);
                }
            });
            for(var i=newRows.length-1;i>=0;i--){
                if($table.find('tbody tr').length>=maxLength) $table.find('tr').last().remove();
                var $tr=$table.prepend(newRows[i]).find('tbody tr').first();
                $tr.find('td').each(function(index,data){
                    $(data).html("<div>"+$(data).html()+"</div>");
                });

                $tr.find('td>div').hide();
                $tr.find('td').hide();
                $tr.find('td,td>div').slideToggle(1000);
                //var pbc=$tr.css("backgroundColor");
                //$tr.css({backgroundColor:"#f44"}).animate({backgroundColor:pbc},800);
            }
        });
    };
    var ARer;
    var go=function(x){
        ARer=window.setInterval(x+".getTable('"+url+"','"+sel+"',$('"+sel2+"'))",time);
    };
    var stop=function(){
        clearInterval(ARer);
    };
    return {
        time:time,
        url:url,
        sel:sel,
        go:go,
        getTable:getTable,
        stop:stop,
        ARer:ARer,
        maxLength:maxLength
    }
};