package util.HTML;

import entity.Goods;
import util.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QAQ on 2016/9/17.
 */
public class MallHTML {
    public static String index(){
        StringBuilder sb = new StringBuilder();
        List<Goods> list = Main.goods.getIndexGoods();
        for(Goods goods : list){
            sb.append(indexEveryGoods(goods));
        }
        return sb.toString();
    }
    private static String coverImg(int id){
        return "<img src='pic/Mall/"+id+".jpg' class='coverImg' onerror='this.src=\"pic/defaulthead.jpg\"' />";
    }
    private static String indexEveryGoods(Goods goods){
        StringBuilder sb = new StringBuilder();
        String coverImg = coverImg(goods.getId());
        String title = HTML.div("goods-title",HTML.text(goods.getTitle(),4));
        String acb = HTML.center(HTML.text(goods.getAcb()+" ACB",3,"#f66"));
        String stock = HTML.floatRight(HTML.text("剩余:"+goods.getStock()+"　","#aaa"));
        sb.append(HTML.div("goods",coverImg+title+stock+acb));
        return sb.toString();
    }
}
