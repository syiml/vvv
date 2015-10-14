package Tool.HTML.TableHTML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/8.
 */
public class TableHTML {
    int col;
    String cl;
    List<String> colname;
    List<List<String>> cell;
    Map<Integer,String> cls;
    public TableHTML(){
        colname = new ArrayList<String>();
        cell = new ArrayList<List<String>>();
        cls = new HashMap<Integer, String>();
    }
    public void setClass(String cl){this.cl=cl; }
    public void setColname(List<String> colname){
        this.colname=colname;
    }
    public void addColname(String s){colname.add(s);}
    public void addColname(String... ss){for(String s:ss)colname.add(s);}
    public void addRow(List<String> row){
        this.cell.add(row);
    }
    public void addRow(String... ss){
        List<String> row=new ArrayList<String>();
        for(String s:ss){
            row.add(s);
        }
        this.cell.add(row);
    }
    public void addCl(int r,int c,String s){
        cls.put(r*10000+c,s);
    }
    public String clsHTML(int i,int j){
        if(cls.containsKey(i*10000+j)){
            return "class='"+cls.get(i*10000+j)+"'";
        }
        return "";
    }
    public String HTML(){
        String s="<table class='"+cl+"'>";
        s+="<thead><tr "+clsHTML(0,-1)+">";
        for(int i=0;i<colname.size();i++){
            s+="<th "+clsHTML(0,i)+">"+colname.get(i)+"</th>";
        }
        s+="</tr></thead><tbody>";
        for(int i=0;i<cell.size();i++){
            s+="<tr "+clsHTML(i+1,-1)+">";
            for(int j=0;j<colname.size()&&j<cell.get(i).size();j++){
                s+="<td "+clsHTML(i+1,j)+">"+cell.get(i).get(j)+"</td>";
            }
            s+="</tr>";
        }
        s+="</tbody></table>";
        return s;
    }
}
