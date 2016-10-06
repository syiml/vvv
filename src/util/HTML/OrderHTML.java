package util.HTML;

import entity.Mall.Order;
import servise.MallMain;

import java.util.List;

import static servise.MallMain.getOrderNum;

/**
 * Created by QAQ on 2016/10/5.
 */
public class OrderHTML extends pageBean {
    private static int everyPageNum = 30;
    List<Order> list = null;
    int page;
    private int totalNum;

    public OrderHTML(int page){
        this.page = page;
        list = MallMain.getOrder((page-1)*everyPageNum,everyPageNum);
        totalNum = MallMain.getOrderNum();
        this.addTableHead("#","goodsId","username","acb","time","cancel","admin");
    }

    @Override
    public String getTitle() {
        return "账单";
    }

    @Override
    public int getCurrPageSize() {
        return list.size();
    }

    @Override
    public int getTotalPageNum() {
        return getTotalPageNum(totalNum,everyPageNum);
    }

    @Override
    public int getCurrPage() {
        return page;
    }

    @Override
    public String getCellByHead(int i, String colname) {
        Order order = list.get(i);
        switch (colname){
            case "#": return order.getId()+"";
            case "goodsId": return HTML.a("goods.jsp?id="+order.getGoodsId(),order.getGoodsId()+"");
            case "username": return order.getUsername()+"";
            case "acb": return order.getAcb()+"";
            case "time": return order.getTime()+"";
            case "cancel": return order.isCancel()+"";
            case "admin":return order.isCancel()?"":HTML.a("cancelOrder.action?id="+order.getId(),"退单");
        }
        return ERROR_CELL_TEXT;
    }

    @Override
    public String getLinkByPage(int page) {
        return "admin.jsp?page=OrderList&pa="+page;
    }

    @Override
    public String rightForm() {
        return "";
    }
}
