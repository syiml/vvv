package servise;

import entity.*;
import entity.Discuss.ReplyReply;
import entity.Discuss.Discuss;
import dao.Discuss.DiscussSQL;
import dao.MessageSQL;
import entity.Discuss.DiscussReply;
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
        return MessageSQL.save(m);
    }
    public static int addMessageDiscussReplyAt(Discuss d,String user){
        Message m=new Message();
        m.setUser(user);
        m.setStatu(0);
        m.setTitle("[有人@你]"+d.getUsername()+"在帖子【"+d.getTitle()+"】的回复中提到了你");
        m.setText(d.getUsername()+"在帖子【"+d.getTitle()+"】的回复中提到了你。"+ HTML.a("Discuss.jsp?id="+d.getId(),"立即查看"));
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.save(m);
    }
    public static int addMessageDisscussReply(DiscussReply dr){
        Discuss d=DiscussSQL.getDiscuss(dr.getDid());
        User u=Main.users.getUser(dr.getUsername());
        Message m=new Message();
        m.setUser(d.getUsername());
        if(m.getUser().equals(dr.getUsername())) return 0;
        m.setStatu(0);
        if(d.getCid()==-1){
            m.setTitle("你的帖子【"+d.getTitle()+"】有新回复");
        }else{
            Contest c=ContestMain.getContest(d.getCid());
            m.setTitle("你在比赛【"+c.getName()+"】中的提问【"+d.getTitle()+"】有新回复");
        }
        String url;
        if(d.getCid()==-1) url="Discuss.jsp?id="+dr.getDid()+"&page="+(dr.getRid()-1)/DiscussMain.replyReplyShowNum;
        else url="Contest.jsp?cid="+d.getCid()+"#D"+dr.getDid();
        m.setText(u.getUsernameHTML() + "(" + u.getNick() + ")回复了你的帖子【" + d.getTitle() + "】：</br>"
                + HTML.HTMLtoString(dr.getText()) + "</br>" + HTML.a(url, "查看帖子"));
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.save(m);
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
        return MessageSQL.save(m);
    }
    public static int addMessageRatingChange(int cid, String user, int prating, int rating){
        Contest c=ContestMain.getContest(cid);
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
        return MessageSQL.save(m);
    }
    public static int addMessageRegisterContest(String user,int cid,int statu){
        Contest c=ContestMain.getContest(cid);
        Message m=new Message();
        m.setUser(user);
        m.setStatu(0);
        m.setTitle("你注册的比赛【" + c.getName() + "】的状态有变化");
        m.setText("你注册的比赛【" + c.getName() + "】的状态有变化：</br>变化为：" + RegisterUser.statuToHTML(statu) + "<br>" + HTML.a("User.jsp?cid=" + cid, "点击查看"));
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.save(m);
    }
    public static int addMessageWelcome(User u){
        Message m=new Message();
        m.setUser(u.getUsername());
        m.setStatu(0);
        m.setTitle("欢迎新用户");
        m.setText(u.getNick() + "，欢迎您入驻T^T Online Judge，当前OJ是测试版，如有发现BUG，可以提交到【" + HTML.a("Discuss.jsp?id=6", "BUG和建议收集") + "】贴<br>"+"更多新用户帮助，可以查看【"+HTML.a("Discuss.jsp?id=4","FAQ")+"】也可以直接在底下回复提问。");
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.save(m);
    }
    public static int addMessageBlockOpen(String user,String title,int num){
        Message m=new Message();
        m.setUser(user);
        m.setStatu(0);
        m.setTitle("模块【" + title + "】开启，获得" + num + "ACB奖励");
        m.setText("由于模块【" + title + "】开启，您获得了" + num + "ACB奖励，请再接再厉！");
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.save(m);
    }
    public static int addMessageAwardACB(String u,int num,String text){
        Message m=new Message();
        m.setUser(u);
        m.setStatu(0);
        m.setTitle("您收到了" + num + "ACB奖励");
        m.setText("您收到了" + num + "ACB奖励：<br>备注信息：" + text);
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.save(m);
    }
    public static int addMessageSubACB(String u, int num, String text){
        Message m=new Message();
        m.setUser(u);
        m.setStatu(0);
        m.setTitle("您被扣去了" + num + "ACB");
        m.setText("您被扣去了" + num + "ACB<br>备注信息：" + text);
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.save(m);
    }

    public static int addMessageReplyReply(ReplyReply rr){
        Discuss d = DiscussSQL.getDiscuss(rr.getDid());
        DiscussReply dr = DiscussSQL.getDiscussReply(rr.getDid(),rr.getRid());
        ReplyReply replyRr = null;
        if(rr.getReplyRid() != -1){
            replyRr = DiscussSQL.getReplyReply(rr.getDid(),rr.getRid(),rr.getReplyRid());
        }
        int cid = d.getCid();
        Message m=new Message();
        m.setStatu(0);
        if(replyRr == null){
            m.setUser(dr.getUsername());
        }else{
            m.setUser(replyRr.getUsername());
        }
        if(m.getUser().equals(rr.getUsername())) return 0;
        m.setTitle("你在【"+d.getTitle()+"】中有新回复");
        String url;
        if(cid==-1) url="Discuss.jsp?id="+dr.getDid()+"&page="+(dr.getRid()-1)/Main.config.discussShowNum;
        else url="Contest.jsp?cid="+cid+"#D"+d.getId();
        User u = Main.users.getUser(rr.getUsername());
        m.setText(u.getUsernameHTML() + "(" + u.getNick() + ")在帖子【" + d.getTitle() + "】中回复了你：</br>" + rr.getText() + "</br>" + HTML.a(url, "查看帖子"));
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.save(m);
    }
    public static int addMessageVerify(UserVerifyInfo userVerifyInfo){
        Message m = new Message();
        m.setTitle("你提交的认证已经处理完毕");
        m.setText("你提交的认证已经处理完毕<br>"+
                "认证内容："+ userVerifyInfo.getVerifyTypeText()+"<br>"+
                "处理结果："
                +(userVerifyInfo.result==UserVerifyInfo.RESULT_ACCEPTED?HTML.text("通过","green"):(
                        HTML.text("未通过","red")+"<br>备注信息："+userVerifyInfo.reason
                ))+
                "<br>"+HTML.a("Verify.action?id="+userVerifyInfo.id,"点击查看")
        );
        m.setUser(userVerifyInfo.username);
        m.setDeadline(new Timestamp(86400000L * 30 + System.currentTimeMillis()));//保留30天
        return MessageSQL.save(m);
    }
}
