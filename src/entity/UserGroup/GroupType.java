package entity.UserGroup;

/**
 * Created by QAQ on 2017/9/24.
 */
public enum GroupType {
    ICPC(1,"集训队"),//ICPC组队
    NEW_STU(2,"新生分组");//新生分组

    int id;
    String name;

    GroupType(int id,String name){
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static GroupType getByID(int id){
        for(GroupType t : GroupType.values()){
            if(id == t.id) return t;
        }
        return null;
    }
}