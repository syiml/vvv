package CodeCompare.cplusplus;

/**
 * Created by Syiml on 2015/7/29 0029.
 */
public class ContestCodeCompareRecord implements Comparable<ContestCodeCompareRecord>{
    int rid1;
    int rid2;
    String user1;
    String user2;
    int pid;
    double f;
    public int compareTo(ContestCodeCompareRecord u){
        double x=f-u.f;
        if(x>0) return -1;
        else return 1;
    }
}
