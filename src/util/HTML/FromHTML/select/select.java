package util.HTML.FromHTML.select;

import util.HTML.FromHTML.form;
import util.HTML.HTML;

import java.util.*;

/**
 * Created by Syiml on 2015/6/11 0011.
 */
public class select extends form {
    String name;
    String label;
    String id;
    String value;
    int type;//0,1,2;
    boolean disabled=false;
    //Map<Integer,String> ops;
    List<Integer> ops1;
    List<String> ops2;
    List<String> style;
    public select(String name,String label){
        this.name=name;
        this.label=label;
        type=0;
        id=null;
        ops1=new ArrayList<Integer>();
        ops2=new ArrayList<String>();
        style=new ArrayList<String>();
        value="";
    }
    public void setId(String p){
        id=p;
    }
    public void setType(int p){
        type=p;
    }
    public select add(int a,String b){
        ops1.add(a);
        ops2.add(b);
        style.add("");
        return this;
    }
    public void add(int a,String b,String style){
        ops1.add(a);
        ops2.add(b);
        this.style.add(style);
    }
    public String toHTML(){
        String s="";
        s+="<div class='form-group'";
        if(disabled){
            s+=" style='display:none'";
        }
        s+=">";
        s+="<div class='input-group input-group-sm'>";
        if(type==1&&!label.equals("")){
            s+="<span class='input-group-addon' ";
            //s+=" id='"+id+"'";
            s+=">"+label+"</span>";
        }
        s+="<select class='form-control'";
        s+=" aria-describedby='"+id+"'";
        if(id!=null) s+=" id='"+id+"'";
        s+=" name='"+name+"'";
        s+=">";
        for(int i=0;i<ops1.size();i++){
            s+="<option value='"+ops1.get(i)+"'";
            if(!style.get(i).equals("")) s+=" "+style.get(i);
            if(value.equals(ops1.get(i)+"")) s+=" selected='selected'";
            s+=">"+ops2.get(i)+"</option>";
        }
//        for(Map.Entry<Integer, String> entry:ops.entrySet()){
//            s+="<option value='"+entry.getKey()+"'";
//            if(value.equals(entry.getKey()+"")) s+=" selected='selected'";
//            s+=">"+entry.getValue()+"</option>";
//        }
        s+="</select>";
        if(type==2&&!label.equals("")){
            s+="<span class='input-group-addon' ";
            //s+=" id='"+id+"'";
            s+=">"+label+"</span>";
        }
        s+="</div></div>";
        return s;
    }
    public String toHTML(int col1,int col2){
        if(type==1) return toHTML();
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
        ss+="<select class='form-control'";
        if(id!=null) ss+=" id='"+id+"'";
        if(name!=null) ss+=" name='"+name+"'";
        ss+=">";
        for(int i=0;i<ops1.size();i++){
            ss+="<option value='"+ops1.get(i)+"'";
            if(id!=null)ss+=" id='"+id+"'";
            if(!style.get(i).equals("")) ss+=" "+style.get(i);
            if(value.equals(ops1.get(i)+"")) ss+=" selected='selected'";
            ss+=">"+ops2.get(i)+"</option>";
            //ss+="<option value='"+ops1.get(i)+"'>"+ops2.get(i)+"</option>";
        }
//        for(Map.Entry<Integer, String> entry:ops.entrySet()){
//            ss+="<option value='"+entry.getKey()+"'>"+entry.getValue()+"</option>";
//        }
        ss+="</select></div>";
        s+=HTML.col(col2,ss);
        return s;
    }
    public select setValue(String value){
        this.value=value;
        return this;
    }
    public void setDisabled(){
        disabled=true;
    }
}
