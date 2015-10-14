/**
 * Created by Syiml on 2015/7/11 0011.
 */
var HTML={
    texts:function (s,color) {
        return "<font color='"+color+"'>"+s+"</font>";
    },
    textb:function(s,color){
        return "<b style='color:"+color+"'>"+s+"</b>";
    },
    panel:function(title,ss,footer,arge,css){
        //面板
        var s="";
        s+="<div class='panel panel-";
        if(arge==null){
            s+="default";
        }else{
            s+=arge;
        }
        s+="' "+css;
        s+=">";
        if(title!=null){
            s+="<div class='panel-heading'>"+title+"</div>";
        }
        s+="<div class='panel-body'>";
        s+=ss;
        s+="</div>";
        if(footer!=null){
            s+="<div class='panel-footer'>"+footer+"</div>";
        }
        s+="</div>";
        return s;
    },
    panelnobody:function(Title,ss,cl,css){
        var s="";
        s+="<div class='panel panel-"+cl+" "+css+"'>";
        s+="<div class='panel-heading'>"+Title+"</div>";
        s+=ss;
        s+="</div>";
        return s;
    },
    code:function(code,replace,lang){
        var language="";
        if(lang==2) language="java";
        else if(lang==1||lang==0) language="cpp";
        if(replace){
            code=code.replaceAll("&","&amp;");
            code=code.replaceAll("\"","&quot;");
            code=code.replaceAll("<","&lt;");
            code=code.replaceAll(">","&gt;");
        }
        return "<pre style='padding:0px;border-style:none;background-color:transparent'>"+
            "<code class='language-"+language+"'>"+code+"</code>"+
            "</pre>";
    },
    a:function(href,s){
        return "<a href="+href+">"+s+"</a>";
    },
    anew:function(href,s){
        return "<a href="+href+" target=\"view_window\">"+s+"</a>";
    },
    floatRight:function(s){
        return "<front style='float: right'>"+s+"</front>";
    },
    floatLeft:function(s){
        return "<front style='float: left'>"+s+"</front>";
    },
    span:function(cl,s){
        return "<span class='label label-"+cl+"'>"+s+"</span>";
    },
    glyphicon:function(name){
        return "<span class='glyphicon glyphicon-"+name+"'></span>";
    },
    abtn:function(size,href,s,arge){
        var ret="<a role='button' class='btn";
        if(arge.contains("btn-primary")){
            ret+=" btn-primary";
        }else{
            ret+=" btn-default";
        }
        ret+=" btn-"+size+"'";
        if(href!=null){
            ret+=" href='"+href+"'";
        }
        if(arge.contains("disabled")){
            ret+=" disabled='disabled'";
        }
        if(arge.contains("id=")){
            ret+=arge.substring(arge.indexOf("id="));
        }
        ret+=">"+s+"</a>";
        return ret;
    },
    btngroup:function(s){
        return "<div class='btn-group' role='group'>"+s+"</div>";
    },
    pre:function(s){
        return "<pre style='padding:0px;border-style:none;background-color:transparent'>"+s+"</pre>";
    },
    center:function(s){return "<div style='text-align:center'>"+s+"</div>";},
    div:function(cl,css,s){
        return "<div class='"+cl+"' " +css+
            " >"+s+"</div>";
    },
    row:function(s){
        return "<div class='row'>"+s+"</div>";
    },
    col:function(width,offset,otherclass,s){
        return "<div class='col-xs-"+width+" col-xs-offset-"+offset+" "+otherclass+"'>"+s+"</div>";
    },
    time:function(time){
        var d=new Date(time);
        return d.Format("yyyy-MM-dd hh:mm:ss");
    },
    HTMLtoString:function(s){
        s=s.replaceAll("&","&amp;");
        s=s.replaceAll("<","&lt;");
        s=s.replaceAll(">","&gt;");
        //s=s.replaceAll(" ","&nbsp;");
        return s;
    },
    modal:function(s){
        //s={id:"id",title:"title",body:"body",btnlabel:"ok",btncls:"link btn-xs"}
        if(s.btncls==null) s.btncls="default";
        return (s.btnlabel!=""?("<button type='button' class='btn btn-"+ s.btncls+"' data-toggle='modal' data-target='#"+ s.id+"'>" +
            s.btnlabel +
            "</button>"):"") +
            "<div class='modal fade' id='"+ s.id+"' tabindex='-1' role='dialog' aria-labelledby='myModalLabel'>" +
            //"<form action='"+action+"' method='post'>"+
            "  <div class='modal-dialog' role='document'>" +
            "    <div class='modal-content'>" +
            "      <div class='modal-header'>" +
            "        <button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>" +
            "        <h4 class='modal-title' id='myModalLabel'>"+ s.title+"</h4>" +
            "      </div>" +
            "      <div class='modal-body'>" +
                     s.body+
            "      </div>" +

            (s.foot==false?"":"      <div class='modal-footer'>") +
            (s.foot==false?"":"        <button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>") +
            //"        <button type='submit' class='btn btn-primary' >Submit</button>" +
            "      </div>" +
            "    </div>" +
            "  </div>" +
            //"</form>"+
            "</div>";
    },
    loader:"<div class='loader'><div class='loader-inner ball-spin-fade-loader'>" +
    "<div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div></div>",
    loader_h:function(height){
        return "<div class='loader' style='"+height+"'><div class='loader-inner ball-spin-fade-loader'>" +
            "<div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div></div>"
    }
};
/**
 * Created by Syiml on 2015/7/11 0011.
 */
