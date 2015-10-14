package Tool.HTML;

import Tool.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Syiml on 2015/10/13 0013.
 */
public abstract class ResultSetPageHtml extends ResultSetTableHtml {
    SQL sql;
    int nowpage;//begin from 1
    protected ResultSetPageHtml(){}
    protected ResultSetPageHtml(SQL sql,int nowpage) {
        super();
        this.sql=sql;
        this.nowpage=nowpage;
    }
    public void setSql(SQL sql){
        this.sql=sql;
    }
    public void setNowpage(int nowpage){
        this.nowpage=nowpage;
    }
    public int getNowpage(){
        return nowpage;
    }
    public abstract String getTitle();
    public abstract int getPageSize();
    public abstract String getCellByHead(String colname) throws SQLException;
    public abstract String getTopPageLink();
    public abstract String getNextPageLink();
    public abstract String getPriPageLink();
    public abstract String rightForm();
    private boolean isLast(){
        try {
            rs.last();
            if(rs.getRow()==getPageSize()+1){
                rs.first();
                return false;
            }
            else{
                rs.first();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private String page(){
        String ret= "<div class='btn-toolbar' role='toolbar'>" +
                "<div class='btn-group' role='group'>" +
                "<a role='button' class='btn btn-default btn-sm' href='"+getTopPageLink()+"'>" +
                "首页" +
                "</a>" +
                "</div>" +
                "<div class='btn-group' role='group'>";
        if(nowpage==1) ret+="<a role='button' class='btn btn-default btn-sm' disabled='disabled'>";
        else  ret+="<a role='button' class='btn btn-default btn-sm' href='"+getPriPageLink()+"'>";
        ret+="上一页" +
                "</a>" +
                "<a role='button' class='btn btn-default btn-sm'>" +
                nowpage +
                "</a>";
        if(!isLast())ret+="<a role='button' class='btn btn-default btn-sm' href='"+getNextPageLink()+"'>";
        else ret+="<a role='button' class='btn btn-default btn-sm' disabled='disabled'>";
        ret+="下一页" +
                "</a>" +
                "</div>" +
                "</div>";
        return ret;
    }
    public String HTML(){
        super.setRs(sql.query((nowpage-1)*getPageSize(),getPageSize()+1));
        String table=super.HTML();
        return HTML.panelnobody(getTitle(),HTML.div("panel-body","style='padding:5px'",HTML.floatLeft(page())+HTML.floatRight(rightForm()))+table);
    }
}
