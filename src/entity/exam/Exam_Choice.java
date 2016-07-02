package entity.exam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择题
 * {description,answers:[[text,isAnswer],["B选项",false]],isRadio}
 * Created by QAQ on 2016/5/25 0025.
 */
public class Exam_Choice extends Base_Exam_problem {

    private String description;
    private boolean isRadio;//是否单选
    private List<String> answers=new ArrayList<>();
    private List<Integer> answer=new ArrayList<>();

    public Exam_Choice() {

    }
    public Exam_Choice(Base_Exam_problem base,JSONObject jo){
        this.title=base.title;
        this.score=base.score;
        this.type=base.type;
        this.createUser=base.createUser;
        this.isPublic=base.isPublic;
        initFromJson(jo);
    }
    public Exam_Choice initFromJson(JSONObject jo){
        isRadio = jo.getBoolean("isRadio");
        description = jo.getString("description");
        JSONArray ja = jo.getJSONArray("answers");
        for(int i=0;i<ja.size();i++){
            JSONArray ja_answer=ja.getJSONArray(i);
            answers.add(ja_answer.getString(0));
            if(ja_answer.getBoolean(1)){
                answer.add(i);
            }
        }
        return this;
    }
    public String toJSON(){
        JSONObject jo=new JSONObject();
        jo.put("type",type.getCode());
        jo.put("description",description);
        jo.put("isRadio",isRadio);
        jo.put("ans",answers);
        return jo.toString();
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
