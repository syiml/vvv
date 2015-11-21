package Tool;

import Main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Syiml on 2015/7/10 0010.
 */
public class SQL {
    String sql;
    Object[] args;
    PreparedStatement p=null;
    ResultSet rs=null;
    public SQL(String sql, Object... args){
        this.sql=sql;
        this.args=args;
    }
    public ResultSet query(){
        try {
            p = Main.conn.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                p.setObject(i+1,args[i]);
            }
            return rs=p.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet query(int from,int num){
        try {
            p=Main.conn.prepareStatement(sql+" LIMIT ?,?");
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
    public <K,V> Map<K,V> queryMap(){
        Map<K,V> ret=new TreeMap<K, V>();
        try {
            p=Main.conn.prepareStatement(sql);
            int i;
            for(i=0;i<args.length;i++){
                p.setObject(i+1,args[i]);
            }
            rs=p.executeQuery();
            while(rs.next()){
                ret.put((K)rs.getObject(1),(V)rs.getObject(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return ret;
    }
    public <T> List<T> queryList(){
        List<T> ret=new ArrayList<T>();
        try {
            p=Main.conn.prepareStatement(sql);
            int i;
            for(i=0;i<args.length;i++){
                p.setObject(i+1,args[i]);
            }
            rs=p.executeQuery();
            while(rs.next()){
                ret.add((T)rs.getObject(1));
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        } finally {
            close();
        }
        return ret;
    }
    public <T> Set<T> querySet(){
        Set<T> ret=new TreeSet<T>();
        try {
            p=Main.conn.prepareStatement(sql);
            int i;
            for(i=0;i<args.length;i++){
                p.setObject(i+1,args[i]);
            }
            rs=p.executeQuery();
            while(rs.next()){
                ret.add((T)rs.getObject(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return ret;
    }
    public <T> T queryObj(){
        rs=query();
        try {
            if(rs != null && rs.next()){
                return (T)rs.getObject(1);
            }else return null;
        } catch (SQLException e) {
//            Main.debug("queryNumError");
//            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }
    public int queryNum(){
        rs=query();
        try {
            if(rs != null && rs.next()){
                return rs.getInt(1);
            }else return 0;
        } catch (SQLException e) {
//            Main.debug("queryNumError");
//            e.printStackTrace();
        } finally {
            close();
        }
        return 0;
    }
    public String queryString(){
        rs=query();
        try {
            if(rs != null && rs.next()){
                return rs.getString(1);
            }else return "";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return "";
    }
    public int update(){
        p=null;
        try {
            p= Main.conn.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                p.setObject(i+1,args[i]);
            }
            return p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            close();
        }
    }
    public void close(){
        try {
            p.close();
        } catch (SQLException ignored) {
        }
        try {
            rs.close();
        } catch (SQLException ignored) {
        }
    }
}
