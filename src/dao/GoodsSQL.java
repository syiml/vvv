package dao;

import entity.Goods;
import util.SQL.SQL;

import java.util.List;

/**
 * Created by QAQ on 2016/9/17.
 */
public class GoodsSQL extends BaseCache<Integer, Goods> {

    public List<Goods> getIndexGoods(){
        return new SQL("SELECT * FROM t_mall WHERE isHidden = false").queryBeanList(Goods.class);
    }


    @Override
    protected Goods getByKeyFromSQL(Integer key) {
        return new SQL("SELECT * FROM t_mall WHERE id=?",key).queryBean(Goods.class);
    }
}
