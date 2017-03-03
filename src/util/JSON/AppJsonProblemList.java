package util.JSON;

import entity.User;
import net.sf.json.JSONObject;
import util.HTML.problemListHTML.problemView;
import util.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by QAQ on 2016/9/21.
 */
public class AppJsonProblemList extends BaseJsonPageBean<problemView> {
    private String search;
    private int index;//第几个20条
    private Map<Integer,Integer> status_map;

    public AppJsonProblemList(int nowPage, int index, String search) {
        super(nowPage, 100);
        this.search = search;
        this.index = index;
    }

    @Override
    protected List<problemView> getList(int from, int num) {
        return Main.problems.getProblems(from + (index * 20), 20, search);
    }

    @Override
    protected List<problemView> getList(){
        if(search != null && !search.equals("")){
            return super.getList();
        }
        int from = (nowPage-1) * 100 + 1000;
        from += (index-1)*20;
        int to = Math.min(from + 19,nowPage*100+999);
        User u = Main.loginUser();
        if(u!=null) {
            status_map = Main.status.submitResult(u.getUsername(),from,to);
        }else{
            status_map = new HashMap<>();
        }
        return Main.problems.getProblems(from,to,false,null);
    }

    @Override
    protected int getTotalNum() {
        return Main.problems.getProblemsNum(search);
    }

    @Override
    protected int getTotalPage(){
        if(search != null && !search.equals("")){
            return super.getTotalPage();
        }
        return Main.problems.getPageNum(100,false);
    }

    @Override
    protected JSONObject processingData(JSONObject jo){
        Integer status = status_map.get(jo.getInt("pid"));
        if(status == null) status = 0;
        else status += 1;
        jo.put("status",status);
        return jo;
    }
}
