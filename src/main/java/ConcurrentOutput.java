public class ConcurrentOutput {

    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println("Первая нить".concat(Thread.currentThread().getName()))
        );
        Thread second = new Thread(
                () -> System.out.println("Вторая нить".concat(Thread.currentThread().getName()))
        );
        another.start();
        second.start();
        for (int i = 0; i < 10; i++) {
            System.out.println("===== JVM решает что использовать многозадачность или параллелизм");
            System.out.println(Thread.currentThread().getName());
        }
    }
}
