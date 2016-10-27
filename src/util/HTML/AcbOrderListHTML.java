package util.HTML;

import entity.AcbOrder;
import entity.User;
import util.Main;

import java.util.List;

/**
 * Created by QAQ on 2016/10/26.
 */
public class AcbOrderListHTML extends pageBean {
    public static final int pageSize = 30;
    String username;
    int page;
    List<AcbOrder> list;
    int totalNum;
    public AcbOrderListHTML(String username,int page){
        if(page<=0) page = 1;
        this.page = page;
        this.username = username;
        list = Main.acbOrderSQL.getAcbOrderList(username,(page-1)*pageSize,pageSize);
        totalNum = Main.acbOrderSQL.getAcbOrderListNun(username);
        if(username==null||username.equals("")){
            addTableHead("id","用户名","数量变化","类型","备注","时间");

        }else{
            addTableHead("id","数量变化","类型","备注","时间");
        }
    }

    @Override
    public String getTitle() {
        return "ACB账单";
    }

    @Override
    public int getCurrPageSize() {
        return list.size();
    }

    @Override
    public int getTotalPageNum() {
        return getTotalPageNum(totalNum,pageSize);
    }

    @Override
    public int getCurrPage() {
        return page;
    }

    @Override
    public String getCellByHead(int i, String colname) {
        AcbOrder acbOrder = list.get(i);
        switch (colname) {
            case "id":
                return acbOrder.id + "";
            case "数量变化":
                return acbOrder.change + "";
            case "类型":
                return acbOrder.reason.getDes();
            case "备注":
                return acbOrder.mark;
            case "用户名":
                User u = Main.users.getUser(acbOrder.username);
                return u.getUsernameHTML();
            case "时间":
                return acbOrder.time.toString().substring(0,16);
        }
        return ERROR_CELL_TEXT;
    }

    @Override
    public String getLinkByPage(int page) {
        if(username==null||username.equals("")){
            return "AcbOrderList.jsp?page="+page;
        }
        return "AcbOrderList.jsp?page="+page+"&username="+username;
    }

    @Override
    public String rightForm() {
        return "";
    }
}
