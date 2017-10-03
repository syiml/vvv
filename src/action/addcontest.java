package action;

import entity.*;
import servise.ContestMain;
import util.Main;
import util.MainResult;
import util.Tool;

/**
 * Created by Syiml on 2015/6/23 0023.
 */
public class addcontest extends BaseAction{
    public String name;
    public int cid;//edit
    public String begintime_d;
    public String begintime_s;
    public String begintime_m;
    public String endtime_d;
    public String endtime_s;
    public String endtime_m;
    public int type;
    public int kind;
    public String registerstarttime_d;
    public String registerstarttime_s;
    public String registerstarttime_m;
    public String registerendtime_d;
    public String registerendtime_s;
    public String registerendtime_m;
    public String pass;
    public String problems;
    public int rank;
    public String icpc_penalty;
    public String icpc_m1_t;
    public String icpc_m1_s;
    public String icpc_m2_t;
    public String icpc_m2_s;
    public String icpc_m3_t;
    public String icpc_m3_s;
    public String info;
    public String computerating;
    public String problemCanPutTag;//内部题目是否可以直接贴标签
    public String statusReadOut;   //计算排名时，是否把全局的提交也计算入内
    public String registerShowComplete;//注册是否需要完整的个人信息

    private int hideRankMinute = 0;//封榜时间
    private String isHideOthersStatus;//隐藏其他人的提交
    private String isHideOthersStatusInfo;//隐藏其他人提交的时间、空间、代码长度

    public String shortcode_m1_t;
    public String shortcode_m1_s;
    public String shortcode_m2_t;
    public String shortcode_m2_s;
    public String shortcode_m3_t;
    public String shortcode_m3_s;
    public String shortcode_chengfa;
    public String training_m1_t;
    public String training_m1_s;
    public String training_m2_t;
    public String training_m2_s;
    public String training_m3_t;
    public String training_m3_s;

    public static boolean validate(String problems){
        User user = Main.loginUser();
        if(user == null) return false;
        String[] pids = problems.split(",");
        for (int i = 0; i < pids.length; i++) {
            int tpid = Integer.parseInt(pids[i]);
            Problem p = Main.problems.getProblem(tpid);
            if (p == null) {
                //this.setPrompt("题目" + tpid + "不存在");
                return false;
            }
            if (p.visiable == 0) {
                if (!(user.getPermission().havePermissions(PermissionType.partAddProblem) && p.getOwner().equals(user.getUsername()))) {
                    //this.setPrompt("题目" + tpid + "不存在");
                    return false;
                }
            }
        }
        return true;
    }
    public String addDIY() {
        User user = Main.loginUser();
        if (user == null) {
            this.setPrompt("未登录");
            return ERROR;
        }
        kind = Contest.KIND_DIY;
        //statusReadOut = null;
        computerating = null;
        registerShowComplete = null;
        try {
            String[] pids = problems.split(",");
            for (int i = 0; i < pids.length; i++) {
                int tpid = Integer.parseInt(pids[i]);
                Problem p = Main.problems.getProblem(tpid);
                if (p == null) {
                    this.setPrompt("题目" + tpid + "不存在");
                    return ERROR;
                }
                if (p.visiable == 0) {
                    if (!(user.getPermission().havePermissions(PermissionType.partAddProblem) && p.getOwner().equals(user.getUsername()))) {
                        this.setPrompt("题目" + tpid + "不存在");
                        return ERROR;
                    }
                }
            }
        } catch (NumberFormatException e) {
            this.setPrompt("题目列表格式错误");
            return ERROR;
        }
        if(cid == 0) ContestMain.addContest(this);
        else{
            Contest c = ContestMain.getContest(cid);
            if(c == null){
                this.setPrompt("比赛"+cid+"不存在");
                return ERROR;
            }
            if(c.getKind()!=Contest.KIND_DIY){
                this.setPrompt("非法编辑");
                return ERROR;
            }
            if(c.getCreateuser().equals(user.getUsername()))
                ContestMain.editContest(this);
            else {
                this.setPrompt("没有权限编辑不是自己创建的比赛");
                return ERROR;
            }
        }

        return SUCCESS;
    }

