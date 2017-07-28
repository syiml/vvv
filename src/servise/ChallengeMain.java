package servise;

import dao.ChallengeSQL;
import entity.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Syiml on 2015/10/10 0010.
 */
public class ChallengeMain {

    public enum BlockType{
        basics(0,"基础"),
        dataStr(1,"数据结构"),
        math(2,"数学"),
        geometry(3,"几何"),
        graph(4,"图论"),
        search(5,"搜索"),
        dp(6,"动态规划");

        private int index;
        private String name;

        BlockType(int index, String name){
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }
    }

    public static Map<Integer, Block> blocks;
    public static List<BlockType> typeList;
    static{
        init();
        System.out.println("ChallengeMain init");
    }

    public static void init(){
        blocks= ChallengeSQL.init();
        typeList = new ArrayList<BlockType>();
        for (BlockType type: BlockType.values()) {
            typeList.add(type);
        }
    }

}
