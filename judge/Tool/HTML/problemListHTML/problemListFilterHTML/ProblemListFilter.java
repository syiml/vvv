package Tool.HTML.problemListHTML.problemListFilterHTML;

import Main.Main;
import Main.User.User;
import ProblemTag.ProblemTag;
import ProblemTag.ProblemTagSQL;
import Tool.HTML.FromHTML.FormHTML;
import Tool.HTML.FromHTML.select.select;
import Tool.HTML.FromHTML.text.text;
import Tool.HTML.HTML;
import Tool.HTML.ResultSetPageHtml;
import Tool.SQL.SQL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 题目列表筛选的显示
 * name和tag是参数
 * Created by Syiml on 2015/10/13 0013.
 */
public class ProblemListFilter extends ResultSetPageHtml{
    String name;
    int tag;
    private List<String> names=new ArrayList<String>();
    {
        names.add("S");
        names.add("#");
        names.add("标题");
        names.add("通过率");
    }
    public ProblemListFilter(String name, int tag, int nowpage) {
        super();
        this.tag=tag;
        if(name==null) name="";
        this.name=name;
        String sql = "";
        User u = Main.loginUser();
        boolean vis;
        vis = ( u != null && u.getPermission().getShowHideProblem() );
        if(tag==-1){
            sql+="SELECT v_problem.pid, ptype, title, ojid, ojspid, visiable, acusernum, submitnum, solved+1 as solved " +
                    "FROM v_problem LEFT JOIN usersolve_view ON username=? AND usersolve_view.pid=v_problem.pid WHERE 1=1 ";
            if(!name.equals("")) sql+="AND title like ? ";
            if(!vis ) sql+=" AND visiable=1 ";
            sql+="ORDER BY v_problem.pid ";
        }else{
            sql += "SELECT v_problem_tag.pid, ptype, title, ojid, ojspid, visiable, acusernum, submitnum, tagid, rating, solved+1 as solved " +
                    "FROM v_problem_tag " +
                    "LEFT JOIN v_problem ON v_problem_tag.pid = v_problem.pid " +
                    "LEFT JOIN usersolve_view ON username = ? " +
                    "AND usersolve_view.pid = v_problem_tag.pid " +
                    "WHERE 1 " +
                    "AND tagid = ? " +
                    (vis ? "" : " AND visiable=1 ") +
                    (!name.equals("")?"AND title like ? ":"")+
                    "ORDER BY rating DESC ";
        }
        String username;
        if (u != null) username = u.getUsername();
        else username = "";
        SQL sq;
        if(!name.equals("")){
            if(tag==-1){
                sq=new SQL(sql,username,"%"+name+"%");
            }else{
                sq=new SQL(sql, username, tag,"%"+name+"%");
            }
        }else{
            if(tag==-1){
                sq=new SQL(sql,username);
            }else{
                sq=new SQL(sql, username, tag);
            }
        }
        super.setSql(sq);
        super.setNowpage(nowpage);
        if(tag!= -1) names.add("标签分");
        super.setTableHead(names);
    }
    @Override
    public String getTitle() {
        return "题目筛选列表";
    }
    @Override
    public int getPageSize() {
        return 30;
    }
    @Override
    public String getCellByHead(String colname) throws SQLException {
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
    @Override
    public String getTopPageLink() {
        return "ProblemListFilter.jsp?name="+name+"&tag="+tag+"&pa="+1;
    }
    @Override
    public String getNextPageLink() {
        return "ProblemListFilter.jsp?name="+name+"&tag="+tag+"&pa="+(getNowpage()+1);
    }
    @Override
    public String getPriPageLink() {
        return "ProblemListFilter.jsp?name="+name+"&tag="+tag+"&pa="+(getNowpage()-1);
    }
    @Override
    public String rightForm() {
        FormHTML form=new FormHTML();
        form.setAction("ProblemListFilter.jsp");
        form.setType(1);
        text t=new text("name", "标题");
        t.setValue(name);
        form.addForm(t);

        select s=new select("tag","标签");
        s.add(-1," - ");
        for(ProblemTag pt: ProblemTagSQL.problemTag){
            s.add(pt.getId(),pt.getName());
        }
        s.setType(1);
        s.setValue(tag+"");

        form.addForm(s);
        return form.toHTML();
    }
}
