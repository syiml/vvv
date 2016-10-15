/**
 * Created by Syiml on 2015/7/21 0021.
 */
var cid=js_cid;
var pnum=0;
var contestInfo;//{cid,name,begintime,endtime,type,now,admin,rating}
var href=location.hash;
var AR=null;
if(href==null||href==''){ href='#H'}
$.getJSON("module/contestNew/info.jsp?cid="+cid,function(data) {
    contestInfo = data;
    init();
});
function init(){
    $("#contestMain").append("<div id='NAV'></div><div id='main'></div><div id='problems'></div><div id='info'></div>");
    loadNAV();
    $.getJSON("module/contestNew/HomeProblemList.jsp?cid="+cid, function (data) {
        pnum=data.length;
        go();
    });
}
function go(){
    if(AR!=null) AR.stop();
    if(href=='#P'){
        loadProblem(0);
    }else if(href.charAt(1)=='P'){
        loadProblem(parseInt(href.substr(2,20)));
    }else if(href=='#S'){
        loadStatus();
    }else if(href=='#SA'){
        loadStatus();
        AR=autoRefreshTable("module/contestNew/status.jsp?cid="+cid,"table",'table');
        AR.go("AR");
    }else if(href.charAt(1)=='S'){
        loadStatus(href.substr(2,20));
    }else if(href=='#R'){
        loadRank();
    }else if(href.charAt(1)=='R'){
        loadRank(href.substr(2,20));
    }else if(href=='#T'){
        loadRating();
    }else if(href=='#C'){
        loadCompare();
    }else if(href.charAt(1)=='D'){
        if(href.charAt(2)=='P'){
            loadDiscussList(href.substr(3,20));
        }else{
            loadDiscuss(href.substr(2,20));
        }
    }else if(href=="#ADMIN"){
        loadAdmin();
    }else{
        loadHome();
    }
}
var text={
    home:"主页",
    problem:"题目描述",
    status:"在线评测",
    rank:"实时排名",
    rating:"rating",
    codeCompare:"代码判重",
    discuss:"在线讨论",
    admin:"后台管理"
};
function loadNAV(){
    var $nav = $('#NAV').append("<ul class='nav nav-tabs'></ul>").find('.nav');
    $nav.append("<li role='presentation' id='homeNAV'>"+HTML.a("#H",text.home)+"</li>");
    if(contestInfo.now>=contestInfo.begintime) {//not pendding
        $nav.append("<li role='presentation' id='problemNAV'>" + HTML.a("#P", text.problem) + "</li>");
        $nav.append("<li role='presentation' id='statusNAV'>" + HTML.a("#S", text.status) + "</li>");
        $nav.append("<li role='presentation' id='rankNAV'>" + HTML.a("#R", text.rank) + "</li>");
    }
    $nav.append("<li role='presentation' id='discussNAV'>"+HTML.a("#DP1",text.discuss)+"</li>");
    if(contestInfo.now>=contestInfo.begintime) {//not pendding
        if(contestInfo.rating){
            $nav.append("<li role='presentation' id='ratingNAV'>"+HTML.a("#T",text.rating)+"</li>");
        }
        if(contestInfo.compare){
            $nav.append("<li role='presentation' id='compareNAV'>"+HTML.a("#C",text.codeCompare)+"</li>");
            $nav.append("<li role='presentation' id='adminNAV'>"+HTML.a("#ADMIN",text.admin)+"</li>");
        }
    }
}
function loadProblemList($main,rollback){
    $.getJSON("module/contestNew/HomeProblemList.jsp?cid="+cid, function (data) {
        if(data.length!=0){
            //problemJson: [{status,pid,title,acnum,submitnum},...]
            var $problemList=$main.append("<div id='problemList'></div>").find('#problemList');
            var table={
                class:"table table-hover ",//table-bordered table-striped
                head:["S","#","Title","R"],
                body:[]
            };
            for(var i=0;i<data.length;i++){
                var row=[];
                if(data[i].result==1){
                    row.push(HTML.textb("✔","green"));
                }else if(data[i].result==0){
                    row.push(HTML.textb("✘","red"));
                }else row.push("");
                if(data.length>26){
                    row.push(i+1);
                }else{
                    row.push(String.fromCharCode(i+65));
                }
                row.push(HTML.a("#P"+i+"",data[i].title) );
                var ratio=(data[i].acnum/data[i].submitnum*100).toFixed(2);
                row.push((data[i].submitnum==0?"0.00":ratio)+"%("+data[i].acnum+"/"+data[i].submitnum+")");
                table.body.push(row);
            }
            pnum=data.length;
            $problemList.append(HTML.row(HTML.col(12,0,"",TableHTML(table))));
            if(rollback) rollback();
        }
    });
}
var runtime_ff,runtimett,isrun=false;
function loadHome(rollback){
    $.getJSON("module/contestNew/info.jsp?cid="+cid,function(data){
        contestInfo=data;
        var $main=$('#main');
        $('#problems').hide();
        $('#NAV').find('li').removeClass("active");
        $('#homeNAV').addClass("active");
        $main=$main.show().html(HTML.row(HTML.col(10,1,"","<div class='panel-body'></div>"))).find(".panel-body");
        $main.append("<div class='contestHomeText'><h4>"+"比赛时间："+HTML.time(contestInfo.begintime)+" ～ "+HTML.time(contestInfo.endtime)+"</h4></div>");
        //.html("<img src='pic/loading.jpg'>");
        if(contestInfo.now<contestInfo.begintime){//pendding
            $main.append("<div class='contestHomeText'><h4>"+("比赛状态：")+(HTML.textb("PENDDING","green"))+"</h4></div>");
            $main.append("<div class='contestHomeText'><h4>"+("倒计时　：")+("<text id='runtime'></text>")+"</h4></div>");
             runtimett=(contestInfo.begintime-contestInfo.now)/1000;
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
        }else if(contestInfo.now<contestInfo.endtime){//running
            $main.append("<div class='contestHomeText'><h4>"+("比赛状态：")+(HTML.textb("RUNNING","red"))+"</h4></div>");
        }else{//end
            $main.append("<div class='contestHomeText'><h4>"+("比赛状态：")+(HTML.textb("END","black"))+"</h4></div>");
        }
        //alert(data.info);
        if(data.info!=null&&data.info!="") $main.append("<div class='contestHomeText'><h4>"+("比赛说明：")+(data.info)+"</h4></div><div style='height:20px'></div>");
        loadProblemList($main,rollback);
    });
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
    for(var i=0;i<pnum;i++){
        if(pnum>26){
            $pnav.append("<li role='persentating'>"+HTML.a("#P"+i+"",(i+1))+"</li>");
        }else{
            $pnav.append("<li role='persentating'>"+HTML.a("#P"+i+"",String.fromCharCode(i+65))+"</li>");
        }
        if(i==pid){
            $pnav.find('li').last().addClass('active');
        }
    }
    $problems.append('<div id="problemView"></div>').find('#problemView').html(HTML.loader)
        .load('module/contestNew/ProblemViewer.jsp?cid='+cid+'&pid='+pid);
}
var user="";
var pid="";
var result="";
var lang="";
var page=0;
function nextpage(){
    loadStatus(user,pid,result,lang,page+1);
}
function toppage(){
    loadStatus(user,pid,result,lang,0);
}
function prepage(){
    loadStatus(user,pid,result,lang,page-1);
}
function topage(pa){
    loadStatus(user,pid,result,lang,pa);
}
function seachStatus(){
    user=$('#user').val();
    pid=$('#pid').val();
    result=$('#result').val();
    lang=$('#lang').val();
    loadStatus(user,pid,result,lang);
}
function loadStatus(_user,_pid,_result,_lang,_page){
    if(!_user) _user="";
    if(!_pid) _pid="";
    if(!_result) _result="";
    if(!_lang) _lang="";
    if(!_page) _page=1;
    user=_user;pid=_pid;result=_result;lang=_lang;page=_page;
    $('#NAV').find('li').removeClass("active");
    $('#statusNAV').addClass("active");
    $('#problems').hide();
    $('#main').show().html(HTML.loader)
        .load("module/contestNew/status.jsp?user="+_user+"&pid="+_pid+"&result="+_result+"&lang="+_lang+"&cid="+cid+"&page="+_page,function(){
        });
}
function loadRank(user){
    $('#NAV').find('li').removeClass("active");
    $('#rankNAV').addClass("active");
    $('#problems').hide();
    $('#main').show().html(HTML.loader).load("module/contestNew/Rank.jsp?cid="+cid,function(){
       if(user){
           $row=$('table tbody tr');
           $row.each(function(){
               if($(this).find('td:eq(1)').text()==user){
                   //$(this).css('border','2px solid #000088');
                   //$(this).css('outline-style','dashed');
                   //$(this).css('outline-width','2px');
                   //$(this).css('outline-color','#ffff00');
                   //$(this).css({filter:'glow(color=#0000ff,strength=30)'});
                   $(this).addClass('shine_red');
               }
           });
       }
    });
}
function loadRating() {
    $('#main').show().html(HTML.loader).load("module/contestNew/Rating.jsp?cid=" + cid);
    $('#problems').hide();
    $('#NAV').find('li').removeClass("active");
    $('#ratingNAV').addClass("active");
}
function sendsubmit(){
    $.post("sendToContest.action",{cid:$('#sendcid').val(),text:$('#sendtext').val()});
    return false;
}
function sendMessageForm(){
    return formToHTML({
        col:[2,10],
       // action:"sendToContest.action",
        method:"post",
        id:"send-form",
        onSubmit:"return sendsubmit();",
        list:[
            {
                type:"hidden",
                name:"cid",
                id:'sendcid',
                value:contestInfo.cid
            },{
                label:"发送消息",
                type:"textarea",
                name:"text",
                id:"sendtext",
                rows:15
            },{
                type:"submit",
                label:"发送"
            }
        ]
    });
}
function loadAdmin(){
    //功能：管理员发送消息
    //     显示当前在线人数
    $main=$('#main');
    $main.show().html(HTML.loader);
    $.getJSON("getOnlineUserWithContest.action?cid="+cid,function(data){
        var s="<h4>在线列表：</h4>";
        for(var i=0;i<data.length;i++){
            s+=data[i]+"<br>";
        }
        $main.html("").append(HTML.row(HTML.col(2,0,"",s)+HTML.col(10,0,"",sendMessageForm())));
    });
    $('#problems').hide();
    $('#NAV').find('li').removeClass("active");
    $('#adminNAV').addClass("active");
}
function ceinfo(rid){
    $('#info').html("").append(
    '<div class="modal fade" id="ceinfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">'+
        '<div class="modal-dialog" role="document">'+
            '<div class="modal-content">'+
                '<div class="modal-header">'+
                    '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
                        '<span aria-hidden="true">&times;</span>' +
                    '</button>'+
                    '<h4 class="modal-title" id="myModalLabel">CE Info</h4>'+
                '</div>'+
                '<div class="modal-body">'+
                'Loading...'+
                '</div>'+
                '<div class="modal-footer">'+
                    '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'+
                '</div>'+
            '</div>'+
        '</div>'+
    '</div>'
    ).find('.modal-body').load("module/contestNew/ceinfo.jsp?rid="+rid);
    $('#ceinfo').modal();
}
function viewcode(rid){
    $('#info').html("").append(
        '<div class="modal fade" id="viewcode" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">'+
        '<div class="modal-dialog" role="document">'+
        '<div class="modal-content">'+
        '<div class="modal-header">'+
        '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
        '<span aria-hidden="true">&times;</span>' +
        '</button>'+
        '<h4 class="modal-title" id="myModalLabel">View Code</h4>'+
        '</div>'+
        '<div class="modal-body">'+
        'Loading...'+
        '</div>'+
        '<div class="modal-footer">'+
        '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'+
        '</div>'+
        '</div>'+
        '</div>'+
        '</div>'
    ).find('.modal-body').load("module/contestNew/viewcode.jsp?rid="+rid,function(){
            //$('#info').append('<link href="js/prism/css.css" rel="stylesheet" />')
            //          .append('<script src="js/prism/js.js"></script>');
            _PRISM.highlightAll();
        });
    $('#viewcode').modal();
}
function doSubmit(){
    var pid=$('#pidinput').val();
    var lang=$('#laninput').val();
    var code=$('#codeinput').val();
    if(code.length<=50) alert('代码长度应该不小于50');
    else if(code.length>=65535) alert('代码长度不能超过65535');
    else{
        $.post("sb.action",{cid:cid,pid:pid,language:lang,code:code});
        $('#submit').modal('hide');
        location.hash='#S';
    }
}
function submit(pid){
    $('#info').html("").append(
        '<div class="modal fade bs-example-modal-lg" id="submit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">'+
        '<div class="modal-dialog  modal-lg" role="document">'+
        '<div class="modal-content">'+
        '<div class="modal-header">'+
        '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
        '<span aria-hidden="true">&times;</span>' +
        '</button>'+
        '<h4 class="modal-title" id="myModalLabel">Submit</h4>'+
        '</div>'+
        '<div class="modal-body">'+
        'Loading...'+
        '</div>'+
        //'<div class="modal-footer">'+
        ////'<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>'+
        ////'<button type="button" class="btn btn-primary" onclick="doSubmit();">Submit</button>'+
        //'</div>'+
        '</div>'+
        '</div>'+
        '</div>'
    ).find('.modal-body').load("module/contestNew/submitForm.jsp?pid="+pid+"&cid="+cid);
    $('#submit').modal();
}
function loadDiscussList(page){
    $('#main').show().html(HTML.loader).load("module/contestNew/discuss.jsp?cid=" + cid + "&page=" + page);
    $('#problems').hide();
    $('#NAV').find('li').removeClass("active");
    $('#discussNAV').addClass("active");
}
function loadDiscuss(id) {
    var page = 0;
    for(var i=0;i<id.length;i++){
        if(id.charAt(i)=='_'){
            page = id.substr(i+1,30);
            id = id.substr(0,i);
        }
    }
    $('#main').show().html(HTML.loader).load("module/contestNew/discuss.jsp?cid=" + cid + "&id=" + id + "&page=" + page);
    $('#problems').hide();
    $('#NAV').find('li').removeClass("active");
    $('#discussNAV').addClass("active");
}
function loadCompare(){
    $('#main').show().html(HTML.loader).load("module/contestNew/CodeCompare.jsp?cid=" + cid+"&f=0.7");
    $('#problems').hide();
    $('#NAV').find('li').removeClass("active");
    $('#compareNAV').addClass("active");
}
$(window).on('hashchange', function() {
    href=location.hash;
    go();
});
function notice(text){
    $('#notics').html("<div class='notics-body'><div class='text'></div></div>").show().find(".notics-body")
        .append(HTML.center(HTML.abtn("md","javascript:closenotics()","已阅","btn-primary"))).find('.text').text(text);
}
function closenotics(){
    $('#notics').hide();
}