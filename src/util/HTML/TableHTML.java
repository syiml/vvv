package util.HTML;

import java.util.*;

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
    public String getColname(int i){
        return colname.get(i);
    }
    public int getColnameSize(){
        return colname.size();
    }
    public void addColname(String s){colname.add(s);}
    public void addColname(String... ss){
        Collections.addAll(colname, ss);
    }
    public void addRow(List<String> row){
        this.cell.add(row);
    }
    public void addRow(String... ss){
        List<String> row=new ArrayList<String>();
        Collections.addAll(row, ss);
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
        StringBuilder s=new StringBuilder("<table class='"+cl+"'>");
        s.append("<thead><tr ").append(clsHTML(0, -1)).append(">");
        for(int i=0;i<colname.size();i++){
            s.append("<th ").append(clsHTML(0, i)).append(">").append(colname.get(i)).append("</th>");
        }
        s.append("</tr></thead><tbody>");
        for(int i=0;i<cell.size();i++){
            s.append("<tr ").append(clsHTML(i + 1, -1)).append(">");
            for(int j=0;j<colname.size()&&j<cell.get(i).size();j++){
                s.append("<td ").append(clsHTML(i + 1, j)).append(">").append(cell.get(i).get(j)).append("</td>");
            }
            s.append("</tr>");
        }
        s.append("</tbody></table>");
        return s.toString();
    }
}
