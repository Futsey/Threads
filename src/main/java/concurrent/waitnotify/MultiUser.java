package concurrent.waitnotify;

public class MultiUser {

    public static void main(String[] args) {
        Barrier barrier = new Barrier();
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName().concat(" started"));
                    barrier.on();
                }, "Master"
        );
        Thread slave = new Thread(
                () -> {
                    try {
                        int count = 0;
                        StringBuilder sb = new StringBuilder("\rWaiting for slave");
                        while (!Thread.currentThread().isInterrupted()) {
                            Thread.sleep(1000);
                            System.out.print(sb.append("."));
                            count++;
                            if (count >= 5) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    barrier.check();
                    System.out.println("\r".concat(Thread.currentThread().getName()).concat(" started"));
                }, "Slave"
                );
        master.start();
        slave.start();
    }
}
