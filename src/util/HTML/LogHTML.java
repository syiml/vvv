package util.HTML;

import entity.Log;
import util.Main;

import java.util.List;

/**
 * Created by QAQ on 2016-2-27.
 */
public class LogHTML extends pageBean {
    List<Log> list;//显示列表
    int num;//每页个数
    int page;
    int pageNum;
    public LogHTML(int num , int page){
        this.num=num;
        this.page=page;
        list = Main.logs.getLogs((page-1)*num,num);
        int allNum=Main.logs.getLogsNum();
        pageNum=pageBean.getPageNum(allNum,num);
        addTableHead("#","时间","触发人");
        this.setCl("table table-striped table-hover table-condensed");
    }
    @Override
    public String getTitle() {
        return "Log列表";
    }

    @Override
    public int getPageSize() {
        return list.size();
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }

    @Override
    public int getNowPage() {
        return page;
    }

    @Override
    public String getCellByHead(int i, String colname) {
        if(colname.equals("#")){
            return HTML.a("admin.jsp?page=ViewLog&id="+list.get(i).getId(),list.get(i).getId()+"");
        }else if(colname.equals("时间")){
            return  HTML.a("admin.jsp?page=ViewLog&id="+list.get(i).getId(),list.get(i).getTime()+"");
        }else if(colname.equals("触发人")){
            return list.get(i).getSessionUser();
        }
        return ERROR_CELL_TEXT;
    }

    @Override
    public String getLinkByPage(int page) {
        return "admin.jsp?page=ViewLog&page="+page;
    }

    @Override
    public String rightForm() {
        return "";
    }
}
