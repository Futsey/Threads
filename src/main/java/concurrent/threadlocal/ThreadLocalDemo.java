package concurrent.threadlocal;

public class ThreadLocalDemo {

    /**
     * объявляем статическую переменную ThreadLocal
     */
    private static ThreadLocal<String> tl = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        Thread first = new FirstThread();
        Thread second = new SecondThread();
        /**
         * аписываем данные в переменную ThreadLocal
         */
        tl.set("Это поток main.");
        /**
         * получаем данные из этой переменной и выводим на печать
         */
        System.out.println(tl.get());
        first.start();
        second.start();
        first.join();
        second.join();
    }

    static class FirstThread extends Thread {
        @Override
        public void run() {
            /**
             * записываем данные в переменную ThreadLocal первой нити
             */
            ThreadLocalDemo.tl.set("Это поток 1.");
            /**
             * получаем данные из этой переменной и выводим на печать
             */
            System.out.println(ThreadLocalDemo.tl.get());
        }
    }

    static class SecondThread extends Thread {
        @Override
        public void run() {
            /**
             * записываем данные в переменную ThreadLocal второй нити
             */
            ThreadLocalDemo.tl.set("Это поток 2.");
            /**
             * получаем данные из этой переменной и выводим на печать
             */
            System.out.println(ThreadLocalDemo.tl.get());
        }
    }
}
