/**
 * Created by QAQ on 2016/5/30 0030.
 */
var href=location.hash;
if(href==null||href==''){ href='#H'}
$.getJSON("module/Exam/examInfo.jsp?id="+js_id,function(data) {
    examInfo = data;
    init();
});
var text={
    home:"主页",
    problem:"题目",
    admin:"后台管理"
};
function init(){
    $("#examMain").append("<div id='NAV'></div><div id='main'></div><div id='problems'></div>");
    loadNAV();
    $.getJSON("module/contestNew/HomeProblemList.jsp?cid="+js_id, function (data) {
        pnum=data.length;
        go();
    });
}
function go(){
    if(href=='#P'){
        loadProblem(0);
    }else if(href.charAt(1)=='P'){
        loadProblem(parseInt(href.substr(2,20)));
    }else if(href=="#ADMIN"){
        loadAdmin();
    }else{
        loadHome();
    }
}
function loadNAV(){
    var $nav = $('#NAV').append("<ul class='nav nav-tabs'></ul>").find('.nav');
    $nav.append("<li role='presentation' id='homeNAV'>"+HTML.a("#H",text.home)+"</li>");
    if(examInfo.now>=examInfo.begintime) {//not pendding
        $nav.append("<li role='presentation' id='problemNAV'>" + HTML.a("#P", text.problem) + "</li>");
    }
    if(examInfo.admin){
        $nav.append("<li role='presentation' id='adminNAV'>"+HTML.a("#ADMIN",text.admin)+"</li>");
    }
}
function loadProblem(pid){
    $('#main').hide();
    var $problems=$('#problems');
    $problems.show();
    $('#NAV').find('li').removeClass("active");
    $('#problemNAV').addClass("active");

    $problems.html('');

    var $pnav=$problems.append("<ul class='nav nav-pills'></ul>").find('ul');
    if(pid==null) pid=0;
    for(var i=0;i<examInfo.problems.length;i++){
        $pnav.append("<li role='persentating'>"+HTML.a("#P"+i+"",(i+1))+"</li>");
        if(i==pid){
            $pnav.find('li').last().addClass('active');
        }
    }
    setProblemView(pid,$problems.append('<div id="problemView"></div>').find('#problemView'),examInfo.problems[pid]);
}
function setProblemView(pid,$div,data){
    $div.html("");
    if(data.type==0){//选择题
        $div.append(data.description);
        for(var i=0;i<data.ans.length;i++){
            if(data.isRadio){
                $div.append("<div class='radio'><label><input type='radio' name='answer' id='answer"+i+"' value='"+i+"'>"+data.ans[i]+"</label></div>");
            }else{
                $div.append("<div class='checkbox'><label><input type='checkbox' name='answer"+i+"' class='answer'>"+data.ans[i]+"</label></div>");
            }
        }
        if(data.isRadio){
            $div.append(HTML.abtn("sm","javascript:submit("+pid+",0)","确定",""));
        }else{
            $div.append(HTML.abtn("sm","javascript:submit("+pid+",1)","确定",""));
        }
        //submit
    }else if(data.type==1){//代码填空
        $div.append(data.description);
        var $pre=$div.append("<pre class='form-inline'></pre>").find("pre");
        for(var i=0;i<data.codes.length;i++){
            if(i!=0){
                $pre.append("<div class='form-group'><input type='text' class='form-control answer input-answer' ></div>");
            }
            $pre.append(HTML.HTMLtoString(data.codes[i]));
        }
        $div.append(HTML.abtn("sm","javascript:submit("+pid+",2)","确定",""));
    }else if(data.type==2){//填空题
        $div.append(data.description);
        $div.append('<input type="text" class="form-control answer" id="answer" placeholder="输入你的答案,不要有任何多余的字符">');
        $div.append(HTML.abtn("sm","javascript:submit("+pid+",3)","确定",""));
    }else if(data.type==3){//编程题
        $div.append(data.description);
        $div.append('<textarea name="code" class="form-control answer" cols="80" rows="15" id="codeinput" placeholder="Put your code here"></textarea>');
        $div.append(HTML.abtn("sm","javascript:submit("+pid+",3)","确定",""));
    }
}
function submit(pid,type){
    var answer="";
    if(type==0){//单项选择题
        answer=$('input[name="answer"]:checked').val()+"";
    }else if(type==1){//多选
        var $answer=$('.answer');
        var i=0;
        answer+="[";
        var bo=true;
        $answer.each(function(){
            if($(this).is(":checked")==true){
                if(bo){
                    answer+=i;
                    bo=false;
                }
                else{
                    answer+=","+i;
                }
            }
            i++;
        });
        answer+="]";
    }else if(type==2){//代码填空
        answer=[];
        $(".answer").each(function(){
            answer.push($(this).val());
        });
        answer=JSON.stringify(answer);
    }else{
        answer=$('.answer').val();
    }
    alert(answer);
}
var runtime_ff,runtimett,isrun=false;
function loadHome(){
    var $main=$('#main');
    $('#problems').hide();
    $('#NAV').find('li').removeClass("active");
    $('#homeNAV').addClass("active");

    $main=$main.show().html(HTML.row(HTML.col(10,1,"","<div class='panel-body'></div>"))).find(".panel-body");
    $main.append("<div class='contestHomeText'><h4>"+"考试时间："+HTML.time(examInfo.begintime)+" ～ "+HTML.time(examInfo.endtime)+"</h4></div>");
    //.html("<img src='pic/loading.jpg'>");
    if(examInfo.now<examInfo.begintime){//pendding
        $main.append("<div class='contestHomeText'><h4>"+("考试状态：")+(HTML.textb("PENDDING","green"))+"</h4></div>");
        $main.append("<div class='contestHomeText'><h4>"+("倒计时　：")+("<text id='runtime'></text>")+"</h4></div>");
        runtimett=(examInfo.begintime-examInfo.now)/1000;
        runtime_ff=function() {
            if(runtimett>=0){
                var d=Math.floor(runtimett/86400);
                var time=(runtimett+16*3600);
                $('#runtime').text((d>0?d+'days ':'')+new Date(time*1000).Format('hh:mm:ss'));
                runtimett--;
            }
        };
        runtime_ff();
        if(!isrun){setInterval("runtime_ff()" , 1000 );isrun=true;}
    }else if(examInfo.now<examInfo.endtime){//running
        $main.append("<div class='contestHomeText'><h4>"+("考试状态：")+(HTML.textb("RUNNING","red"))+"</h4></div>");
    }else{//end
        $main.append("<div class='contestHomeText'><h4>"+("考试状态：")+(HTML.textb("END","black"))+"</h4></div>");
    }
    //alert(data.info);
    if(examInfo.info!=null&&examInfo.info!="") $main.append("<div class='contestHomeText'><h4>"+("考试说明：")+(examInfo.info)+"</h4></div><div style='height:20px'></div>");
}
$(window).on('hashchange', function() {
    href=location.hash;
    go();
});