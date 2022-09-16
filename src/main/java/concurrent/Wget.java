package concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;
    private final String dest;
    private final int speed;
    private static final String validate = """
            Please check your arguments. You must enter:
            1. URL. (For example: https://.google.com);
            2. Destination file name to write your data;
            3. Maximum download speed limit in Kb. For example: 10000 Kb = 80Mbit;
            """;

    public Wget(String url, String dest, int speed) {
        this.url = url;
        this.dest = dest;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream())) {
            FileOutputStream fileOutputStream = new FileOutputStream(dest);
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
                    }
                    loadedData = 0;
                    start = System.currentTimeMillis();
                }
                fileOutputStream.write(buff, 0, bytesRead);
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3) {
            throw new IllegalArgumentException(validate);
        }
        String url = args[0];
        String dest = args[1];
        int speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(url, dest, speed));
        wget.start();
        wget.join();
    }
}
