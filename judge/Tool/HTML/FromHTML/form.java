package Tool.HTML.FromHTML;

/**
 * Created by Syiml on 2015/6/10.
 */
public abstract class form {
    public abstract String toHTML();
    public abstract String toHTML(int col1,int col2);
    public abstract void setValue(String s);
    public abstract void setDisabled();
}