    public String add(){
        if(!Main.loginUserPermission().getAddContest()) return ERROR;
        ContestMain.addContest(this);
        return SUCCESS;
    }
    public String edit(){
        if(!Main.loginUserPermission().getAddContest()) return ERROR;
        ContestMain.editContest(this);
        return SUCCESS;
    }
    public String problemPublic(){
        Permission p=Main.loginUserPermission();
        if(p.getAddContest()&&p.getAddProblem()){
            ContestMain.contestPorblemPublc(cid);
        }
        return SUCCESS;
    }

    public String getInfo() {
        return info==null?"":info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getComputerating() {
        return computerating;
    }

    public void setComputerating(String computerating) {
        this.computerating = computerating;
    }

    public String getProblemCanPutTag() {
        return problemCanPutTag;
    }

    public void setProblemCanPutTag(String problemCanPutTag) {
        this.problemCanPutTag = problemCanPutTag;
    }

    public String getStatusReadOut() {
        return statusReadOut;
    }

    public void setStatusReadOut(String statusReadOut) {
        this.statusReadOut = statusReadOut;
    }

    public String getRegisterShowComplete() {
        return registerShowComplete;
    }

    public void setRegisterShowComplete(String registerShowComplete) {
        this.registerShowComplete = registerShowComplete;
    }

    public String getShortcode_m1_t() {
        return shortcode_m1_t;
    }

    public void setShortcode_m1_t(String shortcode_m1_t) {
        this.shortcode_m1_t = shortcode_m1_t;
    }

    public String getShortcode_m1_s() {
        return shortcode_m1_s;
    }

    public void setShortcode_m1_s(String shortcode_m1_s) {
        this.shortcode_m1_s = shortcode_m1_s;
    }

    public String getShortcode_m2_t() {
        return shortcode_m2_t;
    }

    public void setShortcode_m2_t(String shortcode_m2_t) {
        this.shortcode_m2_t = shortcode_m2_t;
    }

    public String getShortcode_m2_s() {
        return shortcode_m2_s;
    }

    public void setShortcode_m2_s(String shortcode_m2_s) {
        this.shortcode_m2_s = shortcode_m2_s;
    }

    public String getShortcode_m3_t() {
        return shortcode_m3_t;
    }

    public void setShortcode_m3_t(String shortcode_m3_t) {
        this.shortcode_m3_t = shortcode_m3_t;
    }

    public String getShortcode_m3_s() {
        return shortcode_m3_s;
    }

    public void setShortcode_m3_s(String shortcode_m3_s) {
        this.shortcode_m3_s = shortcode_m3_s;
    }

    public String getShortcode_chengfa() {
        return shortcode_chengfa;
    }

    public void setShortcode_chengfa(String shortcode_chengfa) {
        this.shortcode_chengfa = shortcode_chengfa;
    }

    public String getTraining_m1_t() {
        return training_m1_t;
    }

    public void setTraining_m1_t(String training_m1_t) {
        this.training_m1_t = training_m1_t;
    }

    public String getTraining_m1_s() {
        return training_m1_s;
    }

    public void setTraining_m1_s(String training_m1_s) {
        this.training_m1_s = training_m1_s;
    }

    public String getTraining_m2_t() {
        return training_m2_t;
    }

    public void setTraining_m2_t(String training_m2_t) {
        this.training_m2_t = training_m2_t;
    }

    public String getTraining_m2_s() {
        return training_m2_s;
    }

    public void setTraining_m2_s(String training_m2_s) {
        this.training_m2_s = training_m2_s;
    }

    public String getTraining_m3_t() {
        return training_m3_t;
    }

    public void setTraining_m3_t(String training_m3_t) {
        this.training_m3_t = training_m3_t;
    }

    public String getTraining_m3_s() {
        return training_m3_s;
    }

    public void setTraining_m3_s(String training_m3_s) {
        this.training_m3_s = training_m3_s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBegintime_d() {
        return begintime_d;
    }

    public void setBegintime_d(String begintime_d) {
        this.begintime_d = begintime_d;
    }

    public String getBegintime_s() {
        return begintime_s;
    }

    public void setBegintime_s(String begintime_s) {
        this.begintime_s = begintime_s;
    }

    public String getBegintime_m() {
        return begintime_m;
    }

    public void setBegintime_m(String begintime_m) {
        this.begintime_m = begintime_m;
    }

    public String getEndtime_d() {
        return endtime_d;
    }

    public void setEndtime_d(String endtime_d) {
        this.endtime_d = endtime_d;
    }

    public String getEndtime_s() {
        return endtime_s;
    }

    public void setEndtime_s(String endtime_s) {
        this.endtime_s = endtime_s;
    }

    public String getEndtime_m() {
        return endtime_m;
    }

    public void setEndtime_m(String endtime_m) {
        this.endtime_m = endtime_m;
    }

    public String getRegisterstarttime_d() {
        return registerstarttime_d;
    }

    public void setRegisterstarttime_d(String registerstarttime_d) {
        this.registerstarttime_d = registerstarttime_d;
    }

    public String getRegisterstarttime_s() {
        return registerstarttime_s;
    }

    public void setRegisterstarttime_s(String registerstarttime_s) {
        this.registerstarttime_s = registerstarttime_s;
    }

    public String getRegisterstarttime_m() {
        return registerstarttime_m;
    }

    public void setRegisterstarttime_m(String registerstarttime_m) {
        this.registerstarttime_m = registerstarttime_m;
    }

    public String getRegisterendtime_d() {
        return registerendtime_d;
    }

    public void setRegisterendtime_d(String registerendtime_d) {
        this.registerendtime_d = registerendtime_d;
    }

    public String getRegisterendtime_s() {
        return registerendtime_s;
    }

    public void setRegisterendtime_s(String registerendtime_s) {
        this.registerendtime_s = registerendtime_s;
    }

    public String getRegisterendtime_m() {
        return registerendtime_m;
    }

    public void setRegisterendtime_m(String registerendtime_m) {
        this.registerendtime_m = registerendtime_m;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getIcpc_penalty() {
        return icpc_penalty;
    }

    public void setIcpc_penalty(String icpc_penalty) {
        this.icpc_penalty = icpc_penalty;
    }

    public String getIcpc_m1_t() {
        return icpc_m1_t;
    }

    public void setIcpc_m1_t(String icpc_m1_t) {
        this.icpc_m1_t = icpc_m1_t;
    }

    public String getIcpc_m1_s() {
        return icpc_m1_s;
    }

    public void setIcpc_m1_s(String icpc_m1_s) {
        this.icpc_m1_s = icpc_m1_s;
    }

    public String getIcpc_m2_t() {
        return icpc_m2_t;
    }

    public void setIcpc_m2_t(String icpc_m2_t) {
        this.icpc_m2_t = icpc_m2_t;
    }

    public String getIcpc_m2_s() {
        return icpc_m2_s;
    }

    public void setIcpc_m2_s(String icpc_m2_s) {
        this.icpc_m2_s = icpc_m2_s;
    }

    public String getIcpc_m3_t() {
        return icpc_m3_t;
    }

    public void setIcpc_m3_t(String icpc_m3_t) {
        this.icpc_m3_t = icpc_m3_t;
    }

    public String getIcpc_m3_s() {
        return icpc_m3_s;
    }

    public void setIcpc_m3_s(String icpc_m3_s) {
        this.icpc_m3_s = icpc_m3_s;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getHideRankMinute() {
        return hideRankMinute;
    }

    public void setHideRankMinute(int hideRankMinute) {
        this.hideRankMinute = hideRankMinute;
    }

    public String getIsHideOthersStatus() {
        return isHideOthersStatus;
    }

    public void setIsHideOthersStatus(String isHideOthersStatus) {
        this.isHideOthersStatus = isHideOthersStatus;
    }

    public String getIsHideOthersStatusInfo() {
        return isHideOthersStatusInfo;
    }

    public void setIsHideOthersStatusInfo(String isHideOthersStatusInfo) {
        this.isHideOthersStatusInfo = isHideOthersStatusInfo;
    }
}
