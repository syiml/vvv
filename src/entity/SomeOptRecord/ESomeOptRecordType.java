package entity.SomeOptRecord;

/**
 *
 * Created by QAQ on 2017/3/16.
 */
public enum ESomeOptRecordType {

    ClockIn(1),
    Vote(2);



    private int value;
    ESomeOptRecordType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ESomeOptRecordType getByValue(int value){
        for(ESomeOptRecordType type : ESomeOptRecordType.values()){
            if(type.getValue() == value) return type;
        }
        return null;
    }
}
