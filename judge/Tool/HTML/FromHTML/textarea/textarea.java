package Tool.HTML.FromHTML.textarea;

import Tool.HTML.FromHTML.form;
import Tool.HTML.HTML;

/**
 * Created by Syiml on 2015/6/27 0027.
 */
public class textarea extends form {
    private String name;
    private String label;
    private String id;
    private boolean disabled;
    private String value="";
    private int cols=10;
    private int rows=10;
    private String placeholder;

    public void setName(String name) {
        this.name = name;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    public void setCols(int cols) {
        this.cols = cols;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
    public textarea(String name,String label){
        this.name=name;
        this.label=label;
        id="";
        disabled=false;
    }
    public String toHTML(){
        String s="<div class='form-group'>";
        s+="<textarea";
        if(id!=null&&!id.equals("")) s+=" id='" +id+"'";
        if(placeholder!=null) s+=" placeholder='"+placeholder+"'";
        s+=" class='form-control'";
        s+=" cols='"+cols+"'";
        s+=" rows='"+rows+"'";
        s+=" name='" +name+"'";
        s+=">"+value+"</textarea>";
        s+="</div>";
        return s;
    }
    public String toHTML(int col1,int col2){
        String s="<div class='form-group row'>";
        if(col1!=0&&col1!=12)s+="<label class='col-sm-"+col1+" control-label'>"+label+"</label>";
        s+="<div class='col-sm-"+col2+"'>";
        s+="<textarea";
        if(id!=null&&!id.equals("")) s+=" id='" +id+"'";
        if(placeholder!=null) s+=" placeholder='"+placeholder+"'";
        s+=" class='form-control'";
        s+=" cols='"+cols+"'";
        s+=" rows='"+rows+"'";
        s+=" name='" +name+"'";
        s+=">"+value+"</textarea>";
        s+="</div></div>";
        return s;
    }
    public void setValue(String s){
        value=s;
    }
    public void setDisabled(){

    }
}
