package dao.Mall;

import dao.BaseCache;
import entity.Mall.Goods;
import util.SQL.SQL;

import java.util.List;

/**
 * Created by QAQ on 2016/9/17.
 */
public class GoodsSQL extends BaseCache<Integer, Goods> {

    /**
     * 插入商品，插入数据库时获取自增id并返回
     * @param goods 商品类
     * @return 插入的商品id
     */
    public int addGoods(Goods goods){
        return new SQL("INSERT INTO t_mall(title,acb,stock,des,isHidden,user,time,buyLimit,buyVerifyLimit) VALUES (?,?,?,?,?,?,?,?,?)",
                goods.getTitle(),
                goods.getAcb(),
                goods.getStock(),
                goods.getDes(),
                goods.isHidden(),
                goods.getUser(),
                goods.getTime(),
                goods.getBuyLimit(),
                goods.getBuyVerifyLimit()
            ).isnertGetLastInsertId();
    }
    public int editGoods(Goods goods){
        int ret = new SQL("UPDATE t_mall SET title=?,acb=?,stock=?,des=?,isHidden=?,buyLimit=?,BuyVerifyLimit=? WHERE id=?",
                goods.getTitle(),
                goods.getAcb(),
                goods.getStock(),
                goods.getDes(),
                goods.isHidden(),
                goods.getBuyLimit(),
                goods.getBuyVerifyLimit(),
                goods.getId()
        ).update();
//        this.set_catch(goods.getId(),goods);
        removeCatch(goods.getId());
        return ret;
    }

    public List<Goods> getIndexGoods(){
        return new SQL("SELECT * FROM t_mall WHERE isHidden = false").queryBeanList(Goods.class);
    }

    @Override
    protected Goods getByKeyFromSQL(Integer key) {
        return new SQL("SELECT * FROM t_mall WHERE id=?",key).queryBean(Goods.class);
    }
}
