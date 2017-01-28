package entity.Enmu;

/**
 * Created by QAQ on 2017/1/25.
 */
public enum UserStarType {
    PROBLEM(1),
    STATUS(2);

    int value;
    UserStarType(int value){
        this.value = value;
    }
    public static UserStarType getUserStartTypeByID(int value){
        for (UserStarType userStartType:UserStarType.values()){
            if(userStartType.value == value) return userStartType;
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
