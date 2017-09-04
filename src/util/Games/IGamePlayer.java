package util.Games;

/**
 * Created by syimlzhu on 2017/1/13.
 */
public interface IGamePlayer {
    void setTimeOut(long millisecond);
    int getInt() throws GameReadException;
    void putInt(int a) throws GameReadException;

    void gameEnd(int a);
    int getID();
    String getAuthor();
}