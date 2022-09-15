package concurrent;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] process = new char[]{'\\', '|', '/'};
        try {
            int count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(500);
                System.out.print("\r load: " + process[count++]);
                    if (count >= process.length) {
                        count = 0;
                    }
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
