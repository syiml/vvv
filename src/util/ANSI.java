package util;

/**
 * 用于控制控制台显示颜色
 * 字符串用ANSI.RED + s + ANSI.RESET 表示显示s的颜色为red
 */
public enum ANSI{
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");
    String color;
    ANSI(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
