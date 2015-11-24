package util.HTML;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 将SQL返回的ResultSet导出成模板供显示
 * 使用时新建一个类继承ResultSetTableHtml，然后重写getCellByHead方法，但不能改变rs指向的行。
 * 最后调用HTML()
 * Created by Syiml on 2015/10/13 0013.
 */
public abstract class ResultSetTableHtml {
    private List<String> tableHead;
    protected ResultSet rs;
    private String cls="table table-striped table-hover table-condensed";
    protected ResultSetTableHtml(ResultSet rs){
        this.rs=rs;
    }
    protected ResultSetTableHtml(){}
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }
    protected void setTableHead(List<String> tableHead) {
        this.tableHead = tableHead;
    }
    protected void addTableHead(String s){
        if(this.tableHead == null){
            this.tableHead=new ArrayList<String>();
        }
        this.tableHead.add(s);
    }
    protected void setTableHead(String... ss){
        tableHead=new ArrayList<String>();
        Collections.addAll(tableHead, ss);
    }
    public void setCls(String s){
        cls=s;
    }
    public String HTML(){
        TableHTML table = new TableHTML();
        table.setClass(cls);
        table.setColname(tableHead);
        try {
            while(rs.next()){
                List<String> row=new ArrayList<String>();
                for(String colname:tableHead){
                    row.add(getCellByHead(colname));
                }
                table.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table.HTML();
    }
    /**
     * 根据当前列名，返回当前cel的内容
     * 可以使用rs
     * @param colname 列名
     * @return 返回当前单元格的HTML代码
     */
    public abstract String getCellByHead(String colname) throws SQLException;
}
