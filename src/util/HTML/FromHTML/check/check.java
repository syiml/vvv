package util.HTML.FromHTML.check;

import util.HTML.FromHTML.form;
import util.HTML.HTML;

/**
 * Created by Syiml on 2015/7/4 0004.
 */
public class check extends form {
    String label;
    boolean checked;
    String name;
    public check(String name,String label){
        this.name=name;
        this.label=label;
        checked=false;
    }
    public String toHTML(){
        String s="<div class='checkbox'><label><input type='checkbox'";
        if(checked)s+=" checked='checked'";
        s+=" id="+name;
        s+=" name='"+name+"'>"+label+"</label></div>";
        return  HTML.div("form-group",s);
    }
    public String toHTML(int col1,int col2){
        return HTML.div("form-group row",HTML.col(col2,col1,toHTML()));
    }
    public check setValue(String s){
        checked = s.equals("true");
        return this;
    }
    public void setDisabled(){

    }
    public void setChecked(){
        checked = true;
    }
}
