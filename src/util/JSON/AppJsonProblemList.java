package util.JSON;

import util.HTML.problemListHTML.problemView;
import util.Main;

import java.util.List;


/**
 * Created by QAQ on 2016/9/21.
 */
public class AppJsonProblemList extends BaseJsonPageBean<problemView> {
    private String search;
    private int index;//第几个20条

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
        return Main.problems.getProblems(from,to,false);
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
}
