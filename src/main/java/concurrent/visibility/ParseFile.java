package concurrent.visibility;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private synchronized  String parse(Predicate<Character> predicate) {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = reader.read()) != -1) {
                if (predicate.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized String getContent() {
        return parse(data -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return parse(data -> data < 128);
    }
}
