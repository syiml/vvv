package ProblemTag;

import Main.Main;
import Main.User.User;
import Tool.HTML.FromHTML.FormHTML;
import Tool.HTML.FromHTML.select.select;
import Tool.HTML.FromHTML.text.text;
import Tool.HTML.HTML;
import Tool.HTML.TableHTML.TableHTML;
import javafx.scene.control.Tab;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * Created by Syiml on 2015/7/24 0024.
 */
public class ProblemTagHTML {
    public static int ProblemTagPageNum=20;
    public static String ProblemTag(int pid){
        List<ProblemTagRecord> list = ProblemTagSQL.getProblemTags(pid);
        if(list.size()==0) return HTML.div("panel-body","暂无标签");
        Map<Integer,Integer> ret = new TreeMap<Integer, Integer>();
        for(ProblemTagRecord ptg:list){
            Integer rating= ret.get(ptg.tagid);
            if(rating==null){
                ret.put(ptg.tagid,ptg.rating-500);
            }else{
                ret.put(ptg.tagid,ptg.rating+rating-500);
            }
        }
        List<ProblemTagRecord> ans=new ArrayList<ProblemTagRecord>();
        for(Integer tagid:ret.keySet()){
            ans.add(new ProblemTagRecord(pid,"",tagid,ret.get(tagid)));
        }
        Collections.sort(ans);
        //排序

        TableHTML table=new TableHTML();
        table.setClass("table nomargin");
        table.addCl(0,-1,"hide");
        table.addColname("name","rating");
        for(ProblemTagRecord ptr:ans){
            table.addRow(ProblemTagSQL.get(ptr.tagid).getName(),ptr.rating+"");
        }
        return table.HTML();
    }
    public static String problemTagJson(int pid,String user){
        //[{tagid,tagname,rating},...]
        List<ProblemTagRecord> list=ProblemTagSQL.getProblemTags(pid,user);
        JSONArray ret=new JSONArray();
        for(ProblemTagRecord ptr: list){
            JSONObject jo=new JSONObject();
            jo.put("tagid",ptr.tagid);
            jo.put("tagname",ProblemTagSQL.get(ptr.tagid).getName());
            jo.put("rating",ptr.rating);
            ret.add(jo);
        }
        return ret.toString();
    }
    public static String problemTagJson(){
        JSONArray ret=new JSONArray();
        for(ProblemTag pt:ProblemTagSQL.problemTag){
            ret.add(pt);
        }
        return ret.toString();
    }
    public static String problemTagJson(int pid){
        List<ProblemTagRecord> list = ProblemTagSQL.getProblemTags(pid);
        if(list.size()==0) return HTML.div("panel-body","暂无标签");
        Map<Integer,Integer> ret = new TreeMap<Integer, Integer>();
        for(ProblemTagRecord ptg:list){
            Integer rating= ret.get(ptg.tagid);
            if(rating==null){
                ret.put(ptg.tagid,ptg.rating-500);
            }else{
                ret.put(ptg.tagid,ptg.rating+rating-500);
            }
        }
        //List<ProblemTagRecord> ans=new ArrayList<ProblemTagRecord>();
        String res="[";
        int k=0;
        for(Integer tagid:ret.keySet()){
            if(k!=0){
                res+=",";
            }else k=1;
            res+="{name:\""+ProblemTagSQL.get(tagid).getName()+"\",value:"+ret.get(tagid)+",itemStyle: createRandomItemStyle()}";
        }
        res+="]";
        return res;
    }
    public static String problemTag(){
        TableHTML table=new TableHTML();
        table.setClass("table table-condensed");
        table.addColname("#", "name", "admin");
        ProblemTagSQL.readTag();
        for(ProblemTag pt:ProblemTagSQL.problemTag){
            FormHTML form=new FormHTML();
            form.setType(1);
            form.setAction("module/problemtag/tagadmin.jsp");
            text f1=new text("name","重命名");
            f1.setId("name_"+pt.getId());
            text f2=new text("tagid","tagid");
            f2.setValue(pt.getId()+"");
            f2.setDisabled();
            text f3=new text("type","type");
            f3.setValue("rename");
            f3.setDisabled();
            form.addForm(f1);
            form.addForm(f2);
            form.addForm(f3);
            table.addRow(pt.getId()+"",pt.getName(),form.toHTML());
        }
        FormHTML form=new FormHTML();
        form.setAction("module/problemtag/tagadmin.jsp");
        form.setType(1);
        text f1=new text("name","新增");
        f1.setId("name_add");
        text f3=new text("type","type");
        f3.setValue("add");
        f3.setDisabled();
        form.addForm(f1);
        form.addForm(f3);
        return table.HTML()+form.toHTML();
    }
    public static String problemTag(int pid,int page){
       TableHTML table=new TableHTML();
       table.setClass("table");
       List<ProblemTagRecord> list=ProblemTagSQL.getProblemTags(pid,page*ProblemTagPageNum,ProblemTagPageNum+1);
       table.addColname("user", "nick", "tag", "#");
       int size=list.size();
       if(size==ProblemTagPageNum+1){
           size=ProblemTagPageNum;
       }
       for(int i=0;i<size;i++){
           List<String> row=new ArrayList<String>();
           User u=Main.users.getUser(list.get(i).username);
           row.add(u.getUsernameHTML());
           row.add(u.getNick());
           row.add(ProblemTagSQL.get(list.get(i).tagid).getName());
           row.add(HTML.a("Status.jsp?all=1&user="+list.get(i).username+"&pid="+pid,list.get(i).rating-500+""));
           table.addRow(row);
       }
       return HTML.panelnobody("标签详情",table.HTML());
    }
}
