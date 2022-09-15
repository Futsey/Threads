package concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileDownload {

    public static void main(String[] args) throws Exception {
        String file = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long speedTemplate = getDownloadSpeed(in, dataBuffer);
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                if (speedTemplate < 1000) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    Thread.sleep(1000);
                }
            }
        } catch (IOException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static long getDownloadSpeed(BufferedInputStream in, byte[] dataBuffer)  throws Exception {
        long rsl = 0;
        long start = System.currentTimeMillis();
        in.read(dataBuffer, 0, 1024);
        long finish = System.currentTimeMillis();
        rsl = (finish - start);
        return rsl;
    }
}
