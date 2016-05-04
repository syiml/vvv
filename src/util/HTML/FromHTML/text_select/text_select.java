package util.HTML.FromHTML.text_select;

import util.HTML.FromHTML.form;
import util.HTML.HTML;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/9/25 0025.
 */
public class text_select extends form {
    List<String> sel;
    String label;
    String name;
    String id=null;

    String value1;
    String value2;
    boolean disabled=false;

    public text_select(String name,String label){
        this.name=name;
        this.label=label;
        sel=new ArrayList<String>();
        sel.add("手动输入");
    }
    public void setId(String id){
        this.id=id;
    }
    public void add(String s){
        sel.add(s);
    }
    public String toHTML(){
        return toHTML(2,10);
    }
    public String toHTML2(int col1,int col2){
        String s="";
        s+="<div class='form-group'";
        if(!value1.equals("手动输入")){
            s+=" style='display:none'";
        }
        if(id!=null&&!id.equals("")){
            s+=" id="+id+"_text";
        }
        s+=">";
        if(!label.equals("")){
            s+="<label";
            if(id!=null){ s+=" for='"+id+"_text"+"'";}
            s+=" class='col-sm-"+col1+" control-label'";
            s+=">"+label+"</label>";
        }
        String ss="";
        //input 标签
        ss+="<input type='";
        ss+="text";
        ss+="' class='form-control ";
        ss+="'";
        ss+=" name='"+name+"_text'";
        if(value2!=null){ ss+=" value='"+value2+"'";}
        ss+="placeholder='手动输入值'";
        ss+="></div>";
        s+=HTML.col(col2,ss);
        return s;
    }
    public String script(){
        return "<script language=\"javascript\" type=\"text/javascript\"> \n" +
                "$(function(){ \n" +
                "$('#"+id+"').change(function(){ \n" +
                "var v=($(this).children('option:selected').val()); \n" +
                "if(v=='手动输入'){$('#"+id+"_text').slideDown().find('input').val('');}else{$('#"+id+"_text').slideUp().find('input').val(v);}"+
                "}) \n" +
                "}) \n" +
                "</script> ";
    }
    public String toHTML(int col1,int col2){
        String s="";
        s+="<div class='form-group'";
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
        String ss="";
        ss+="<select class='form-control'";
        if(id!=null) ss+=" id='"+id+"'";
        if(name!=null) ss+=" name='"+name+"'";
        ss+=">";
        for(int i=0;i<sel.size();i++){
            ss+="<option value='"+sel.get(i)+"'";
            if(id!=null)ss+=" id='"+id+"'";
            if(value1.equals(sel.get(i)+"")) ss+=" selected='selected'";
            ss+=">"+sel.get(i)+"</option>";
        }
        ss+="</select></div>";
        s+= HTML.col(col2, ss)+toHTML2(col1,col2)+script();
        return s;
    }
    public text_select setValue(String s){
        for(String ss:sel){
            if(ss.equals(s)){
                value1=s;
                value2=s;
                return this;
            }
        }
        value1="手动输入";
        value2=s;
        return this;
    }
    public void setDisabled(){
        disabled=true;
    }
}
