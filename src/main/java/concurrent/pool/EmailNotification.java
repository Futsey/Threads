package concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );


    private void emailTo(User user) {
        pool.submit(() -> {
            String email = user.getEmail();
            String username = user.getUsername();
            String subject = String.format("subject = Notification %s to email %s.", username, email);
            String body = String.format("body = Add a new event to %s", username);
            send(subject, body, email);
        });
    }

    private void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }
}
