package dao.Mall;

import dao.BaseCache;
import entity.Mall.Order;
import util.SQL.SQL;

import java.util.List;

/**
 * Created by QAQ on 2016/10/4.
 */
public class OrderSQL extends BaseCache<Integer, Order> {
    @Override
    protected Order getByKeyFromSQL(Integer key) {
        return new SQL("SELECT * FROM t_order WHERE id=?",key).queryBean(Order.class);
    }

    public int addOrder(Order order){
        return new SQL("INSERT INTO t_order(username,goodsID,acb,time,isCancel) VALUES(?,?,?,?,?)",
                order.getUsername(),
                order.getGoodsId(),
                order.getAcb(),
                order.getTime(),
                order.isCancel()
        ).isnertGetLastInsertId();
    }

    public int cancelOrder(int id){
        return new SQL("UPDATE t_order SET isCancel=? WHERE id=?",true,id).update();
    }
    public List<Order> getOrdersByGoodsID(int goodsId){
        return new SQL("SELECT * FROM t_order WHERE goodsId=? AND isCancel=?",goodsId,false).queryBeanList(Order.class);
    }
    public List<Order> getOrder(int from,int num){
        return new SQL("SELECT * FROM t_order ORDER BY id DESC LIMIT ?,?",from,num).queryBeanList(Order.class);
    }
    public int getOrderNum(){
        return new SQL("SELECT COUNT(*) FROM t_order").queryNum();
    }
    public int getBuyNum(int goodsId,String username){
        return new SQL("SELECT COUNT(*) FROM t_order WHERE goodsId=? AND username=? AND isCancel=?",goodsId,username,false).queryNum();
    }
}
