package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 无乐园 on 2017/9/11.
 */
public class AiBattleInfo implements IBeanResultSetCreate {
    int id,win;
    String white,black;
    String processes;

    public AiBattleInfo(){}

    @Override
    public void init(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        black = rs.getString("black");
        white = rs.getString("white");
        win = rs.getInt("win");
        processes = rs.getString("processes");
    }

    public int getId() {
        return id;
    }

    public int getWin() {
        return win;
    }

    public String getWhite() {
        return white;
    }

    public String getBlack() {
        return black;
    }

    public String getProcesses() {
        return processes;
    }
}
