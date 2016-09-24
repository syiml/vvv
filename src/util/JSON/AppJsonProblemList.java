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
    public AppJsonProblemList(int nowPage,int index,String search){
        super(nowPage,30);
        this.search = search;
        this.index = index;
    }

    @Override
    List<problemView> getList(int from, int num) {
        return Main.problems.getProblems(from+(index*20), 20, search);
    }

    @Override
    int getTotalNum() {
        return Main.problems.getProblemsNum(search);
    }
}
