package servise;

import dao.ExamProblemSQL;
import dao.ExamSQL;
import entity.User;
import entity.exam.Base_Exam_problem;
import entity.exam.Exam;
import entity.exam.Exam_Program;
import util.Main;

import java.util.List;

/**
 * Created by QAQ on 2016/5/26 0026.
 */
public class ExamMain {
    private static ExamSQL examSQL = new ExamSQL();
    private static ExamProblemSQL examProblemSQL = new ExamProblemSQL();

    public static Base_Exam_problem getProblem(int id){
        return examProblemSQL.getBeanByKey(id);
    }
    public static Exam getExam(int id){
        return examSQL.getBeanByKey(id);
    }
    public static List<Exam> getExams(int page,int pageSize){
        return examSQL.getExams((page-1)*pageSize,pageSize);
    }
    public static int getExamNum(){
        return examSQL.getExamsNum();
    }
    public static List<Base_Exam_problem> getProblems(String user,int page,int pageSize){
        return examProblemSQL.getProblems(user,(page-1)*pageSize,pageSize);
    }
    public static int getProblemsNum(String user){
        return examProblemSQL.getProblemsNum(user);
    }
    public static String getProblemHTML(int id){
        Base_Exam_problem pro = getProblem(id);
        User u= Main.loginUser();
        if(u==null||!u.getPermission().getExamAdmin()||(pro.getIsPublic()==0&&!pro.getCreateUser().equals(u.getUsername()))){
            return "没有权限";
        }
        return null;
    }
}
