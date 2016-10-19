package util;

import net.sf.json.JSONObject;

/**
 * Created by QAQ on 2016/9/27.
 */
public class Config {
    public int problemShowNum;//每页显示的题目数量
    public int statusShowNum;//statu每页显示数量
    public int contestShowNum;//contest每页显示数量
    public int userShowNum;//user每页显示数量
    public int discussShowNum;//discuss的显示数量

    public int autoConnectionTimeMinute;//连接池自动清空的时间间隔

    public Boolean isDebug;
    public boolean delRun;//本地评测结束后是否删除编译的程序和用户的临时输出数据文件

    public String version;
    public String OJName;

    public long maxSampleFileSize;

    public String localJudgeWorkPath;
    public int LocalJudgeNumber;
    public String appPath = "/file/FJUTACM.apk";
    public String verifyPicPath = "/pic/verify/";

    public String sqlclass;
    public String sqlconnstring;
    public String sqlusername;
    public String sqlpassword;

    public Config readConfig(JSONObject GV)
    {
        problemShowNum=GV.getInt("problemShowNum");
        statusShowNum =GV.getInt("statusShowNum");
        contestShowNum=GV.getInt("contestShowNum");
        userShowNum=GV.getInt("userShowNum");
        discussShowNum=GV.getInt("discussShowNum");
        autoConnectionTimeMinute=GV.getInt("autoConnectionTimeMinute");
        isDebug=GV.getBoolean("debug");
        version=GV.getString("version");
        sqlclass=GV.getString("sqlclass");
        localJudgeWorkPath = GV.getString("localJudgeWorkPath");
        LocalJudgeNumber = GV.getInt("LocalJudgeNumber");
        maxSampleFileSize = GV.getLong("maxSampleFileSize");
        delRun = GV.getBoolean("delRun");
        sqlconnstring = GV.getString("sqlconnstring");
        sqlusername = GV.getString("sqlusername");
        sqlpassword = GV.getString("sqlpassword");
        OJName = GV.getString("OJName");
        return this;
    }
}