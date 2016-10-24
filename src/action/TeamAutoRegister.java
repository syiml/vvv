package action;

import com.sun.jmx.snmp.SnmpUnknownAccContrModelException;
import org.junit.internal.runners.statements.Fail;
import servise.ContestMain;
import util.MainResult;

/**
 * Created by Administrator on 2016/10/24 0024.
 */
public class TeamAutoRegister extends BaseAction {
    int cid;

    public String autoRegister(){
        MainResult mr = ContestMain.teamAutoRegister(cid);
        setPrompt(mr.getPrompt());
        if(mr!=MainResult.SUCCESS) return ERROR;
        return SUCCESS;
    }



    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
