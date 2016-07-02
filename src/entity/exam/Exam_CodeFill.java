package entity.exam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.HTML.HTML;
import util.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码填空题
 * 可能有多个空。
 * 所有空都填对才能得分
 * Created by QAQ on 2016/5/25 0025.
 */
public class Exam_CodeFill extends Base_Exam_problem {
    private String description;
    private List<String> codes=new ArrayList<>();

    public Exam_CodeFill(Base_Exam_problem base,JSONObject jo){
        copy(base);
        initFromJson(jo);
    }

    public Exam_CodeFill initFromJson(JSONObject jo){
        description = jo.getString("description");
        JSONArray ja = jo.getJSONArray("codes");
        for(int i=0;i<ja.size();i++){
            codes.add(ja.getString(i));
        }
        return this;
    }
    public String toJSON(){
        JSONObject jo=new JSONObject();
        jo.put("type",type.getCode());
        jo.put("description",description);
        jo.put("codes", codes);
        return jo.toString();
    }
    public String getCode(String answer){
        JSONArray jo=JSONArray.fromObject(answer);
        StringBuilder ret=new StringBuilder();
        for(int i=0;i<codes.size();i++){
            ret.append(codes.get(i));
            if(i<jo.size()){
                ret.append(jo.getString(i));
            }
        }
        return ret.toString();
    }
}
