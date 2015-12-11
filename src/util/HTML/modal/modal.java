package util.HTML.modal;

import util.HTML.HTML;

/**
 * Created by Syiml on 2015/7/5 0005.
 */
public class modal {
    String title;
    String body;
    String id;
    String btncls="default";
    String btnlabel;
    String action="";
    String formId=null;
    boolean havesubmit=true;
    boolean enctype=false;
    boolean islage=false;
//    String btnsize="xs";
    public modal(String id,String title,String body,String btnlabel){
        this.id=id;
        this.title=title;
        this.body=body;
        this.btnlabel=btnlabel;
    }
    public void setBtnCls(String cls){
        btncls=cls;
    }
    public void setHavesubmit(boolean b){
        havesubmit=b;
    }
    public void setAction(String a){
        action=a;
    }
    public void setEnctype(){enctype=true;}
    public void setLage(){
        islage=true;
    }
    public void setFormId(String id){
        formId=id;
    }
    public String toHTML(){
        return "<button type=\"button\" class=\"btn btn-"+btncls+"\" data-toggle=\"modal\" data-target=\"#"+id+"\">\n" +
                btnlabel +
                "</button>" +
                "<div class=\"modal fade "+(islage?"bs-example-modal-lg":"")+"\" id=\""+id+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\">\n" +
                "<form " +
                (formId==null?"":"id='"+formId+"' ")+
                "action='"+action+"' method='post'>"+
                "  <div class=\"modal-dialog "+(islage?"modal-lg":"")+"\" role=\"document\">\n" +
                "    <div class=\"modal-content\">\n" +
                "      <div class=\"modal-header\">\n" +
                "        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>\n" +
                "        <h4 class=\"modal-title\" id=\"myModalLabel\">"+title+"</h4>\n" +
                "      </div>\n" +
                "      <div class=\"modal-body row\">\n" +
                         HTML.col(12, body) +
                "      </div>\n" +
                "      <div class=\"modal-footer\">\n" +
                "        <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button>\n" +
                (havesubmit?"        <button type=\"submit\" class=\"btn btn-primary\" >Submit</button>\n":"") +
                "      </div>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</form>"+
                "</div>";
    }
    public String toHTMLA(){
        return "<a href='' data-toggle=\"modal\" data-target=\"#"+id+"\">" +
            btnlabel +
            "</a>" +
            "<div class=\"modal fade\" id=\""+id+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\">\n" +
            "<div class=\"modal-dialog\" role=\"document\">\n" +
            "  <form " +
            (formId==null?"":"id='"+formId+"' ") +
            "action='"+action+"' method='post' "+(enctype?"enctype=\"multipart/form-data\"":"")+">"+
            "    <div class=\"modal-content\">\n" +
            "      <div class=\"modal-header\">\n" +
            "        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>\n" +
            "        <h4 class=\"modal-title\" id=\"myModalLabel\">"+title+"</h4>\n" +
            "      </div>\n" +
            "      <div class=\"modal-body row\">\n" +
            HTML.col(12, body) +
            "      </div>\n" +
            "      <div class=\"modal-footer\">\n" +
            "        <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close</button>\n" +
            (havesubmit?"        <button type=\"submit\" class=\"btn btn-primary\" >Submit</button>\n":"") +
            "      </div>\n" +
            "    </div>\n" +
            "  </form>\n"+
            "</div>\n" +
            "</div>";
    }
}
