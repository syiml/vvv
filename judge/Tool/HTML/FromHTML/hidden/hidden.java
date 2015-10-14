package Tool.HTML.FromHTML.hidden;

import Tool.HTML.FromHTML.form;

/**
 * Created by Syiml on 2015/9/28 0028.
 */
public class hidden extends form {
    String name;
    String value;
    public hidden(String name,String value){
        this.name=name;
        this.value=value;
    }
    public String toHTML(){
        return "<input type='hidden' value='"+value+"' name='"+name+"'>";
    }
    public String toHTML(int col1,int col2){
        return toHTML();
    }
    public void setValue(String s){
        value=s;
    }
    public void setDisabled(){

    }
}
