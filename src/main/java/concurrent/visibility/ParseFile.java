package concurrent.visibility;

import java.io.*;

public final class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String parseAllContent() {
        GetContent getContent = new GetAllContent();
        return getContent.getContent(file);
    }

    public String parseContentWithoutUnicode() {
        GetContent getContent = new GetContentWithoutUnicode();
        return getContent.getContent(file);
    }
}
