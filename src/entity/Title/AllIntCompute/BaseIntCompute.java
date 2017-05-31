package entity.Title.AllIntCompute;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Event.BaseTitleEvent;

/**
 * Created by QAQ on 2017/5/29.
 */
public class BaseIntCompute {
    private String name1,name2;
    private String opt;

    private BaseIntCompute(String name1, String opt, String name2){
        this.name1 = name1;
        this.name2 = name2;
        this.opt = opt;
    }
    public int getInt(BaseTitleEvent event,int jd){
        int value1 = name1.equals("jd")?jd:event.getInt(name1);
        int value2 = name2.equals("jd")?jd:event.getInt(name2);
        switch (opt){
            case "+":return value1+value2;
            case "-":return value1-value2;
            case "*":return value1*value2;
            case "/":return value1/value2;
        }
        return 0;
    }

    public static BaseIntCompute getIntCompute(Object jd_value){
        if(jd_value instanceof Integer){
            return new BaseIntCompute((jd_value)+"" , "+" ,"0");
        }
        if(jd_value instanceof String){
            switch ((String)jd_value){
                case "++": return new BaseIntCompute("jd","+","1");
                default: return new BaseIntCompute((String)jd_value,"+","0");
            }
        }
        JSONObject jsonObject = JSONObject.fromObject(jd_value);
        switch (jsonObject.getString("type")){
            case "opt":{
                JSONArray ja = jsonObject.getJSONArray("value");
                return new BaseIntCompute(ja.getString(0),ja.getString(1),ja.getString(2));
            }
        }
        return null;
    }
}
