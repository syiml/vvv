package util.HTML;

import action.ExamProblem;
import entity.User;
import entity.exam.Base_Exam_problem;
import servise.ExamMain;
import util.Main;

import java.util.List;

/**
 * Created by QAQ on 2016/5/31 0031.
 */
public class ExamProblemList extends pageBean {
    private List<Base_Exam_problem> list;
    private int pageSize=20;
    private User u;
    private int pageNum;
    private int page;
    public ExamProblemList(int page){
        if(page<=0) page=1;
        this.page=page;
        u=Main.loginUser();
        list= ExamMain.getProblems(u.getUsername(),page,pageSize);
        pageNum = getPageNum(ExamMain.getProblemsNum(u.getUsername()),pageSize);
        addTableHead("#","标题","类型","公开","作者");
    }
    @Override
    public String getTitle() {
        return "我的试题列表";
    }

    @Override
    public int getPageSize() {
        return list.size();
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }

    @Override
    public int getNowPage() {
        return page;
    }

    @Override
    public String getCellByHead(int i, String colname) {
        Base_Exam_problem pro=list.get(i);
        switch (colname) {
            case "#":    return pro.getId() + "";
            case "标题": return pro.getTitle();
            case "类型": return pro.getType().toString();
            case "公开": return pro.getIsPublic() == 1 ? "是" : "";
            case "作者": return pro.getCreateUser();
        }
        return "=ERROR=";
    }

    @Override
    public String getLinkByPage(int page) {
        return "ExamAdmin.jsp?page="+page;
    }

    @Override
    public String rightForm() {
        return "";
    }
}
