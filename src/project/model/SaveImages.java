package project.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javafx.scene.control.TextArea;

public class SaveImages implements Runnable {

    public static File downloadPath;
    public static List<String> listOfImagesURLs;
    private TextArea URLTa;

    public SaveImages(TextArea URLTa, File downloadPath) {
        this.downloadPath = downloadPath;
        this.URLTa = URLTa;
    }

    public void Save() throws MalformedURLException, IOException {

        for (int i = 0; i < listOfImagesURLs.size(); i++) {
            String src = listOfImagesURLs.get(i);
            int indexname = src.lastIndexOf("/");
            if (indexname == src.length()) {
                src = src.substring(0, src.length() - 1);
            }
            indexname = src.lastIndexOf("/");
            String name = src.substring(indexname, src.length());

            URL url = new URL(src);

            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            in.close();
            byte[] response = out.toByteArray();
            out.close();

            try {
                FileOutputStream fos = new FileOutputStream(downloadPath.getPath() + name);
                fos.write(response);
                URLTa.appendText("saving: " + name.substring(1, name.length()) + "\n");
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        URLTa.appendText("done.\n");
    }

    @Override
    public void run() {
        try {
            Save();
        } catch (IOException ex) {
        }
    }
}
