package entity.exam;

/**
 * Created by QAQ on 2016/5/26 0026.
 */
public enum ExamProblemType {
    ERROR(-1),
    CHOICE(0),
    CODE_FILL(1),
    FILL_BLANK(2),
    PROGRAM(3);

    ExamProblemType(int code){
        this.code = code;
    }
    private int code;
    public int getCode() {
        return code;
    }

    public static ExamProblemType getByCode(int code){
        for(ExamProblemType it : ExamProblemType.values()){
            if(it.code == code){
                return it;
            }
        }
        return ExamProblemType.ERROR;
    }
    public String toString(){
        switch (this){
            case CHOICE:return "选择题";
            case CODE_FILL:return "代码填空题";
            case FILL_BLANK:return "结果填空题";
            case PROGRAM:return "编程题";
        }
        return "错误";
    }
}
