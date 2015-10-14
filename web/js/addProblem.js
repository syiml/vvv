/**
 * Created by Syiml on 2015/6/15 0015.
 */
$('#getProblemTitle').click(function(){
    var oj=$('#ojid').val();
    var ojspid=$('#ojspid').val();
    $.getJSON('module/getProblemTitle.jsp?oj='+oj+'&pid='+ojspid,function(data){
        $('#title').val(data.title);
        if(data.list.length!=0){
            var $append=$('#buff');
            $append.text("已有题目：");
            for(var i in data.list){
                $append.append(" "+HTML.anew("Problem.jsp?pid="+data.list[i],data.list[i]));
            }
        }else{
            $('#buff').text("");
        }
    });
});