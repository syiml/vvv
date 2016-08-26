package dao;

import entity.exam.*;
import util.SQL;

import java.util.List;

/**
 * Created by QAQ on 2016/5/26 0026.
 */
public class ExamSQL extends BaseCache<Integer,Exam> {

    @Override
    protected Exam getByKeyFromSQL(Integer key) {
        Exam e = new SQL("SELECT * FROM t_exam WHERE id=?",key).queryBean(Exam.class);

        return e;
    }

    public List<Exam> getExams(int from,int num){
        return new SQL("SELECT * FROM t_exam ORDER BY endTime<now(),begintime DESC LIMIT ?,?",from,num).queryBeanList(Exam.class);
    }
    public int getExamsNum(){
        return new SQL("SELECT COUNT(*) FROM t_exam").queryNum();
    }

    protected void addToSQL(Exam e){
        if(e.getId()==-1){//add
            new SQL("INSERT INTO t_exam(name,begintime,endtime,info,createuser,data) VALUES(?,?,?,?,?,?)",
                    e.getName(),e.getBegintime(),e.getEndtime(),e.getInfo(),e.getCreateuser(),e.getData());
        }else{//update
            new SQL("UPDATE t_exam SET name=?,begintime=?,endtime=?,info=?,data=? WHERE id=?",
                    e.getName(),e.getBegintime(),e.getEndtime(),e.getInfo(),e.getData(),e.getId());
            removeCatch(e.getId());
        }
    }
}
