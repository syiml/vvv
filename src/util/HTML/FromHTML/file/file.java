package util.HTML.FromHTML.file;

import util.HTML.FromHTML.form;
import util.HTML.HTML;

/**
 * Created by Syiml on 2015/9/29 0029.
 */
public class file extends form {
    String name;
    String label;
    String id;
    String accept;
    boolean disabled=false;
    public file(String name,String label){
        this.name=name;
        this.label=label;
    }
    public void setId(String id){
        this.id=id;
    }
    public void setAccept(String accept){
        this.accept=accept;
    }
    public String toHTML(){
        return toHTML(2,10);
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
            s+=" class='col-sm-"+col1+" control-label'";
            s+=">"+label+"</label>";
        }
        String ss="";
        //input 标签
        ss+="<input type='";
         ss+="file";
        ss+="' class='form-control ";
        ss+="'";
        if(accept!=null&&!accept.equals("")){
            ss+=" accept='"+accept+"' ";
        }
        ss+=" name='"+name+"'";
        if(id!=null) ss+=" id='"+id+"'";
        ss+="></div>";
        s+= HTML.col(col2, ss);
        return s;
    }
    public void setValue(String s){

    }
    public void setDisabled(){
        disabled=true;
    }
}
