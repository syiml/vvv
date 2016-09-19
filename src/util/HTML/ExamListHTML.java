package util.HTML;

import entity.exam.Exam;
import servise.ExamMain;
import util.Main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QAQ on 2016/5/27 0027.
 */
public class ExamListHTML extends pageBean {
    List<Exam> list=new ArrayList<>();
    static int pageSize=30;
    int pageNum=1;
    int nowpage=1;
    public ExamListHTML(int page){
        if(page<=0) page=1;
        list= ExamMain.getExams(page, pageSize);
        pageNum = getTotalPageNum(ExamMain.getExamNum(),pageSize);
        nowpage=page;
        addTableHead("#","名称","开始时间","结束时间","状态","考生列表");
    }

    @Override
    public String getTitle() {
        if(Main.loginUserPermission().getExamAdmin()){
            return "考试列表"+HTML.floatRight(HTML.a("ExamAdmin.jsp","admin"));
        }
        return "考试列表";
    }

    @Override
    public int getCurrPageSize() {
        return list.size();
    }

    @Override
    public int getTotalPageNum() {
        return pageNum;
    }

    @Override
    public int getCurrPage() {
        return nowpage;
    }

    @Override
    public String getCellByHead(int i, String colname) {
        Exam e=list.get(i);
        switch (colname) {
            case "#":
                return e.getId() + "";
            case "名称":
                return HTML.a("Exam.jsp?id="+e.getId(), e.getName());
            case "开始时间":
                return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(e.getBegintime());
            case "结束时间":
                return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(e.getEndtime());
            case "状态":
                return e.getStatuHTML();
            case "考生列表":
                return HTML.a("", "查看");
        }
        return "=ERROR=";
    }

    @Override
    public String getLinkByPage(int page) {
        return "ExamList.jsp?page="+page;
    }

    @Override
    public String rightForm() {
        return "";
    }
}
