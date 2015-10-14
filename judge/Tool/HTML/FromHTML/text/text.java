package Tool.HTML.FromHTML.text;

import Tool.HTML.FromHTML.form;
import Tool.HTML.HTML;

/**
 * Created by Syiml on 2015/6/10.
 */
public class text extends form {
    String name;
    String label;
    String placeholder;
    String id;
    String allid;
    String value;
    String validate;
    boolean disabled=false;
    boolean pass=false;
    int type;//0,1;
    public text(String name,String label){
        this.name=name;
        this.label=label;
        type=0;
        id=null;
        placeholder=null;
        value=null;
        validate=null;
    }
    public void setPlaceholder(String p){
        placeholder=p;
    }
    public void setId(String p){
        id=p;
    }
    public void setAllid(String p){allid=p;}
    public void setType(int p){
        type=p;
    }
    public void setPass(){
        pass=true;
    }
    public void setValidate(String val){
        validate=val;
    }
    public String toHTML(){
        String s="";
        s+="<div class='form-group'";
        if(allid!=null&&!allid.equals("")){
            s+=" id="+allid;
        }
        if(disabled){
            s+=" style='display:none'";
        }
        s+="><div class='input-group input-group-sm'>";
        if(!label.equals("")){
            s+="<span class='input-group-addon' ";
            //s+=" id='"+id+"'";
            s+=">"+label+"</span>";
        }
        s+="<input type='";
        if(pass)s+="password";
        else s+="text";
        s+="' class='form-control ";
        s+="'";
        s+=" aria-describedby='"+id+"'";
        //if(label.equals("")){
            s+=" id='"+id+"'";
        //}
        s+=" name='"+name+"'";
        if(placeholder!=null){
            s+=" placeholder='"+placeholder+"'";
        }
        if(value!=null){ s+=" value='"+value+"'";}
        s+="></div></div>";
        return s;
    }
    public String toHTML(int col1,int col2){
        if(type!=0) return toHTML();
        String s="";
        s+="<div class='form-group row'";
        if(disabled){
            s+=" style='display:none'";
        }
        if(allid!=null&&!allid.equals("")){
            s+=" id="+allid;
        }
        s+=">";
        if(!label.equals("")){
            s+="<label";
            if(id!=null){ s+=" for='"+id+"'";}
            s+=" class='col-xs-"+col1+" control-label'";
            s+=">"+label+"</label>";
        }
        String ss="";
        //input 标签
        ss+="<input type='";
        if(pass)ss+="password";
        else ss+="text";
        ss+="' class='form-control ";
        ss+="'";
        ss+=" name='"+name+"'";
        if(id!=null) ss+=" id='"+id+"'";
        if(placeholder!=null){
            ss+=" placeholder='"+placeholder+"'";
        }
        if(value!=null){ ss+=" value='"+value+"'";}
        ss+="></div>";
        s+=HTML.col(col2,ss);
        return s;
    }
    public void setValue(String value){
        this.value=value;
    }
    public void setDisabled(){
        disabled=true;
    }
}
