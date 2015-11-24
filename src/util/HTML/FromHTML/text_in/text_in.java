package util.HTML.FromHTML.text_in;

import util.HTML.FromHTML.form;

/**
 * Created by Syiml on 2015/6/15 0015.
 */
public class text_in extends form {
    String s;
    public text_in(String s){
        this.s=s;
    }
    public String toHTML(){
        return s;
    }
    public String toHTML(int col1,int col2){
        return s;
    }
    public void setValue(String s){
        this.s=s;
    }
    public void setDisabled(){

    }
}
