<%@ page import="java.sql.Timestamp" %>
<%@ page import="Main.User.*" %>
<%@ page import="Main.Main" %>
<%--
  所需参数：cid
  如果未登录，要登录

  如果当前cid不是public要验证
--%>
<ul class="nav nav-tabs" id="contestNAV">
  <li role="presentation" id="homeNAV"><a href="#Home">Home</a></li>
  <%
    Timestamp now=new Timestamp(System.currentTimeMillis());
      boolean start=Main.contests.getContest(Integer.parseInt(cid)).getBeginDate().before(now);
      if(start){
  %>
  <li role="presentation" id="problemNAV"><a href="#Problem">Problem</a></li>
  <li role="presentation" id="statusNAV"><a href="#Status">Status</a></li>
  <li role="presentation" id="rankNAV"><a href="#Rank">Rank</a></li>
  <%
      if(p.getComputrating()){
        %><li role="presentation" id="ratingNAV"><a href="#Rating">Rating</a></li><%
      }
  }%>
</ul>
<div class="row">
  <div class="col-sm-12">
    <div class="row">
      <div class="col-sm-12" id="_problemMain">
      </div>
    </div>
    <div class="row">
      <div class="col-sm-12" id="problemMain">
        <div class="row">
          <div class="col-sm-12" id="problemsNav"></div>
        </div>
        <div class="row">
          <div class="col-sm-12" id="problemView"></div>
        </div>
      </div>
    </div>
  </div>
</div>
<%if(start){%>
<script type="text/javascript">
  var c=function(responseTxt,statusTxt,xhr){//load problem失败提示
    if(statusTxt!="success")
      $('#problemView').html(statusTxt);
  };
  var load=function()
  {
    var s=location.hash;
    $('#problemNAV').removeClass('active');
    $('#statusNAV').removeClass('active');
    $('#rankNAV').removeClass('active');
    $('#homeNAV').removeClass('active');
    $('#ratingNAV').removeClass('active');
    if(s.substr(0,8)=="#Problem"){
      $('#problemNAV').addClass('active');
      $('#_problemMain').hide();
      $('#problemMain').show();
      var pid;
      if(s.length>=10){
        pid=s.substr(9);
        $('#problemView').html("<img src='pic/loading.jpg'>");
        $('#problemsNav').load("module/contestModule/Problems.jsp?cid=<%=cid%>&pid="+pid);
        $('#problemView').load("module/contestModule/ProblemViewer.jsp?cid=<%=cid%>&pid="+pid,c);
      }
    }else{
      $('#problemMain').hide();
      $('#_problemMain').show();
      $('#_problemMain').html("<img src='pic/loading.jpg'>");
      if(s=="#Status"){
        $('#statusNAV').addClass('active');
        $('#_problemMain').load("module/contestModule/Status.jsp" +
            "?cid=<%=cid%>"+
            "&page=<%=pa%>"+
            "&result=<%=result%>"+
            "&lang=<%=lang%>"+
            "&pid=<%=pid%>"+
            "&user=<%=ssuser%>");
      }else if(s=="#Rank") {
        $('#rankNAV').addClass('active');
        $('#_problemMain').load("module/contestModule/Rank.jsp?cid=<%=cid%>");
      }else if(s=="#Rating"){
        $('#ratingNAV').addClass('active');
        $('#_problemMain').load("module/contestModule/Rating.jsp?cid=<%=cid%>");
      }else if(s.substr(0,7)=="#Submit") {
        $('#_problemMain').load("module/contestModule/Submit.jsp?cid=<%=cid%>&pid=" + s.substr(8));
      }else{
        $('#homeNAV').addClass('active');
        $('#_problemMain').load("module/contestModule/Home.jsp?cid=<%=cid%>");
      }
    }
  };
  $('#problemsNav').load("module/contestModule/Problems.jsp?cid=<%=cid%>&pid=<%=pid%>");
  $('#problemView').load("module/contestModule/ProblemViewer.jsp?cid=<%=cid%>&pid=<%=pid%>",c);
  load();
  $(window).on('hashchange', function() {
    load();
  });
<%if(main.equals("Status")){%>
  location.hash="#Status";
<%}%></script><%
}else{%>
<script>
  $('#homeNAV').addClass('active');
  var data;
  $('#_problemMain').load("module/contestModule/Home.jsp?cid=<%=cid%>");
</script>
<%}%>