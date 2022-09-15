package concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Wget implements Runnable {

    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream())) {
            FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml");
            byte[] buff = new byte[1024];
            int bytesRead;
            long start = System.currentTimeMillis();
            System.out.println("Start: ".concat(String.valueOf(start)));
            String startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(start));
            System.out.println("StartDate: ".concat(startDate));
            while ((bytesRead = in.read(buff)) != -1) {
                fileOutputStream.write(buff, 0, bytesRead);
                long finish = System.currentTimeMillis();
                System.out.println("Finished: ".concat(String.valueOf(finish))
                        .concat(" | Diff = ")
                        .concat(String.valueOf(finish - start)));
                String finishDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(finish));
                System.out.println("FinishDate: ".concat(finishDate));
                if ((finish - start) < speed) {
                    Thread.sleep(1000);
                }
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
