package action.App;

import action.BaseAction;
import net.sf.json.JSONObject;
import org.jsoup.Connection;
import servise.GvMain;
import util.Config;
import util.Main;
import util.MainResult;

import java.io.File;

/**
 * Created by QAQ on 2016/10/10.
 */
public class ActionAppUpdate extends BaseAction {
    private String versionName;
    private int versionCode;
    private File app;
    private String update;

    public String update(){
        if(app==null){
            setPrompt("没有选择APK文件");
            return INPUT;
        }
        MainResult mr = Main.appUpdate(this);
        setPrompt(mr.getPrompt());
        return SUCCESS;
    }

    public String getVersionInfo(){
        JSONObject jo = new JSONObject();
        jo.put("versionCode", GvMain.getAppVersionCode());
        jo.put("versionName", GvMain.getAppVersionName());
        jo.put("versionInfo", GvMain.getAppUpdate());
        jo.put("url", Main.config.appPath);
        out.print(jo);
        return NONE;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public File getApp() {
        return app;
    }

    public void setApp(File app) {
        this.app = app;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
