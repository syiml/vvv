package servise;

import action.Mall.ActionAddGoods;
import dao.Mall.GoodsSQL;
import dao.Mall.OrderSQL;
import entity.Mall.Goods;
import entity.Mall.Order;
import entity.User;
import util.Main;
import util.MainResult;
import util.Tool;

import java.util.List;

/**
 * Created by QAQ on 2016/9/27.
 */
public class MallMain {
    private static GoodsSQL goodsSQL = new GoodsSQL();
    private static OrderSQL orderSQL = new OrderSQL();
    public static synchronized MainResult buy(int goodsId){//买东西
        Goods goods = goodsSQL.getBeanByKey(goodsId);
        if(goods == null) return MainResult.ARR_ERROR;
        User u = Main.loginUser();
        if(u==null) return MainResult.NO_LOGIN;
        if(goods.getStock()<=0) return MainResult.NO_STOCK;
        if(goods.isHidden() ) return MainResult.NO_PERMISSION;
        if(u.getAcb()<goods.getAcb()) return MainResult.ACB_NOT_ENOUGH;
        if(goods.getBuyLimit()>=0) {
            int buyNum = MallMain.getBuyNum(goodsId, u.getUsername());
            if(buyNum >= goods.getBuyLimit()){
                return MainResult.BUY_NUM_LIMIT;
            }
        }
        Order order = new Order();
        order.setGoodsId(goodsId);
        order.setAcb(goods.getAcb());
        order.setCancel(false);
        order.setTime(Tool.now());
        order.setUsername(u.getUsername());

        //扣钱
        if(Main.users.subACB(u.getUsername(),goods.getAcb(),AcbOrderType.MALL_BUY,"订单号："+orderId)<=0) return MainResult.FAIL;
        //减库存
        goods.setStock(goods.getStock()-1);
        if(goodsSQL.editGoods(goods)<=0) return MainResult.FAIL;
        return MainResult.SUCCESS;
    }
    public static synchronized MainResult cancelOrder(int id){
        User u = Main.loginUser();
        if(u==null) return MainResult.NO_LOGIN;
        if(!u.getPermission().getMallAdmin()) return MainResult.NO_PERMISSION;
        Order order = orderSQL.getBeanByKey(id);
        if(order == null) return MainResult.ARR_ERROR;
        if(order.isCancel()) return MainResult.FAIL;
        //修改库存
        Goods goods = goodsSQL.getBeanByKey(order.getGoodsId());
        goods.setStock(goods.getStock()+1);
        goodsSQL.editGoods(goods);
        //修改账单属性
        orderSQL.cancelOrder(id);
        //恢复ACB
        User us = Main.users.getUser(order.getUsername());
        if(us == null) return MainResult.FAIL;
        Main.users.addACB(order.getUsername(),order.getAcb());

        return MainResult.SUCCESS;
    }

    public static Goods getGoods(int id){
        return goodsSQL.getBeanByKey(id);
    }
    public static int addGoods(ActionAddGoods goodsAction){
        User u = Main.loginUser();
        if(u==null) return -1;
        Goods goods = new Goods();
        goods.setId(-1);
        goods.setAcb(goodsAction.getAcb());
        goods.setDes(goodsAction.getDes());
        goods.setHidden(goodsAction.getIsHidden()!=null);
        goods.setStock(goodsAction.getStock());
        goods.setTitle(goodsAction.getTitle());
        goods.setBuyLimit(goodsAction.getBuyLimit());
        goods.setTime(Tool.now());
        goods.setUser(u.getUsername());
        return goodsSQL.addGoods(goods);
    }
    public static int editGoods(ActionAddGoods goodsAction) {
        User u = Main.loginUser();
        if(u==null) return -1;
        Goods goods = goodsSQL.getBeanByKey(goodsAction.getId());
        if(goods == null) return -1;

        goods.setAcb(goodsAction.getAcb());
        goods.setDes(goodsAction.getDes());
        goods.setHidden(goodsAction.getIsHidden()!=null);
        goods.setStock(goodsAction.getStock());
        goods.setTitle(goodsAction.getTitle());
        goods.setBuyLimit(goodsAction.getBuyLimit());
        int ret =  goodsSQL.editGoods(goods);
        if(ret <=0) return -1;
        return 0;
        //goods.setT(Tool.now());
        //goods.setUser(u.getUsername());

    }
    public static List<Order> getOrdersByGoodsID(int goodsID){
        return orderSQL.getOrdersByGoodsID(goodsID);
    }

    public static GoodsSQL getGoodsSQL() {
        return goodsSQL;
    }
    public static List<Order> getOrder(int from,int num){
        return orderSQL.getOrder(from,num);
    }
    public static int getOrderNum(){
        return 0;
    }
    public static int getBuyNum(int goodsID,String username){
        return orderSQL.getBuyNum(goodsID,username);
    }
}
