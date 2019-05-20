package project.model;

import java.io.File;
import javafx.scene.control.TextArea;

public class ImageSearch implements Runnable {

    private File dir;
    private TextArea URLTa = new TextArea();
    private String ImageName;

    public ImageSearch(TextArea URLTa, String ImageName) throws InterruptedException {
        dir = SaveImages.downloadPath;
        this.URLTa = URLTa;
        this.ImageName = ImageName;
    }

    public void Search(String ImageName) throws InterruptedException {
        String[] children = dir.list();
        URLTa.clear();
        if (children == null) {
            URLTa.appendText("sorry, image doesn't exist\n");
        } else {
            for (int i = 0; i < children.length; i++) {
                if (children[i].contains(ImageName)) {
                    URLTa.appendText(children[i] + "\n");
                }
                Thread.sleep(100);
            }
        }
    }

    @Override
    public void run() {
        try {
            Search(ImageName);
        } catch (InterruptedException ex) {
        }
    }
}
