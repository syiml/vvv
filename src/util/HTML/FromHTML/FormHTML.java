package util.HTML.FromHTML;

import util.HTML.FromHTML.file.file;
import util.HTML.HTML;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2015/6/9.
 */
public class FormHTML {
    int type;
    int col1,col2;
    String action;
    String method;
    String id;
    String submitText="submit";
    String submitId;
    boolean isForm;//是否是完整的form
    List<form> list;
    boolean isscript=false;
    boolean enctype=false;
    public FormHTML(){
        type=0;
        list = new ArrayList<form>();
        method="post";
        col1=2;
        col2=10;
        isForm=true;
    }

    public void setAction(String s){action=s;}

    public void setEnctype(){enctype=true;}

    public void setScript(String scriptfunction){
        isscript=true;
        action=scriptfunction;
    }

    public void setType(int type){
        this.type=type;
        if(type==1){
            method="get";
        }else{
            method="post";
        }
    }
    public void setId(String id){
        this.id=id;
    }
    public void setCol(int col1,int col2){
        this.col1=col1;
        this.col2=col2;
    }
    public void addForm(form... f){
        for( form ff:f){
            if(ff.getClass().equals(file.class)){
                setEnctype();
            }
        }
        Collections.addAll(list, f);
    }
    public void setSubmitText(String s){submitText=s;}
    public void setSubmitId(String s){submitId=s;}
    public void setPartFrom(){isForm=false;}
    public String toHTML(){
        StringBuilder s=new StringBuilder();
        if(isForm){
            s.append("<form style='margin:0px' class='");
            if(type==1){
                s.append("form-inline");
            }else{
                s.append("form-horizontal");
            }
            s.append("'");
            if(id!=null){
                s.append(" id='").append(id).append("'");
            }
            if(enctype) s.append(" enctype=\"multipart/form-data\" ");
            if(action!=null){
                s.append(" action='").append(action).append("'");
            }
            s.append(" method='").append(method).append("'>");
        }
        for (form aList : list) {
            if (type == 0) {
                s.append(aList.toHTML(col1, col2));
            } else {
                s.append(aList.toHTML());
            }
        }
        if(isForm) {
            if (type == 1)
                if(isscript){
                    s .append( HTML.abtn( "sm",action, submitText, ""));
                }else{
                    String submitid=(submitId==null?"":"id='"+submitId+"'");
                    s.append("<button ").append(submitid).append(" type='submit' class='btn btn-default btn-sm'>").append(submitText).append("</button>");
                }
            else {
                if(isscript){
                    s .append( HTML.abtn("md", action, submitText,  ""));
                }else{
                    String submitid=(submitId==null?"":"id='"+submitId+"'");
                    s .append( HTML.div("form-group", HTML.col(col2, col1, "<input "+submitid+" class=\"submit btn btn-default\" type=\"submit\" value=\""+submitText+"\">")));
                }
            }
            s .append( "</form>");
        }
        return s.toString();
    }
    public void delForm(int i){
        list.remove(i);
    }
    public void setMethod(String method){this.method=method;}
}
