package util.SQL;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by QAQ on 2016/12/13.
 */
public class SQLUpdateThread implements Runnable{
    private BlockingDeque<SQL> sqls = new LinkedBlockingDeque<>();
    public void putSql(SQL sql){
        try {
            sqls.put(sql);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(true){
            try {
                SQL sql = sqls.take();
                sql.update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
