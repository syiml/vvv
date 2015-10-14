package Tool.HTML.problemListHTML.problemListFilterHTML;

import Tool.HTML.HTML;
import Tool.HTML.ResultSetTableHtml;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Syiml on 2015/10/13 0013.
 */
public class problemListFilterTableHTML extends ResultSetTableHtml {
    private List<String> names=new ArrayList<String>();
    {
        names.add("S");
        names.add("#");
        names.add("标题");
        names.add("通过率");
    }
    public problemListFilterTableHTML(ResultSet rs,int type) {
        super(rs);
        super.setTableHead(names);
        if(type==1){
            super.addTableHead("标签分");
        }
    }
    public problemListFilterTableHTML(ResultSet rs) {
        super(rs);
        super.setTableHead(names);
    }
    public String getCellByHead(String colname) throws SQLException{
        int x=names.indexOf(colname);
        switch (x){
            case 0:
                    int solved=rs.getInt("solved");
                    if(solved==1){
                        return HTML.text("✘", "red");
                    }else if(solved==2){
                        return HTML.text("✔","green");
                    }else{
                        return "";
                    }
            case 1: return rs.getInt("pid")+"";
            case 2: return HTML.a("Problem.jsp?pid="+rs.getInt("pid"), rs.getString("title"));
            case 3:
                    int ac=rs.getInt("acusernum");
                    int sub=rs.getInt("submitnum");
                    if(sub!=0){
                        return String.format("%.2f", 1.0*ac/sub*100)+
                                "%("+
                                HTML.a("Status.jsp?all=1&pid="+rs.getInt("pid")+"&result=1",ac+"")+
                                "/"+
                                HTML.a("Status.jsp?all=1&pid="+rs.getInt("pid"),sub+"")+")";
                    }
                    else return "0.00%(0/0)";
            case 4: return rs.getInt("rating")+"";
        }
        return "ERROR";
    }
}
