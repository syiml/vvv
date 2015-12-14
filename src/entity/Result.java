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
}
