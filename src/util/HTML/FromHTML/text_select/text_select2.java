package util.HTML.FromHTML.text_select;

import util.HTML.FromHTML.form;
import util.HTML.HTML;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QAQ on 2016/5/4 0004.
 */
public class text_select2 extends form {
    List<String> sel;
    String label;
    String name;
    String id=null;

    String value = "";
    boolean disabled=false;

    public text_select2(String name,String label){
        this.name=name;
        this.label=label;
        sel=new ArrayList<String>();
        id = name;
    }
    public text_select2 setId(String id){
        this.id=id;
        return this;
    }
    public text_select2 add(String s){
        sel.add(s);
        return this;
    }
    public String toHTML(){
        return toHTML(2,10);
    }
    public String script(){
        return "<script language='javascript' type='text/javascript'>" +
                "function _TS2_"+id+"_select(s){" +
                    "$('#"+id+"').val(s);" +
                "}" +
                "</script> ";
    }
    public String toHTML(int col1,int col2){
        String s="";
        s+="<div class='form-group row'";
        if(disabled){
            s+=" style='display:none'";
        }
        s+=">";
        if(!label.equals("")){
            s+="<label";
            if(id!=null){ s+=" for='"+id+"'";}
            s+=" class='col-xs-"+col1+" control-label'";
            s+=">"+label+"</label>";
        }
        StringBuilder ss=new StringBuilder("<div class='input-group'>");
        ss.append("<input type='text' id='")
                .append(id).append("' class='form-control' name='")
                .append(name)
                .append("' value='")
                .append(value)
                .append("'>")
                .append("<div class='input-group-btn'>")
                .append("<button type='button' class='btn btn-default dropdown-toggle' ")
                .append("data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>")
                .append("快速选择 <span class='caret'></span></button>");
        ss.append("<ul class='dropdown-menu dropdown-menu-right'>");
        for(String st:sel){
            ss.append("<li><a href='javascript:_TS2_"+id+"_select(\""+st+"\");'>").append(st).append("</a></li>");
        }
        ss.append("</ul></div></div>");
        s+= HTML.col(col2, ss.toString())+script()+"</div>";
        return s;
    }
    public text_select2 setValue(String s){
        value=s;
        return this;
    }
    public void setDisabled(){
        disabled=true;
    }
}
