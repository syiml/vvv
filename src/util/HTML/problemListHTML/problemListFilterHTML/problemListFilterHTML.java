package util.HTML.problemListHTML.problemListFilterHTML;

import util.Main;
import util.HTML.HTML;
import util.HTML.TableHTML;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/10/4 0004.
 */
public class problemListFilterHTML {
    ResultSet rs;
    private static int pageNum=20;
    int num=0;
    public problemListFilterHTML(int tag, int page){
        rs= Main.problems.getProblemListByTag(tag,page*pageNum,pageNum+1);
    }
    public boolean islast(){
        return num<=pageNum;
    }
    public String HTML(){
        TableHTML table=new TableHTML();
        table.setClass("table table-striped table-hover table-condensed");
        table.addColname("S","#","标题","通过率","标签分");
        try {
            while(rs.next()){
                num++;
                if(num>pageNum) continue;
                List<String> row=new ArrayList<String>();
                //solved
                int solved= rs.getInt("solved");
                if(solved==1){
                    row.add(HTML.text("✘", "red"));
                }else if(solved==2){
                    row.add(HTML.text("✔","green"));
                }else{
                    row.add("");
                }
                //#
                int pid=rs.getInt("pid");
                row.add(pid+"");
                //Title
                row.add(HTML.a("Problem.jsp?pid="+pid, rs.getString("title")));
                //Ratio
                int ac=rs.getInt("acusernum");
                int sub=rs.getInt("submitnum");
                if(sub!=0){
                    row.add(String.format("%.2f", 1.0*ac/sub*100)+
                            "%("+
                            HTML.a("Status.jsp?all=1&pid="+pid+"&result=1",ac+"")+
                            "/"+
                            HTML.a("Status.jsp?all=1&pid="+pid,sub+"")+")");
                }
                else row.add("0.00%(0/0)");
                //rating
                row.add(rs.getInt("rating")+"");
                table.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table.HTML();
    }
}
