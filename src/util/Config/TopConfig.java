package util.Config;

import net.sf.json.JSONObject;

/**
 * 从src/util/GlobalVariables/GlobalVariables.json读取的配置内容
 * Created by QAQ on 2016/9/27.
 */
public class TopConfig extends BaseConfig{
    public int problemShowNum;//每页显示的题目数量
    public int statusShowNum;//statu每页显示数量
    public int contestShowNum;//contest每页显示数量
    public int userShowNum;//user每页显示数量
    public int discussListShowNum;//discuss列表显示数量
    public int discussReplyShowNum;//discuss内每页回复的显示数量

    public int autoConnectionTimeMinute;//连接池自动清空的时间间隔

    public Boolean isDebug;
    public boolean delRun;//本地评测结束后是否删除编译的程序和用户的临时输出数据文件

    public String version;
    public String OJName;

    public long maxSampleFileSize;

    public String svnPath;//用于同步oj和judge_system的数据
    public String localJudgeWorkPath;
    public int LocalJudgeNumber;
    public String appPath = "/file/FJUTACM.apk";
    public String verifyPicPath = "/pic/verify/";

    public String sqlclass;
    public String sqlconnstring;
    public String sqlusername;
    public String sqlpassword;


    @Override
    public void readConfig(JSONObject GV)
    {
        problemShowNum=GV.getInt("problemShowNum");
        statusShowNum =GV.getInt("statusShowNum");
        contestShowNum=GV.getInt("contestShowNum");
        userShowNum=GV.getInt("userShowNum");
        discussListShowNum=GV.getInt("discussListShowNum");
        discussReplyShowNum=GV.getInt("discussReplyShowNum");
        autoConnectionTimeMinute=GV.getInt("autoConnectionTimeMinute");
        isDebug=GV.getBoolean("debug");
        version=GV.getString("version");
        sqlclass=GV.getString("sqlclass");
        svnPath = GV.getString("svnPath");
        localJudgeWorkPath = GV.getString("localJudgeWorkPath");
        LocalJudgeNumber = GV.getInt("LocalJudgeNumber");
        maxSampleFileSize = GV.getLong("maxSampleFileSize");
        delRun = GV.getBoolean("delRun");
        sqlconnstring = GV.getString("sqlconnstring");
        sqlusername = GV.getString("sqlusername");
        sqlpassword = GV.getString("sqlpassword");
        OJName = GV.getString("OJName");
    }

    @Override
    public String getFileName() {
        return "GlobalVariables.json";
    }
}