package project.model;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

public class ShortestPath implements Runnable {

    public static String URL;
    public static String URL2;
    private int time;
    private Tree tree;
    private boolean founded;
    private TextArea URLTa;
    private List<String> listOfPendingURLs;
    private List<String> listOfTraversedURLs;
    private List<String> pathList;

    public ShortestPath(TextArea URLTa, int time) {
        listOfPendingURLs = new ArrayList<>();
        listOfTraversedURLs = new ArrayList<>();
        this.time = time;
        tree = new Tree();
        founded = false;
        pathList = new ArrayList();
        this.URLTa = URLTa;
    }

    public void crawl() throws IOException, InterruptedException {
        listOfPendingURLs.add(URL);
        tree.setChild(URL, null);
        long timer = System.currentTimeMillis() + (time * 60 * 1000);
        while (!listOfPendingURLs.isEmpty() && System.currentTimeMillis() < timer && founded == false) {
            String urlString = listOfPendingURLs.remove(0);
            if (!listOfTraversedURLs.contains(urlString)) {
                listOfTraversedURLs.add(urlString);
                URLTa.appendText("crawling " + urlString + "\n");
                try {
                    Document doc = Jsoup.connect(urlString).get();
                    for (Element s : doc.select("a[href]")) {
                        if (!listOfTraversedURLs.contains(s.attr("abs:href"))) {
                            listOfPendingURLs.add(s.attr("abs:href"));
                            tree.setChild(s.attr("abs:href"), urlString);
                            if (URL2.equals(s.attr("abs:href"))) {
                                founded = true;
                                pathList = tree.getPath(URL2);
                                break;
                            }
                        }
                    }
                } catch (Exception ex) {
                }
            }
        }
        if (founded == true) {
            URLTa.clear();
            URLTa.appendText("Shortest Path:\n");
            for (int i = pathList.size() - 1; i >= 0; i--) {
                URLTa.appendText(pathList.get(i) + "\n");
                Thread.sleep(100);
            }
        } else {
            URLTa.appendText("There is no path\n");
        }
    }

    @Override
    public void run() {
        
        try {
            crawl();
        } catch (IOException ex) {
            Logger.getLogger(ShortestPath.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ShortestPath.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
