package Tool.HTML.FromHTML;

import Tool.HTML.HTML;

import java.util.ArrayList;
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
    public void setAction(String s){action=s;}
    public void setEnctype(){enctype=true;}
    public void setScript(String scriptfunction){
        isscript=true;
        action=scriptfunction;
    }
    public FormHTML(){
        type=0;
        list = new ArrayList<form>();
        method="post";
        col1=2;
        col2=10;
        isForm=true;
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
    public void addForm(form f){
        list.add(f);
    }
    public void setSubmitText(String s){submitText=s;}
    public void setSubmitId(String s){submitId=s;}
    public void setPartFrom(){isForm=false;}
    public String toHTML(){
        String s="";
        if(isForm){
            s+="<form style='margin:0px' class='";
            if(type==1){
                s+="form-inline";
            }else{
                s+="form-horizontal";
            }
            s+="'";
            if(id!=null){
                s+=" id='"+id+"'";
            }
            if(enctype) s+=" enctype=\"multipart/form-data\" ";
            if(action!=null){
                s+=" action='"+action+"'";
            }
            s+=" method='"+method+"'>";
        }
        for(int i=0;i<list.size();i++){
            if(type==0){
                s+=list.get(i).toHTML(col1,col2);
            }else{
                s+=list.get(i).toHTML();
            }
        }
        if(isForm) {
            if (type == 1)
                if(isscript){
                    s += HTML.abtn( "sm",action, submitText, "");
                }else{
                    String submitid=(submitId==null?"":"id='"+submitId+"'");
                    s += "<button "+submitid+" type='submit' class='btn btn-default btn-sm'>" + submitText + "</button>";
                }
            else {
                if(isscript){
                    s += HTML.abtn("md", action, submitText,  "");
                }else{
                    String submitid=(submitId==null?"":"id='"+submitId+"'");
                    s += HTML.div("form-group", HTML.col(col2, col1, "<input "+submitid+" class=\"submit btn btn-default\" type=\"submit\" value=\""+submitText+"\">"));
                }
            }
            s += "</form>";
        }
        return s;
    }
}
