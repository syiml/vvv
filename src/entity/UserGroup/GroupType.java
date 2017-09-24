package entity.UserGroup;

/**
 * Created by QAQ on 2017/9/24.
 */
public enum GroupType {
    ICPC(1),//ICPC组队
    NEW_STU(2);//新生分组

    int id;

    GroupType(int id){
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public static GroupType getByID(int id){
        for(GroupType t : GroupType.values()){
            if(id == t.id) return t;
        }
        return null;
    }
}
