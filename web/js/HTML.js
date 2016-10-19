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
    $panel:function($div,title,ss,footer,arge,css){
        return $div.append(HTML.panel(title,ss,footer,arge,css));
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
        if(arge&&arge.indexOf("btn-primary")!=-1){
            ret+=" btn-primary";
        }else{
            ret+=" btn-default";
        }
        ret+=" btn-"+size+"'";
        if(href!=null){
            ret+=" href='"+href+"'";
        }
        if(arge&&arge.indexOf("disabled")!=-1){
            ret+=" disabled='disabled'";
        }
        if(arge&&arge.indexOf("id=")!=-1){
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
    progress:function(s){
        //{active,jd}
        return "<div class='progress' style='margin-bottom: 0;'>" +
            " <div id='contest_pro' class='progress-bar progress-bar-striped " +
            (s.active?"active":"") +
            "' role='progressbar' style='width: "+(s.jd*100)+"%;'></div>" +
            "</div>"
    },
    HTMLtoString:function(s){
        s=s.replace(new RegExp(/(&)/g),'&amp;');
        s=s.replace(new RegExp(/(<)/g),'&lt;');
        s=s.replace(new RegExp(/(>)/g),'&gt;');
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
    if(json.enctype) ret += "enctype='multipart/form-data' ";
    if(json.id) ret+=" id='"+json.id+"' ";
    ret+="method='"+json.method+"'";
    ret+=">";
    if(!json.col) json.col=[2,10];
    for(var i=0;i<json.list.length;i++){
        ret+=_formToHTML(json.list[i],json.col);
    }
    ret+="</form>";
    return ret;
}
function $formToHTML($div,json){
    $div.append(formToHTML(json));
    $div.find(".text_select").each(function (i,$everyDiv) {
        $($everyDiv).change(function(){
            var v=($(this).children('option:selected').val());
            var id = $($everyDiv).attr("id");
            id = id.substr(0,id.length - 7);
            if(v=='手动输入'){
                $("#"+id).attr("value","");
                $("#"+id+"_left_select").attr("class","col-xs-"+parseInt(json.col[1]/2));
                $("#"+id+"_right_text").show();
            }else{
                $("#"+id).attr("value",v);
                $("#"+id+"_left_select").attr("class","col-xs-"+json.col[1]);
                $("#"+id+"_right_text").hide();
            }
        })
    });
    return $div;
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
        ret+=">";
        if(d.label) ret+="</div>";
    }else if(d.type=="submit"){
        if(!d.class) d.class="btn btn-primary";
        ret+="<div class='col-xs-"+col[1]+" col-xs-offset-"+col[0]+"'>";
        ret+="<button class='"+d.class+"' type='submit' ";
        if(d.id) ret+="id='"+d.id+"";
        ret+="'>"+d.label+"</button>";
        ret+="</div>";
    }else if(d.type=="hidden"){
        return "<input type='hidden' name='"+ d.name+"' value='"+ d.value+"' id='"+ d.id+"'>";
    }else if(d.type=="textarea"){
        if(d.label) ret+="<label for='"+d.id+"' class='control-label col-xs-"+col[0]+"'>"+d.label+"</label>";
        ret+="<div class='col-xs-"+col[1]+"'>";
        ret+="<textarea ";
        if(d.type=="password") ret+="type='password' ";
        ret+="name='"+d.name+"' class='form-control' ";
        if(d.rows) ret+=" rows="+ d.rows+" ";
        if(d.value) ret+=" value='"+ d.value +"' " ;
        if(d.id) ret+=" id='"+d.id+"' ";
        if(d.placeholder) ret+=" placeholder='"+d.placeholder+"' ";
        ret+=" ></textarea>";
        ret+="</div>";
    }else if(d.type=="button"){
        if(!d.class) d.class="btn btn-primary";
        ret+="<div class='col-xs-"+col[1]+" col-xs-offset-"+col[0]+"'>";
        ret+="<button class='"+d.class+"' type='button' ";
        if(d.onclick) ret+=" onclick='"+ d.onclick+"' ";
        ret+=" >"+d.label+"</button>";
        ret+="</div>";
    }else if(d.type=="select"){
        if(d.label) ret+="<label for='"+d.id+"' class='control-label col-xs-"+col[0]+"'>"+d.label+"</label>";
        ret+="<div class='col-xs-"+col[1]+"'>";
        ret+="<select type='select' ";
        ret+="name='"+d.name+"' class='form-control' ";
        ret+=">";
        for(var i=0;i<d.option.length;i++){
            ret+="<option value='"+d.option[i][0]+"' "+(d.value==d.option[i][0]?"selected=selected":"")+">"+d.option[i][1]+"</option>"
        }
        ret+="</select></div>";
    }else if(d.type == "text_select"){ //可选择的text
        if(d.label) ret+="<label class='control-label col-xs-"+col[0]+"'>"+d.label+"</label>";

        ret+="<div class='col-xs-"+col[1]+"' id='"+d.id+"_left_select'>";
        ret+="<select type='select' class='form-control text_select' id='"+d.id+"_select'";
        ret+=">";
        ret+="<option value='手动输入'>手动输入</option>";
        var flag = false;
        for(var i=0;i<d.option.length;i++){
            if(d.value==d.option[i][0]) flag = true;
            ret+="<option value='"+d.option[i][0]+"' "+(d.value==d.option[i][0]?"selected=selected":"")+">"+d.option[i][1]+"</option>"
        }
        ret+="</select>";
        ret+="</div>";
        ret+="<div class='col-xs-"+parseInt((col[1]+1)/2)+"' id='"+d.id+"_right_text' style='display: none'>";
        ret+="<input type='text' ";
        ret+="name='"+d.name+"' class='form-control' ";
        if(d.value) ret+="value='"+ d.value +"' " ;
        if(d.id) ret+="id='"+d.id+"' ";
        if(d.placeholder) ret+="placeholder='"+d.placeholder+"' ";
        ret+=">";
        ret+="</div>";
    }else if(d.type == "file"){
        if(d.label) ret+="<label for='"+d.id+"' class='control-label col-xs-"+col[0]+"'>"+d.label+"</label>";
        if(d.label) ret+="<div class='col-xs-"+col[1]+"'>";
        ret+="<input ";
        ret+="type='file' ";
        ret+="name='"+d.name+"' class='form-control' ";
        if(d.id) ret+="id='"+d.id+"' ";
        if(d.placeholder) ret+="placeholder='"+d.placeholder+"' ";
        if(d.accept) ret+= "accept='"+d.accept+"' ";
        if(d.title) ret+="title='"+d.title+"' ";
        ret+=">";
        ret+="</div>";
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