package util;

import util.HTML.pageBean;

import java.util.List;

/**
 * 一个直接显示Bean列表的pageBean
 * 继承它，然后重写指定的方法即可
 * Created by QAQ on 2017/1/29.
 */
public abstract class SimplePageBean<T> extends pageBean {

    private List<T> list;
    private int page;

    protected SimplePageBean(int page) {
        this.page = page;
        this.list = getElement( getEveryPageNumber() * (page-1) ,getEveryPageNumber());
    }

    public abstract List<T> getElement(int from,int num);

    /**
     * @return 总数量
     */
    public abstract int getTotalNumber();

    /**
     * @return 每页的大小
     */
    public abstract int getEveryPageNumber();

    /**
     * 重写这个方法返回每个单元格的内容
     * @param i 当前行
     * @param cla 当前行要展示的对象
     * @param colname 当前列的列名
     * @return 单元格内容 HTML格式
     */
    public abstract String getCellByClass(int i,T cla,String colname);

    /**
     * 重写这个方法指定每一列的列名
     * 默认显示列名，如果要更换列名的显示，重写pageBean.getColname()方法
     * @return 返回列名的数组
     */
    public abstract String[] getColNames();

    @Override
    public int getCurrPageSize() {
        return list.size();
    }

    @Override
    public int getTotalPageNum() {
        return pageBean.getTotalPageNum(getTotalNumber(), getEveryPageNumber());
    }

    @Override
    public int getCurrPage() {
        return page;
    }

    @Override
    public String getCellByHead(int i, String colname) {
        return getCellByClass(i,list.get(i),colname);
    }
}
