package entity.exam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择题
 * {description,answers:[{text,isAnswer},["B选项",false]],isRadio}
 * Created by QAQ on 2016/5/25 0025.
 */
public class Exam_Choice extends Base_Exam_problem {

    private String description;
    private boolean isRadio;
    private List<String> answers;
    private List<Integer> answer;

    public Exam_Choice(){
        answers=new ArrayList<>();
        answer=new ArrayList<>();
    }

    public Exam_Choice initFromJson(JSONObject jo){
        isRadio = jo.getBoolean("isRadio");
        description = jo.getString("description");
        JSONArray ja = jo.getJSONArray("answers");
        for(int i=0;i<ja.size();i++){
            JSONObject jo_answer=ja.getJSONObject(i);
            answers.add(jo_answer.getString("text"));
            if(jo_answer.getBoolean("isAnswer")){
                answer.add(i);
            }
        }
        return null;
    }

    public String getDescription() {
        return description;
    }
    public boolean isRadio() {
        return isRadio;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setRadio(boolean isRadio) {
        this.isRadio = isRadio;
    }
}
