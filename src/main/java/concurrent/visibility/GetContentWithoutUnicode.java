package concurrent.visibility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class GetContentWithoutUnicode implements GetContent {

    @Override
    public String getContent(File source) {
        String output = "";
        try (InputStream bis = new BufferedInputStream(new FileInputStream(source), 1024)) {
            int data;
            while ((data = bis.read()) > 0) {
                if (data < 0x80) {
                    output += (char) data;
                }
            }
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
