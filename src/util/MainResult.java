package util;

/**
 * Created by QAQ on 2016/10/4.
 */
public enum MainResult {

    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    ARR_ERROR(2, "参数错误"),//参数错误
    NO_PERMISSION(3, "权限不足"),//没有权限
    NO_LOGIN(4, "未登录"),//未登录
    NO_STOCK(5, "库存不足"),//没有库存（acb商城购买）
    ACB_NOT_ENOUGH(6, "ACB不足"),//ACB不足
    BUY_NUM_LIMIT(7, "超出购买限制"),//超过限购数量
    VERIFY_EXIST(8, "不能重复认证"),
    BUY_OBJECT(9,"您的购买权限不足");
    int value;
    String prompt;

    MainResult(int v, String prompt) {
        value = v;
        this.prompt = prompt;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getPrompt() {
        return prompt;
    }

}
