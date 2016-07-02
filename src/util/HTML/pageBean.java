package util.HTML;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Syiml on 2015/11/2 0002.
 */
public abstract class pageBean {
    private String cl="table";
    TableHTML table = new TableHTML();
    List<String> Colname = new ArrayList<String>();

    protected String ERROR_CELL_TEXT="=ERROR=";
    public abstract String getTitle();

    /**
     * @return 当前页大小
     */
    public abstract int getPageSize();
    public abstract int getPageNum();//总页数
    public abstract int getNowPage();//当前页
    public abstract String getCellByHead(int i,String colname);//第i行的每列的内容

    public abstract String getLinkByPage(int page);
    public abstract String rightForm();

    public String getTableClass(){return cl;}
    public void setCl(String cl){this.cl=cl;}
    protected void addTableHead(String... ss){
        Collections.addAll(Colname,ss);
    }
    protected String getColname(String colname){
        return colname;
    }

    public String tableHTML(){
        for (String aColname : Colname) {
            table.addColname(getColname(aColname));
        }
        table.setClass(getTableClass());
        int PageSize=getPageSize();
        for(int i=0;i<PageSize;i++){
            List<String> row=new ArrayList<String>();
            for(int j=0;j<table.getColnameSize();j++){
                String colname=Colname.get(j);
                row.add(getCellByHead(i,colname));
            }
            table.addRow(row);
        }
        return table.HTML();
    }
    public void addClass(int r,int c,String cls){
        table.addCl(r,c,cls);
    }

    public String btn(String href,String text){
        return HTML.abtn("sm",href,text,"");
    }
    public String btn_disabled(String href,String text){
        return HTML.abtn("sm",href,text,"disabled");
    }
    public String btn_active(String href,String text){
        return HTML.abtn("sm",href,text,"btn-primary");
    }
    public String page(){
        int PageNum=getPageNum();
        int NowPage=getNowPage();
//        if(PageNum==1) return "";
        int k=4;//当前页往外扩展页数
        int l=NowPage;
        int r=NowPage;
        while(k>0&&(l>1||r<PageNum)){
            if(l>1){
                l--;
                k--;
            }
            if(r<PageNum){
                r++;
                k--;
            }
        }
        StringBuilder s =new StringBuilder("<div class='btn-toolbar' role='toolbar'>");
        s.append("<div class='btn-group' role='group'>");
        if(NowPage==1){
            s.append(btn_disabled(getLinkByPage(1),"<<"));
        }else{
            s.append(btn(getLinkByPage(1),"<<"));
        }
        if(NowPage==1){
            s.append(btn_disabled(getLinkByPage(1),"＜"));
        }else{
            s.append(btn(getLinkByPage(NowPage-1),"＜"));
        }
        if(l!=1){
            s.append(btn_disabled("","..."));
        }
        for(int i=l;i<=r;i++){
            if(i==NowPage){
                s.append(btn_active(getLinkByPage(i),i+""));
            }else{
                s.append(btn(getLinkByPage(i),i+""));
            }
        }
        if(r!=PageNum) {
            s.append(btn_disabled("", "..."));
        }
        if(NowPage==PageNum){
            s.append(btn_disabled(getLinkByPage(PageNum),"＞"));
        }else{
            s.append(btn(getLinkByPage(NowPage+1),"＞"));
        }
        if(NowPage==PageNum){
            s.append(btn_disabled(getLinkByPage(PageNum),">>"));
        }else{
            s.append(btn(getLinkByPage(PageNum),">>"));
        }
        s.append("</div></div>");
        return s.toString();
    }
    public String head(){
        return HTML.div("panel-body","style='padding:5px'",HTML.floatLeft(page())+HTML.floatRight(rightForm()));
    }
    public String foot(){
        return "";
//        if(getPageNum()==1) return "";
//        return HTML.div("panel-body","style='padding:5px'",page());
    }
    public String HTML(){
        return HTML.panelnobody(getTitle(),head()+tableHTML()+foot());
    }
    public static int getPageNum(int Num,int everyPageNum){
        if(Num==0) return 1;
        if(Num%everyPageNum==0){
            return Num/everyPageNum;
        }else return Num/everyPageNum+1;
    }
}
