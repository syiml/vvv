package util.HTML;

import dao.Mall.GoodsSQL;
import entity.Mall.Goods;
import entity.Mall.Order;
import entity.User;
import servise.MallMain;
import util.Main;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static servise.MallMain.getOrdersByGoodsID;

/**
 * Created by QAQ on 2016/9/17.
 */
public class MallHTML {
    private static GoodsSQL goodsSQL = MallMain.getGoodsSQL();
    public static String index(){
        StringBuilder sb = new StringBuilder();
        List<Goods> list = goodsSQL.getIndexGoods();
        for(Goods goods : list){
            sb.append(indexEveryGoods(goods));
        }
        return sb.toString();
    }

    public static String coverImg(int id){
        return "<img src='pic/goods/"+id+".jpg' class='coverImg' onerror='this.src=\"pic/defaulthead.jpg\"' />";
    }
    private static String indexEveryGoods(Goods goods){
        StringBuilder sb = new StringBuilder();
        String coverImg = coverImg(goods.getId());
        String title = HTML.div("goods-title",HTML.text(goods.getTitle(),4));
        String acb = HTML.center(HTML.text(goods.getAcb()+" ACB",3,"#f66"));
        String stock = HTML.floatRight(HTML.text("剩余:"+goods.getStock()+"　","#aaa"));
        sb.append(HTML.a("goods.jsp?id="+goods.getId(),HTML.div("goods",coverImg+title+stock+acb)));
        return sb.toString();
    }
    public static String goodsBuyRecord(int goodsId){
        List<Order> orders = MallMain.getOrdersByGoodsID(goodsId);
        TableHTML tableHTML = new TableHTML();
        tableHTML.setClass("table table-condensed table-hover");
        tableHTML.addColname("已购买的用户");
        for (Order order : orders) {
            List<String> row = new ArrayList<>();
            User u = Main.users.getUser(order.getUsername());
            row.add(u.getUsernameHTML());
            //row.add(order.getTime() + "");
            tableHTML.addRow(row);
        }
//        return HTML.panelnobody("已购买记录",tableHTML.HTML());
        return tableHTML.HTML();
    }
}
