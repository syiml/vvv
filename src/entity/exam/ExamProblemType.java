package entity.exam;

/**
 * Created by QAQ on 2016/5/26 0026.
 */
public enum ExamProblemType {
    ERROR(-1),
    CHOICE(0),
    FILL_BLANK(1),
    CODE_FILL(2),
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
}
