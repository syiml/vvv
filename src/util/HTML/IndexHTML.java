package util.HTML;

import servise.WeekRankCount.WeekRankCountHTML;
import util.Main;
import entity.User;
import util.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/7/3 0003.
 */
public class IndexHTML {
    public static String rank1(){
        List<User> list= Main.users.getUsers(0,10,"","",false);
        TableHTML table= new TableHTML();
        table.setClass("table table-condensed table-hover");
        table.addColname("#");
        table.addColname("user");
        table.addColname("rating");
        for(int i=0;i<list.size();i++){
            User u=list.get(i);
            List<String> row=new ArrayList<String>();
            row.add(u.getRank()+"");
            //if(i<=2){
                //row.add(HTML.headImg(u.getUsername(),0)+u.getUsernameHTML());
            //}else{
                row.add(u.getUsernameHTML());
            //}
            row.add(User.ratingToHTML(u.getShowRating()));
            table.addRow(row);
        }
        return HTML.panelnobody("Rating  Top10" +HTML.floatRight(HTML.a("User.jsp", "All")), table.HTML());
    }
    public static String rank2(){
        List<User> list= Main.users.getRichTop10();
        TableHTML table= new TableHTML();
        table.setClass("table table-condensed table-hover");
        table.addColname("#");
        table.addColname("user");
        table.addColname("ACB");
        int k=1;
        for(User u:list){
            List<String> row=new ArrayList<String>();
            row.add(k + "");
            k++;
            row.add(u.getUsernameHTML());
            row.add(u.getAcb()+"");
            table.addRow(row);
        }
        return HTML.panelnobody("ACB富豪榜 Top10"+HTML.floatRight(HTML.a("User.jsp?search=&order=acb&desc=1", "All")), table.HTML());
    }
    public static String rank3(){
        List<User> list= Main.users.getAcnumTop10();
        TableHTML table= new TableHTML();
        table.setClass("table table-condensed table-hover");
        table.addColname("#");
        table.addColname("user");
        table.addColname("AC");
        int k=1;
        for(User u:list){
            List<String> row=new ArrayList<String>();
            row.add(k++ +"");
            row.add(u.getUsernameHTML());
            row.add(u.getAcnum()+"");
            table.addRow(row);
        }
        return HTML.panelnobody("AC榜 Top10" +HTML.floatRight(HTML.a("User.jsp?search=&order=acnum&desc=1", "All")), table.HTML());
    }
    public static String HTML(){
        String l= DiscussHTML.IndexDiscuss();
        String r=rank1()+rank2()+rank3()+new WeekRankCountHTML(1).IndexHTML();
        return HTML.row(HTML.col(9,"xs",l)+HTML.col(3,"xs",r));
    }
}
