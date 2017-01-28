package entity.exam;

import com.google.gson.JsonObject;
import net.sf.json.JSONObject;
import util.HTML.problemHTML;

/**
 * 编程题
 * Created by QAQ on 2016/5/25 0025.
 */
public class Exam_Program extends Base_Exam_problem {
    private String description;
    private String input;
    private String output;
    private String sampleInput;
    private String sampleOutput;
    private int timeLimit;
    private int memoryLimit;

    public Exam_Program(Base_Exam_problem base,JSONObject jo){
        copy(base);
        initFromJson(jo);
    }
    public Exam_Program initFromJson(JSONObject jo){
        description = jo.getString("description");
        input = jo.getString("input");
        output = jo.getString("output");
        sampleInput = jo.getString("sampleInput");
        sampleOutput = jo.getString("sampleOutput");
        timeLimit = jo.getInt("timeLimit");
        memoryLimit = jo.getInt("memoryLimit");
        return this;
    }
    public String toJSON(){
        problemHTML p=new problemHTML();
        p.setTitle(title);
        p.setTimeLimit(timeLimit+"MS");
        p.setMenoryLimit(memoryLimit*1024+"KB");
        p.setDis(description);
        p.setInput(input);
        p.setInt64("%I64d");
        p.setOutput(output);
        p.addSample(sampleInput,sampleOutput);
        JSONObject jo=new JSONObject();
        p.setInContest(true);
        jo.put("description",p.getHTML());
        return jo.toString();
    }
}
