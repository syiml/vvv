package util.HTML;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * pageBean 的基类，显示一个带翻页的表格列表panel
 * Created by Syiml on 2015/11/2 0002.
 */
public abstract class pageBean {
    private String cl="table";
    protected TableHTML table = new TableHTML();
    protected List<String> Colname = new ArrayList<String>();

    protected String ERROR_CELL_TEXT="=ERROR=";

    /**
     * 返回panel顶上的标题栏显示的内容
     * @return 标题
     */
    public abstract String getTitle();

    /**
     * @return 当前页的行数
     */
    public abstract int getCurrPageSize();

    /**
     * 重写这个方法，返回当前页的总页数
     * @return 总页数
     */
    public abstract int getTotalPageNum();

    /**
     * 重写这个方法，返回当前的页码是第几页
     * @return 当前页码数
     */
    public abstract int getCurrPage();

    /**
     * 重写这个方法，指定第i行的列名为colname的单元格要显示的内容
     * @param i 第i行
     * @param colname 列名
     * @return 单元格显示的内容
     */
    public abstract String getCellByHead(int i,String colname);//第i行的每列的内容

    /**
     * 重写这个方法，指定当用户点击第page页的按钮时，应该跳转到哪个页面
     * @param page 页码
     * @return 跳转的超链接
     */
    public abstract String getLinkByPage(int page);

    /**
     * 返回在页码右侧的表单内容（一般用于做筛选表单）
     * 如果不需要这个表单，请返回空串
     * @return 表单内容
     */
    public abstract String rightForm();

    /**
     * 表格的class，用于前端显示表格的样式（参考bootstrap表格样式）
     * @return table class
     */
    public String getTableClass(){return cl;}
    protected void setCl(String cl){this.cl=cl;}

    /**
     * 添加表格的列名，会按添加的顺序显示
     * @param ss 列名列表
     */
    protected void addTableHead(String... ss){
        Collections.addAll(Colname,ss);
    }

    /**
     * 当表头显示的内容和列名不同时，可以重写该方法。
     * @param colname 当前列名
     * @return 返回新的表头位置显示的内容
     */
    protected String getColname(String colname){
        return colname;
    }

    /**
     * 返回表格的HTML代码，HTML()方法会调用，一般不用重写
     * @return 表格HTML代码
     */
    protected String tableHTML(){
        for (String aColname : Colname) {
            table.addColname(getColname(aColname));
        }
        table.setClass(getTableClass());
        int PageSize= getCurrPageSize();
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

    /**
     * 给第r行，第c列单元格设定一个class，用来控制样式的显示。（参考bootstrap的表格单元格样式）
     * @param r 行
     * @param c 列
     * @param cls 类名
     */
    public void addClass(int r,int c,String cls){
        table.addCl(r,c,cls);
    }

    /**
     * @return 返回翻页按钮组的HTML代码，一般不用重写
     */
    public String page(){
        int PageNum= getTotalPageNum();
        int NowPage= getCurrPage();
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
//        if(getTotalPageNum()==1) return "";
//        return HTML.div("panel-body","style='padding:5px'",page());
    }
    public String HTML(){
        return HTML.panelnobody(getTitle(),head()+tableHTML()+foot());
    }

    /**
     * 静态函数，根据总数和每页显示的数量计算总页码数
     * @param Num 总条目数
     * @param everyPageNum 每页的数量
     * @return 总页数
     */
    public static int getTotalPageNum(int Num, int everyPageNum){
        if(Num==0) return 1;
        if(Num%everyPageNum==0){
            return Num/everyPageNum;
        }else return Num/everyPageNum+1;
    }

    private String btn(String href, String text){
        return HTML.abtn("sm",href,text,"");
    }
    private String btn_disabled(String href, String text){
        return HTML.abtn("sm",href,text,"disabled");
    }
    private String btn_active(String href, String text){
        return HTML.abtn("sm",href,text,"btn-primary");
    }
}
