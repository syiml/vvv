package dao;

/**
 * Created by Administrator on 2015/11/23 0023.
 */
public interface IBaseDao <Bean> {
    void save(Bean bean);
    void edit(Bean bean);
}
