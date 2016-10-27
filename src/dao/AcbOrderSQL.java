package dao;

import entity.AcbOrder;
import util.SQL.SQL;

import java.util.List;

/**
 * Created by QAQ on 2016/10/26.
 */
public class AcbOrderSQL {
    public List<AcbOrder> getAcbOrderList(String username,int from,int num){
        if(username==null||username.equals("")){
            return new SQL("SELECT * FROM t_acborder ORDER BY id DESC LIMIT ?,?",from,num).queryBeanList(AcbOrder.class);
        }
        return new SQL("SELECT * FROM t_acborder WHERE username=? ORDER BY id DESC LIMIT ?,?",username,from,num).queryBeanList(AcbOrder.class);
    }
    public int getAcbOrderListNun(String username){
        if(username==null||username.equals("")){
            return new SQL("SELECT COUNT(*) FROM t_acborder").queryNum();
        }
        return new SQL("SELECT COUNT(*) FROM t_acborder WHERE username=?",username).queryNum();
    }

    public int addAcbOrder(AcbOrder acbOrder){
        return new SQL("INSERT INTO t_acborder(username,acbchange,reason,mark,time) VALUES(?,?,?,?,?)",
                acbOrder.username,
                acbOrder.change,
                acbOrder.reason.getId(),
                acbOrder.mark,
                acbOrder.time
        ).isnertGetLastInsertId();
    }
}
