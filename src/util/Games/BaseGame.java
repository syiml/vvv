package util.Games;

import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by QAQ on 2016/9/30.
 */
public abstract class BaseGame implements Runnable {
    /**
     * 返回重现情况
     * @return 重现情况的json对象
     */
    public abstract JSONObject getRecord();

    /**
     * 玩家列表
     */
    protected List<IGamePlayer> players;

    /**
     * 观战人列表
     */
    protected List<IGamePlayer> observers;

    public abstract void run();

    public abstract int gameEnd(int status);
}
