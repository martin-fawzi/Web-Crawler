package project.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.control.TextArea;

public class WordSearch implements Runnable {

    private TextArea URLTa;
    private File directory;
    private String searchWord;

    public WordSearch(TextArea URLTa, String searchWord) {
        directory = new File("Result");
        this.URLTa = URLTa;
        this.URLTa.clear();
        this.searchWord = searchWord;
    }

    void directoryCrawler(File directory, String searchWord) {

        File[] filesAndDirs = directory.listFiles();
        for (File file : filesAndDirs) {
            if (file.isFile()) {
                searchWord(file, searchWord);
            } else {
                directoryCrawler(file, searchWord);
            }
        }
    }
    
    void searchWord(File file, String searchWord) {
        Scanner scanFile;
        String line;
        String url = null;

        try {
            scanFile = new Scanner(file);

            if (scanFile.hasNext()) {
                url = scanFile.nextLine();
            }
            while (scanFile.hasNext()) {
                line = scanFile.nextLine();

                String str = "";
                for (char c : line.toCharArray()) {
                    if (c != ' ') {
                        str += c;
                        continue;
                    }
                    if (str.equals(searchWord)) {
                        URLTa.appendText(url + "\n");
                        URLTa.appendText(line + "\n");
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                        }

                    }
                    str = "";
                }

            }
            scanFile.close();
        } catch (FileNotFoundException e) {
        }
    }
    @Override
    public void run() {
        directoryCrawler(directory, searchWord);
    }
}
