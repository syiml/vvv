package Challenge;

import java.util.Map;

/**
 * Created by Syiml on 2015/10/10 0010.
 */
public class ChallengeMain {
    public static Map<Integer,Block> blocks;
    static{
        init();
        System.out.println("ChallengeMain init");
    }
    public static void init(){
        blocks= ChallengeSQL.init();
    }

}
