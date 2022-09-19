package concurrent;

public class DCLSingleton {

    private volatile static DCLSingleton inst;

    public static DCLSingleton instOf() {
        if (inst == null) {
            inst = new DCLSingleton();
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {
    }
}
