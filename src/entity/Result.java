package entity;

/**
 * Created by Administrator on 2015/5/22.
 */
public enum Result{
        PENDING(0),
        AC(1),
        WA(2),
        CE(3),
        RE(4),
        TLE(5),
        MLE(6),
        OLE(7),
        PE(8),
        DANGER(9),
        RUNNING(10),
        ERROR(11),
        JUDGING(12);

        int value;
        Result(int v){
                value=v;
        }

        public int getValue() {
                return value;
        }

        public void setValue(int value) {
                this.value = value;
        }

        public boolean isAc(){
                return this == Result.AC;
        }
        public boolean isErr(){
                return  this == Result.WA||
                        this == Result.CE||
                        this == Result.RE||
                        this == Result.TLE||
                        this == Result.MLE||
                        this == Result.OLE||
                        this == Result.PE;
        }
        public boolean isPd(){
                return this == Result.PENDING||
                        this == Result.DANGER||
                        this == Result.RUNNING||
                        this == Result.ERROR||
                        this == Result.JUDGING;
        }
        public static Result getResultById(int value){
                for(Result r: Result.values()){
                        if(r.value == value) return r;
                }
                return null;
        }
}