var TableHTML=function(s){
    var ss="<table class='"+ s.class+"'>";
    ss+="<thead>";
    for(var i=0;i< s.head.length;i++){
        ss+="<th>"+ s.head[i]+"</th>";
    }
    ss+="</thead>";
    ss+="<tbody>";
    for(var i=0;i< s.body.length;i++){
        ss+="<tr>";
        for(var j=0;j< s.body[i].length;j++){
            ss+="<td>";
            ss+= s.body[i][j];
            ss+="</td>";
        }
        //alert(s.body[i].length);
        ss+="</tr>"
    }
    ss+="</tbody>";
    ss+="</table>";
    return ss;
};
//样例
//var sample={
//    class:"table",
//    head:["#","message","time"],
//    body:[
//        ["1","消息1","时间1"],
//        ["2","消息2","时间2"]
//    ]
//};
function formToHTML(json){
    var ret="<form class='form-horizontal' ";
    if(json.action) ret+="action='"+json.action+"' ";
    if(json.onSubmit) ret+="onsubmit='"+json.onSubmit+"' ";
    if(!json.method) json.method="post";
    ret+="method='"+json.method+"'";
    ret+=">";
    if(!json.col) json.col=[2,10];
    for(var i=0;i<json.list.length;i++){
        ret+=_formToHTML(json.list[i],json.col);
    }
    ret+="</form>";
    return ret;
}
function _formToHTML(d,col){
    var ret="<div class='form-group row'>";
    if(d.type=="text"||d.type=="password"){
        if(d.label) ret+="<label for='"+d.id+"' class='control-label col-xs-"+col[0]+"'>"+d.label+"</label>";
        if(d.label) ret+="<div class='col-xs-"+col[1]+"'>";
        ret+="<input ";
        if(d.type=="text") ret+="type='text' ";
        if(d.type=="password") ret+="type='password' ";
        ret+="name='"+d.name+"' class='form-control' ";
        if(d.value) ret+="value='"+ d.value +"' " ;
        if(d.id) ret+="id='"+d.id+"' ";
        if(d.placeholder) ret+="placeholder='"+d.placeholder+"' ";
        ret+="'>";
        if(d.label) ret+="</div>";
    }else if(d.type=="submit"){
        if(!d.class) d.class="btn btn-primary";
        ret+="<div class='col-xs-"+col[1]+" col-xs-offset-"+col[0]+"'>";
        ret+="<button class='"+d.class+"' type='submit' id='loginsubmit'>"+d.label+"</button>";
        ret+="</div>";
    }else if(d.type=="hidden"){
        return "<input type='hidden' name='"+ d.name+"' value='"+ d.value+"'>";
    }
    ret+="</div>";
    return ret;
}
/*{
    col:[2,8],
    action:"xx.action",
    method:"post",
    onSubmit:"",
    list:[
        {
            type:"text",
            name:"",
            id:"",
            label:"用户名",
            value:"123",
            placeholder:"提示"
        },{
            type:"password",
            name:"",
            id:"",
            label:"密码",
            value:"123",
            placeholder:"提示"
        },{
            type:"select",
            name:"sex",
            id:"id",
            option:[["0","男"],["1","女"]],
            value:"男"
        },{
            type:"textarea",
            name:"note",
            value:""
        },{
            type:"checkbox",
            name:"istop",
            value:""
        },{
            type:"submit",
            label:"确定"
        },{
            type:"hidden",
            name:"id",
            value:"123"
        }
    ]
}*/