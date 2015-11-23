package Tool.SQL;

import Main.Main;
import Tool.Pair;

import java.sql.Connection;
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
    Connection conn;
    protected ResultSet rs=null;
    public SQL(String sql, Object... args){
        this.sql=sql;
        this.args=args;
        conn=Main.conns.getConn();
    }
    public ResultSet query(){
        try {
            p = conn.prepareStatement(sql);
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
            p=conn.prepareStatement(sql+" LIMIT ?,?");
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
    protected Object getObject(int i) throws SQLException {
        return rs.getObject(i);
    }
    public <K,V> Map<K,V> queryMap(){
        Map<K,V> ret=new TreeMap<K, V>();
        try {
            p=conn.prepareStatement(sql);
            int i;
            for(i=0;i<args.length;i++){
                p.setObject(i+1,args[i]);
            }
            rs=p.executeQuery();
            while(rs.next()){
                Object key=getObject(1);
                Object value=getObject(2);
                ret.put((K)key,(V)value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return ret;
    }
    public <K,V> List<Pair<K,V>> queryPairList(){
        List<Pair<K,V>> ret=new ArrayList<Pair<K,V>>();
        try {
            p=conn.prepareStatement(sql);
            int i;
            for(i=0;i<args.length;i++){
                p.setObject(i+1,args[i]);
            }
            rs=p.executeQuery();
            while(rs.next()){
                Object key=getObject(1);
                Object value=getObject(2);
                ret.add(new Pair<K, V>((K)key,(V)value));
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
            p=conn.prepareStatement(sql);
            int i;
            for(i=0;i<args.length;i++){
                p.setObject(i+1,args[i]);
            }
            rs=p.executeQuery();
            while(rs.next()){
                ret.add((T)getObject(1));
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
            p=conn.prepareStatement(sql);
            int i;
            for(i=0;i<args.length;i++){
                p.setObject(i+1,args[i]);
            }
            rs=p.executeQuery();
            while(rs.next()){
                ret.add((T)getObject(1));
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
            p= conn.prepareStatement(sql);
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
        pClose();
        cClose();
        Main.conns.putCondition(conn);
    }
    private void cClose(){
        try {
            if(rs!=null)rs.close();
        } catch (SQLException ignored) {
        }
    }
    private void pClose(){
        try {
            if(p!=null) p.close();
        } catch (SQLException ignored) {
        }
    }
}
