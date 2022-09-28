package concurrent.visibility;

import java.io.*;

public class SaveToTXTFile implements SaveFile {

    @Override
    public boolean save(String file, File target) {
        boolean rsl = false;
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(target))) {
            for (int i = 0; i < file.length(); i++) {
                os.write(file.charAt(i));
            }
            System.out.println("File saved to txt: ".concat(target.getAbsolutePath()));
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }
}
