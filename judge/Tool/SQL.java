package Tool;

import Main.User.Permission;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Syiml on 2015/7/10 0010.
 */
public class SQL {
    String sql;
    Object[] args;
    public SQL(String sql, Object... args){
        this.sql=sql;
        this.args=args;
    }
    public ResultSet query(int from,int num){
        try {
            PreparedStatement p=Main.Main.conn.prepareStatement(sql+" LIMIT ?,?");
            int i;
            for(i=0;i<args.length;i++){
                p.setObject(i+1,args[i]);
            }
            p.setObject(i+1,from);
            p.setObject(i+2,num);
            return p.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int update(){
        PreparedStatement p=null;
        try {
            p= Main.Main.conn.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                p.setObject(i+1,args[i]);
            }
            return p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static ResultSet query(String sql,Object... args){
        PreparedStatement p= null;
        try {
            p = Main.Main.conn.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                p.setObject(i+1,args[i]);
            }
//            Main.Main.debug(p.toString());
            return p.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int update(String sql,Object... args){
        PreparedStatement p=null;
        try {
            p=Main.Main.conn.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                p.setObject(i+1,args[i]);
            }
            return p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }finally {
            try {
                if (p != null) {
                    p.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
