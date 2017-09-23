package util.Config;

import net.sf.json.JSONObject;

/**
 * Created by QAQ on 2017/9/16.
 */
public abstract class BaseConfig {
    public abstract void readConfig(JSONObject jo);
    public abstract String getFileName();
}
