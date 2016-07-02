package entity.exam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 填空题
 * Created by QAQ on 2016/5/25 0025.
 */
public class Exam_FillBlank extends Base_Exam_problem {
    private String description;
    private String answer;

    public Exam_FillBlank(Base_Exam_problem base,JSONObject jo){
        copy(base);
        initFromJson(jo);
    }

    public Exam_FillBlank initFromJson(JSONObject jo){
        description = jo.getString("description");
        answer = jo.getString("answer");
        return this;
    }
    public String toJSON(){
        JSONObject jo=new JSONObject();
        jo.put("type",type.getCode());
        jo.put("description",description);
        return jo.toString();
    }
    public boolean isRight(String answer){
        return this.answer.equals(answer);
    }
}
