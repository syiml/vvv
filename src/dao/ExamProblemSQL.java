package dao;


import entity.exam.Base_Exam_problem;
import entity.exam.ExamProblemType;
import entity.exam.Exam_Choice;
import entity.exam.Exam_CodeFill;
import util.SQL;

import java.util.List;

/**
 * Created by QAQ on 2016/5/27 0027.
 */
public class ExamProblemSQL extends BaseCache<Integer,Base_Exam_problem> {

    @Override
    protected Base_Exam_problem getByKeyFromSQL(Integer id) {
        Base_Exam_problem pro = new SQL("SELECT * FROM t_exam_problem WHERE id=?", id).queryBean(Base_Exam_problem.class);
        Base_Exam_problem ret=null;
        if(pro.getType()==ExamProblemType.CHOICE){
            ret=new Exam_Choice(pro,pro.data);
        }else if(pro.getType() == ExamProblemType.CODE_FILL){
            ret=new Exam_CodeFill(pro,pro.data);
        }
        return ret;
    }

    public List<Base_Exam_problem> getProblems(String user,int from,int num){
        return new SQL("SELECT * FROM t_exam_problem WHERE user=? OR public=1 LIMIT ?,?",user,from,num).queryBeanList(Base_Exam_problem.class);
    }
    public int getProblemsNum(String user){
        return new SQL("SELECT COUNT(*) FROM t_exam_problem WHERE user=? OR public=1",user).queryNum();
    }
}
