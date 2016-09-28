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
        int id =  new SQL("INSERT INTO t_mall(title,cover,acb,stock,des,isHidden) VALUES (?,?,?,?,?);SELECT last_insert_id();").queryNum();
        goods.setId(id);
        set_catch(id,goods);
        return id;
    }

    public List<Goods> getIndexGoods(){
        return new SQL("SELECT * FROM t_mall WHERE isHidden = false").queryBeanList(Goods.class);
    }

    @Override
    protected Goods getByKeyFromSQL(Integer key) {
        return new SQL("SELECT * FROM t_mall WHERE id=?",key).queryBean(Goods.class);
    }
}
