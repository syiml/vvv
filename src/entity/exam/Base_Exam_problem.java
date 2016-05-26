package entity.exam;

import entity.IBeanResultSetCreate;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 试题基类
 * Created by QAQ on 2016/5/25 0025.
 */
public class Base_Exam_problem implements IBeanResultSetCreate<Base_Exam_problem> {
    protected String title;
    protected ExamProblemType type;
    protected int score;

    public Base_Exam_problem(){

    }

    public Base_Exam_problem init(ResultSet rs) throws SQLException {
        type = ExamProblemType.getByCode(rs.getInt("type"));
        JSONObject data = JSONObject.fromObject(rs.getString("data"));

        Base_Exam_problem ret;
        if(type == ExamProblemType.CHOICE){
            ret = new Exam_Choice();
        }else if(type == ExamProblemType.CODE_FILL){
            ret = new Exam_CodeFill();
        }else if(type == ExamProblemType.FILL_BLANK){
            ret = new Exam_FillBlank();
        }else if(type == ExamProblemType.PROGRAM){
            ret = new Exam_Program();
        }else return null;

        ret.setType(type);
        ret.initFromJson(data);
        ret.setTitle(rs.getString("title"));
        ret.setScore(0);
        return ret;
    }

    protected Base_Exam_problem initFromJson(JSONObject jo){
        return null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(ExamProblemType type) {
        this.type = type;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public ExamProblemType getType() {
        return type;
    }

    public int getScore() {
        return score;
    }
}
