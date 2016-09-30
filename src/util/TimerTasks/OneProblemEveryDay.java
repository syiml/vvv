package util.TimerTasks;

import action.addcontest;
import action.addproblem1;
import entity.Contest;
import entity.OJ.CodeVS.CodeVS;
import servise.ContestMain;
import servise.GvMain;
import util.Main;
import util.Submitter;
import util.Tool;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;

/**
 * Created by QAQ on 2016/9/22.
 */
public class OneProblemEveryDay extends MyTimer{
    private static int OJID = 6;

    @Override
    public void run() {
        Calendar ca = Calendar.getInstance();
        String contestName = "【每日一题】"+ca.get(Calendar.YEAR)+"年"+(ca.get(Calendar.MONTH)+1)+"月";
        int cid = GvMain.getOneProblemEveryDayCid();
        String gvName = GvMain.getOneProblemEveryDayName();
        if(cid == -1 || !contestName.equals(gvName)){
            cid = addContest(contestName);
        }
        if(cid != -1){
            int tryTimes = 0;
            int randomPid = -1;
            String title = null;
            while(true){
                tryTimes ++;
                if(tryTimes >= 30){
                    break;
                }
                randomPid = Tool.randNum(1000,5331);
                if(Main.problems.getProblemsByOjPid(OJID,randomPid+"").size()==0){
                    title = Submitter.ojs[OJID].getTitle(randomPid+"");
                    if(title != null){
                        break;
                    }
                }
            }
            if(tryTimes >= 30 || title == null || randomPid == -1){
                Tool.log("每日一题任务：随机题目失败！！！");
            }else{
                int pid = addProblem(title,randomPid);
                Contest c = ContestMain.getContest(cid);
                String pidstring = c.getProblems();
                if(pidstring.length() == 0){
                    pidstring += pid;
                }else{
                    pidstring += ","+pid;
                }
                ContestMain.ResetContestProblmes(cid,pidstring);
            }
        }
    }

    private int addProblem(String title,int randomPid){
        addproblem1 problem = new addproblem1();
        problem.setTitle(title);
        problem.setOjid(OJID+"");
        problem.setOjspid(randomPid+"");
        problem.setAuthor("");
        Main.addProblem(problem);
        List<Integer> pids = Main.problems.getProblemsByOjPid(OJID,randomPid+"");
        if(pids.size()==0) return -1;
        return pids.get(0);
    }
    private int addContest(String name){
        Calendar time = Calendar.getInstance();
        //每个月的一号创建一场比赛
        addcontest contest = new addcontest();
        contest.setName(name);

        time.set(Calendar.DAY_OF_MONTH,1);
        String month = (time.get(Calendar.MONTH) + 1) +"";
        if(month.length()==1) month = "0"+month;
        contest.setBegintime_d(time.get(Calendar.YEAR)+"-"+month+"-01");
        contest.setBegintime_m("0");
        contest.setBegintime_s("0");

        time.add(Calendar.MONTH,1);
        month = (time.get(Calendar.MONTH) + 1) +"";
        if(month.length()==1) month = "0"+month;
        contest.setEndtime_d(time.get(Calendar.YEAR)+"-"+month+"-01");
        contest.setEndtime_m("0");
        contest.setEndtime_s("0");

        contest.setType("0");
        contest.setKind("0");
        contest.setProblems("");
        contest.setInfo("");
        contest.setRank("3");
//        contest.setTraining_m1_s("1");//金奖个数
//        contest.setTraining_m2_s("3");//银奖个数
//        contest.setTraining_m3_s("6");//铜奖个数
//        contest.setTraining_m1_t("0");
//        contest.setTraining_m2_t("0");
//        contest.setTraining_m3_t("0");
        int cid = ContestMain.addContestReturnResult(contest);
        //////-------------//////
        GvMain.setOneProblemEveryDayCid(cid);
        GvMain.setOneProblemEveryDayName(name);
        return cid;
    }
    @Override
    public void getTimer() throws Exception {
        setEveryDay(22,14,30);
        new Timer().scheduleAtFixedRate(this, date, period);
    }
}
