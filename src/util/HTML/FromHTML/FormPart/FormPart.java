package util.HTML.FromHTML.FormPart;

import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.form;
import util.HTML.HTML;

/**
 * Created by Syiml on 2015/6/23 0023.
 */
public class FormPart extends form {
    FormHTML f;
    String id;
    boolean disabled=false;
    public FormPart(FormHTML f){
        this.f=f;
    }
    public void setId(String id){
        this.id=id;
    }
    public String toHTML(){
        String s="";
        if(id!=null&&!id.equals(""))
            s+="id='"+id+"'";
        if(disabled){
            s+="  style='display:none'";
        }
        return HTML.div("",s,f.toHTML());
    }
    public String toHTML(int col1,int col2){
        f.setCol(col1,col2);
        return toHTML();
    }
    public FormPart setValue(String s){
        return this;
    }
    public void setDisabled(){
        disabled=true;
    }
}
