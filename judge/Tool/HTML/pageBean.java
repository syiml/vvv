package Tool.HTML;

import Tool.HTML.TableHTML.TableHTML;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Syiml on 2015/11/2 0002.
 */
public abstract class pageBean {
    private String cl="table";
    TableHTML table = new TableHTML();

    public abstract String getTitle();
    public abstract int getPageSize();//当前页大小
    public abstract int getPageNum();//总页数
    public abstract int getNowPage();//当前页
    public abstract String getCellByHead(int i,String colname);//第i行的每列的内容

    public abstract String getLinkByPage(int page);
    public abstract String rightForm();

    public String getTableClass(){return cl;}
    public void setCl(String cl){this.cl=cl;}
    protected void setTableHead(List<String> tableHead) {
        table.setColname(tableHead);
    }
    protected void addTableHead(String... ss){
        table.addColname(ss);
    }

    public String tableHTML(){
        table.setClass(getTableClass());
        int PageSize=getPageSize();
        for(int i=0;i<PageSize;i++){
            List<String> row=new ArrayList<String>();
            for(int j=0;j<table.getColnameSize();j++){
                String colname=table.getColname(j);
                row.add(getCellByHead(i,colname));
            }
            table.addRow(row);
        }
        return table.HTML();
    }
    public void addClass(int r,int c,String cls){
        table.addCl(r,c,cls);
    }
    public String page(){
        int PageNum=getPageNum();
        int NowPage=getNowPage();
//        if(PageNum==1) return "";
        int k=5;//当前页往外扩展页数
        String size="sm";
        StringBuilder s =new StringBuilder("<div class='btn-toolbar' role='toolbar'>");
        s.append("<div class='btn-group' role='group'>");
        if(NowPage==1){
            s.append(HTML.abtn(size,getLinkByPage(1),"<<","disabled"));
        }else{
            s.append(HTML.abtn(size,getLinkByPage(1),"<<",""));
        }
        int begin=NowPage-k/2;
        if(begin<1) begin=1;
        int end=NowPage+k/2;
        if(end>PageNum) end=PageNum;
        if(begin!=1){
            s.append(HTML.abtn(size,"","...","disabled"));
        }
        for(int i=begin;i<=end;i++){
            s.append(HTML.abtn(size,getLinkByPage(i),i+"",i==NowPage?"btn-primary":""));
        }
        if(end!=PageNum){
            s.append(HTML.abtn(size,"","...","disabled"));
        }
        if(NowPage==PageNum){
            s.append(HTML.abtn(size,getLinkByPage(PageNum),">>","disabled"));
        }else{
            s.append(HTML.abtn(size,getLinkByPage(PageNum),">>",""));
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
    public static int getPageNm(int Num,int everyPageNum){
        if(Num==0) return 1;
        if(Num%everyPageNum==0){
            return Num/everyPageNum;
        }else return Num/everyPageNum+1;
    }
}
