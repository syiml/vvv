package servise;

import entity.User;
import entity.Discuss;
import dao.DiscussSQL;
import entity.Contest;
import entity.RegisterUser;
import dao.MessageSQL;
import entity.DiscussReply;
import entity.Message;
import util.HTML.HTML;
import util.Main;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class MessageMain {
    /**
     * 对discuss的消息中的@信息发送消息，多次@同一个人只发送一条
     * @param text 发送文本
     * @return 返回数量
     */
    public static int discussAt(int did,String text,boolean isReply){
        Discuss d= DiscussSQL.getDiscuss(did);
        if(isReply && d != null && d.isReplyHidden()) return 0;//discuss是回复默认隐藏则不提醒
        Pattern p = Pattern.compile("(@[_0-9a-zA-Z]+)");
        Matcher m = p.matcher(text);
        Set<String> users=new HashSet<String>();
        int ret=0;
        while(m.find()){
            String group=m.group();
            String name=group.substring(1,group.length());
            if(users.contains(name)) break;//重复@只发送一条
            User u= Main.users.getUser(name);
            if(u!=null){
                users.add(name);
                if(isReply){
                    addMessageDiscussReplyAt(d,name);
                }
                else addMessageDiscussAt(d,name);
                ret++;
            }
        }
        return ret;
    }
    public static int addMessageDiscussAt(Discuss d,String user){
        Message m=new Message();
        m.setUser(user);
        m.setStatu(0);
        m.setTitle("[有人@你]" + d.getUsername() + "在帖子【" + d.getTitle() + "】中提到了你");
        m.setText(d.getUsername() + "在帖子【" + d.getTitle() + "】中提到了你。" + HTML.a("Discuss.jsp?id=" + d.getId(), "立即查看"));
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.insert(m);
    }
    public static int addMessageDiscussReplyAt(Discuss d,String user){
        Message m=new Message();
        m.setUser(user);
        m.setStatu(0);
        m.setTitle("[有人@你]"+d.getUsername()+"在帖子【"+d.getTitle()+"】的回复中提到了你");
        m.setText(d.getUsername()+"在帖子【"+d.getTitle()+"】的回复中提到了你。"+ HTML.a("Discuss.jsp?id="+d.getId(),"立即查看"));
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.insert(m);
    }
    public static int addMessageDisscussReply(int cid, String append, int did, String text){
        Discuss d=DiscussSQL.getDiscuss(did);
        User u=Main.users.getUser(append);
        Message m=new Message();
        m.setUser(d.getUsername());
        if(m.getUser().equals(append)) return 0;
        m.setStatu(0);
        if(cid==-1){
            m.setTitle("你的帖子【"+d.getTitle()+"】有新回复");
        }else{
            Contest c=Main.contests.getContest(cid);
            m.setTitle("你在比赛【"+c.getName()+"】中的提问【"+d.getTitle()+"】有新回复");
        }
        String url;
        if(cid==-1) url="Discuss.jsp?id="+did;
        else url="Contest.jsp?cid="+cid+"#D"+did;
        m.setText(u.getUsernameHTML() + "(" + u.getNick() + ")回复了你的帖子【" + d.getTitle() + "】：</br>" + text + "</br>" + HTML.a(url, "查看帖子"));
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.insert(m);
    }
    public static int addMessageDiscussReplyAdmin(int did, int rid, String text){//管理员回复
        DiscussReply dr=DiscussSQL.getDiscussReply(did,rid);
        Discuss d=DiscussSQL.getDiscuss(did);
        Message m=new Message();
        m.setUser(dr.getUsername());
        m.setStatu(0);
        m.setTitle("管理员回复了你在帖子【" + d.getTitle() + "】的回复");
        m.setText("管理员回复了你在帖子【" + d.getTitle() + "】的回复：</br>" + text + "</br>" + HTML.a("Discuss.jsp?id=" + did, "查看帖子"));
        m.setDeadline(new Timestamp(System.currentTimeMillis() + 86400000L * 30L));//保留30天
        return MessageSQL.insert(m);
    }
    public static int addMessageRatingChange(int cid, String user, int prating, int rating){
        Contest c=Main.contests.getContest(cid);
        Message m=new Message();
        m.setUser(user);
        m.setStatu(0);
        m.setTitle("Rating值有新变化");
        if(prating==-100000){
            m.setText("由于参加了比赛【" + c.getName() + "】,您的rating值变为" + User.ratingToHTML(rating) + "。");
        }else{
            m.setText("由于参加了比赛【" + c.getName() + "】,您的rating值由" + User.ratingToHTML(prating) + "变为" + User.ratingToHTML(rating) + "。");

        }
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.insert(m);
    }
    public static int addMessageRegisterContest(String user,int cid,int statu){
        Contest c=Main.contests.getContest(cid);
        Message m=new Message();
        m.setUser(user);
        m.setStatu(0);
        m.setTitle("你注册的比赛【" + c.getName() + "】已经审核完成了");
        m.setText("你注册的比赛【" + c.getName() + "】已经审核完成了：</br>审核结果：" + RegisterUser.statuToHTML(statu) + "<br>" + HTML.a("User.jsp?cid=" + cid, "点击查看"));
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.insert(m);
    }
    public static int addMessageWelcome(User u){
        Message m=new Message();
        m.setUser(u.getUsername());
        m.setStatu(0);
        m.setTitle("欢迎新用户");
        m.setText(u.getNick() + "，欢迎您入驻T^T Online Judge，当前OJ是测试版，如有发现BUG，可以提交到【" + HTML.a("Discuss.jsp?id=6", "BUG和建议收集") + "】贴<br>"+"更多新用户帮助，可以查看【"+HTML.a("Discuss.jsp?id=4","FAQ")+"】也可以直接在底下回复提问。");
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.insert(m);
    }
    public static int addMessageBlockOpen(String user,String title,int num){
        Message m=new Message();
        m.setUser(user);
        m.setStatu(0);
        m.setTitle("模块【" + title + "】开启，获得" + num + "ACB奖励");
        m.setText("由于模块【" + title + "】开启，您获得了" + num + "ACB奖励，请再接再厉！");
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.insert(m);
    }
    public static int addMessageAwardACB(String u,int num,String text){
        Message m=new Message();
        m.setUser(u);
        m.setStatu(0);
        m.setTitle("您收到了" + num + "ACB奖励");
        m.setText("您收到了" + num + "ACB奖励：<br>备注信息：" + text);
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.insert(m);
    }
}
