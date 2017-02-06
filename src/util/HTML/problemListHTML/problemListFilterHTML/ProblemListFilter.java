package util.HTML.problemListHTML.problemListFilterHTML;

import util.HTML.FromHTML.check.check;
import util.Main;
import entity.User;
import entity.ProblemTag;
import dao.ProblemTagSQL;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.select.select;
import util.HTML.FromHTML.text.text;
import util.HTML.HTML;
import util.HTML.ResultSetPageHtml;
import util.SQL.SQL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 题目列表筛选的显示
 * name和tag是参数
 * Created by Syiml on 2015/10/13 0013.
 */
public class ProblemListFilter extends ResultSetPageHtml {
    String name;
    int tag;
    boolean star;
    private List<String> names=new ArrayList<String>();
    {
        names.add("S");
        names.add("#");
        names.add("标题");
        names.add("通过率");
    }
    public ProblemListFilter(String name, int tag, int nowpage,boolean star) {
        super();
        this.tag=tag;
        this.star = star;
        if(name==null) name="";
        this.name=name;
        String sql = "";
        User u = Main.loginUser();
        if(u == null) star = false;
        boolean vis;
        vis = ( u != null && u.getPermission().getShowHideProblem() );
        if(tag==-1){
            sql+="SELECT problem.pid, ptype, title, ojid, ojspid, visiable, totalAcUser, totalSubmit, status+1 as solved " +
                    (star?",t_star.text ":" ")+
                    "FROM problem LEFT JOIN t_usersolve ON username=? AND t_usersolve.pid=problem.pid "+
                    (star?" JOIN t_star ON star_id=problem.pid AND t_star.type=1 AND t_star.username='"+u.getUsername()+"' ":" ")
                    +" WHERE 1=1 ";
            if(!name.equals("")) sql+="AND (title like ? OR problem.pid=?)";
            if(!vis ) sql+=" AND visiable=1 ";
            sql+="ORDER BY problem.pid ";
        }else{
            sql += "SELECT v_problem_tag.pid, ptype, title, ojid, ojspid, visiable, totalAcUser, totalSubmit, tagid, rating, status+1 as solved " +
                    (star?",t_star.text ":" ")+
                    "FROM v_problem_tag " +
                    "LEFT JOIN problem ON v_problem_tag.pid = problem.pid " +
                    "LEFT JOIN t_usersolve ON username = ? " +
                    "AND t_usersolve.pid = v_problem_tag.pid " +
                    (star?" JOIN t_star ON star_id=problem.pid AND t_star.type=1 AND t_star.username='"+u.getUsername()+"' ":" ")+
                    "WHERE 1 " +
                    "AND tagid = ? " +
                    (vis ? "" : " AND visiable=1 ") +
                    (!name.equals("")?"AND (title like ? OR problem.pid=?) ":"")+
                    "ORDER BY rating DESC ";
        }
        String username;
        if (u != null) username = u.getUsername();
        else username = "";
        SQL sq;
        if(!name.equals("")){
            if(tag==-1){
                sq=new SQL(sql,username,"%"+name+"%",name);
            }else{
                sq=new SQL(sql, username, tag,"%"+name+"%",name);
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
        if(star) names.add("收藏备注");
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
        switch (colname){
            case "S":
                int solved=rs.getInt("solved");
                if(solved==1){
                    return HTML.text("✘", "red");
                }else if(solved==2){
                    return HTML.text("✔","green");
                }else{
                    return "";
                }
            case "#": return rs.getInt("pid")+"";
            case "标题": return HTML.a("Problem.jsp?pid="+rs.getInt("pid"), rs.getString("title"));
            case "通过率":
                int ac=rs.getInt("totalAcUser");
                int sub=rs.getInt("totalSubmit");
                if(sub!=0){
                    return String.format("%.2f", 1.0*ac/sub*100)+
                            "%("+
                            HTML.a("Status.jsp?all=1&pid="+rs.getInt("pid")+"&result=1",ac+"")+
                            "/"+
                            HTML.a("Status.jsp?all=1&pid="+rs.getInt("pid"),sub+"")+")";
                }
                else return "0.00%(0/0)";
            case "标签分": return rs.getInt("rating")+"";
            case "收藏备注": return rs.getString("text");
        }
        return "ERROR";
    }
    @Override
    public String getTopPageLink() {
        return "ProblemListFilter.jsp?name="+name+"&tag="+tag+"&pa="+1+(star?"&star=on":"");
    }
    @Override
    public String getNextPageLink() {
        return "ProblemListFilter.jsp?name="+name+"&tag="+tag+"&pa="+(getNowpage()+1)+(star?"&star=on":"");
    }
    @Override
    public String getPriPageLink() {
        return "ProblemListFilter.jsp?name="+name+"&tag="+tag+"&pa="+(getNowpage()-1)+(star?"s&tar=on":"");
    }
    @Override
    public String rightForm() {
        FormHTML form=new FormHTML();
        form.setAction("ProblemListFilter.jsp");
        form.setType(1);

        check ch = new check("star","我的收藏 ");
        if(star) ch.setChecked();
        form.addForm(ch);

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
        form.setSubmitText("筛选");
        form.addForm(s);
        return form.toHTML();
    }
}
