package Tool.HTML.FromHTML.date;

import Tool.HTML.FromHTML.form;
import Tool.HTML.FromHTML.select.select;
import Tool.HTML.FromHTML.text.text;
import Tool.HTML.HTML;

import java.sql.Timestamp;

/**
 * Created by Syiml on 2015/6/22 0022.
 */
public class date extends form {
    text t;
    String id;
    String label;
    String hv,mv;
    public date(String id,String label){
        t=new text(id+"_d","日期");
        t.setId(id+"_d");
        this.id=id;
        this.label=label;
        this.hv="12";
        this.mv="0";
    }
    private String s(){
        select s=new select(id+"_s","时");
        for(int i=23;i>=0;i--){
            if(i<10) s.add(i,"0"+i);
            else s.add(i,""+i);
        }
        s.setValue(Integer.parseInt(hv)+"");
        s.setType(2);
        return s.toHTML();
    }
    private String m(){
        select s=new select(id+"_m","分");
        for(int i=0;i<60;i+=5){
            if(i<10) s.add(i,"0"+i);
            else s.add(i,""+i);
        }
        s.setValue(Integer.parseInt(mv)+"");
        s.setType(2);
        return s.toHTML();
    }
    public String toHTML() {
        t = new text(id, label);
        String s = "";
        s += t.toHTML();
        s += "<script>" +
                "$(function() {" +
                "$('#" + id + "').datepicker({" +
                "format: 'yyyy-mm-dd'" +
                "});" +
                "})" +
                "</script>";
        return s;
    }
    public String toHTML(int col1,int col2){
        String s="";
        if(!label.equals("")){
            s+="<label";
            if(id!=null){ s+=" for='"+id+"'";}
            s+=" class='col-sm-"+col1+" control-label'";
            s+=">"+label+"</label>";
        }
        s+=HTML.div("form-inline col-sm-"+col2,"style='pendding:15px' ",t.toHTML()+s()+m())+
                "<script>" +
                "$(function() {" +
                "$('#"+id+"_d').datepicker({" +
                "format: 'yyyy-mm-dd'" +
                "});" +
                "});" +
                "$('.form-inline .form-group').css('margin','0px');" +
                "$('.form-inline .form-group').css('margin-right','5px');" +
                "</script>";
        return HTML.div("form-inline form-group","id="+id,s);
    }
    public void setValue(String s){
        t.setValue(s);
    }
    public void setValue(Timestamp t){
        String s=t.toString();
        this.t.setValue(s.substring(0,10));
        this.hv=s.substring(11, 13);
        this.mv=s.substring(14,16);
//        this.t.setValue();
    }
    public void setDisabled(){
        t.setDisabled();
    }
}
/*
<link href="js/bootstrap-datepicker-master/datepicker.css" rel="stylesheet">
<script src="js/bootstrap-datepicker-master/bootstrap-datepicker.js"></script>
<input style="height: auto" type="text" class="span2" value="2012-02-16" id="dp1" >
        <script>
          $(function() {
            //window.prettyPrint && prettyPrint();
            $('#dp1').datepicker({
              format: 'yyyy-mm-dd'
            });
          })
        </script>
*/