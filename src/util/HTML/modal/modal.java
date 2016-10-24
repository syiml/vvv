package util.HTML.modal;

import util.HTML.HTML;

/**
 * Created by Syiml on 2015/7/5 0005.
 * 这个类是回复用的类，带弹框的小回复
 */
public class modal {
    String title;
    String body;
    String id;
    String btncls="default";//bootstrap提供的样式
    String btnlabel;//初始字。。。
    String action="";//提交地址
    String formId=null;
    boolean havesubmit=true;//提交按钮
    boolean enctype=false;//有无文件提交
    boolean islage=false;//是否为大回复框
//    String btnsize="xs";
    public modal(String id,String title,String body,String btnlabel){
        this.id=id;//回复的编号
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
    //纯文本返回
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
                (havesubmit?"        <button type=\"submit\" class=\"btn btn-primary\" >确定</button>\n":"") +
                "      </div>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</form>"+
                "</div>";
    }
    //带链接的返回
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
            (havesubmit?"        <button type=\"submit\" class=\"btn btn-primary\" >确定</button>\n":"") +
            "      </div>\n" +
            "    </div>\n" +
            "  </form>\n"+
            "</div>\n" +
            "</div>";
    }
}
