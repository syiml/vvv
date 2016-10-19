package dao;

import entity.UserVerifyInfo;
import util.SQL.SQL;

import java.util.List;

/**
 * 用户认证DAO
 * Created by QAQ on 2016/10/16.
 */
public class VerifySQL extends BaseCache<Integer, UserVerifyInfo> {

    public UserVerifyInfo getUserVerifyInfo(int key){
        return getBeanByKey(key);
    }

    /**
     * 获取用户最近的认证请求
     * @param username 用户名
     * @return 认证信息
     */
    public UserVerifyInfo getUserVerifyInfo(String username){
        return new SQL("SELECT * FROM t_verify WHERE username=? AND result=?",username,UserVerifyInfo.RESULT_PADDING).queryBean(UserVerifyInfo.class);
    }

    public UserVerifyInfo getLastUserVerifyInfo(String username){
        return new SQL("SELECT * FROM t_verify WHERE username=? ORDER BY id DESC LIMIT 0,1",username).queryBean(UserVerifyInfo.class);
    }

    public int insert(UserVerifyInfo userVerifyInfo){
        return new SQL("INSERT INTO t_verify(VerifyType,username,name,school,gender,faculty,major,cla,no,phone,email,time,result,reason,graduationTime) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    userVerifyInfo.VerifyType,
                    userVerifyInfo.username,
                    userVerifyInfo.name,
                    userVerifyInfo.school,
                    userVerifyInfo.gender,
                    userVerifyInfo.faculty,
                    userVerifyInfo.major,
                    userVerifyInfo.cla,
                    userVerifyInfo.no,
                    userVerifyInfo.phone,
                    userVerifyInfo.email,
                    userVerifyInfo.time,
                    userVerifyInfo.result,
                    "",
                    userVerifyInfo.graduationTime
                ).isnertGetLastInsertId();
    }

    public void updateResult(int id,int result,String reason){
        new SQL("UPDATE t_verify SET result=?,reason=? WHERE id=?",result,reason,id).update();
        removeCatch(id);
    }

    /**
     * 获取所有未处理的申请
     * @return 所有未处理的认证列表
     */
    public List<UserVerifyInfo> getUserVerifyInfoList(){
        return new SQL("SELECT * FROM t_verify WHERE result=0").queryBeanList(UserVerifyInfo.class);
    }

    public List<UserVerifyInfo> getAllUserVerifyInfoList(int from,int num){
        return new SQL("SELECT * FROM t_verify LIMIT ?,?",from,num).queryBeanList(UserVerifyInfo.class);
    }

    @Override
    protected UserVerifyInfo getByKeyFromSQL(Integer key) {
        return new SQL("SELECT * FROM t_verify WHERE id=?",key).queryBean(UserVerifyInfo.class);
    }
}
