package Main.contest.rank.RankICPC;

import Tool.HTML.FromHTML.form;
import Tool.HTML.FromHTML.select.select;
import Tool.HTML.FromHTML.text.text;
import Tool.HTML.HTML;

/**
 * Created by Syiml on 2015/6/23 0023.
 */
public class Form_text_select_inline extends form {
    text t;
    select s;
    String id;
    String label;
    public Form_text_select_inline(String id,String label){
        this.id=id;
        this.label=label;
        t=new text(id+"_t","");
        t.setType(1);
        t.setValue("0");
        t.setId(id + "_t");
        s=new select(id+"_s","");
        s.add(0,"个");
        s.add(1,"%");
        s.setType(1);
        s.setValue("0");
        s.setId(id+"_s");
    }
    public void setValue(String t,String s){
        this.t.setValue(t);
        this.s.setValue(s);
    }
    public String toHTML() {
        return "";
    }
    public String toHTML(int col1,int col2){
        String s="";
        if(!label.equals("")){
            s+="<label";
            if(id!=null){ s+=" for='"+id+"'";}
            s+=" class='col-sm-"+col1+" control-label'";
            s+=">"+label+"</label>";
        }
        s+= HTML.div("form-inline col-sm-" + col2, "style='pendding:15px' ", t.toHTML() + this.s.toHTML());
        return HTML.div("form-inline form-group","id="+id,s)+
                "<script>" +
                "$('#"+id+" .form-group').css('margin','0px');" +
                "$('#"+id+" .form-group').css('margin-right','5px');" +
                "$('#"+id+" .form-control').css('border-radius','4px');" +
                "</script>";
    }
    public void setValue(String s){
        t.setValue(s);
        this.s.setValue("0");
    }
    public void setDisabled(){
        t.setDisabled();
        s.setDisabled();
    }
}
