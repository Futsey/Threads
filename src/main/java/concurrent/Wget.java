package concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

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
            int loadedData = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(buff)) != -1) {
                loadedData += bytesRead;
                if (loadedData >= speed) {
                    long finish = System.currentTimeMillis();
                    if ((finish - start) < 1000) {
                        Thread.sleep(1000 - (finish - start));
                        loadedData = 0;
                        start = System.currentTimeMillis();
                    }
                }
                fileOutputStream.write(buff, 0, bytesRead);
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
