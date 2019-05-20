package project.model;

import java.io.IOException;
import java.util.*;
import javafx.scene.control.TextArea;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

public class Crawler implements Runnable {

    public static String URL;
    public static String downloadPath;
    private int Task;
    private int time;
    private TextArea URLTa;
    private List<String> listOfPendingURLs;
    private List<String> listOfTraversedURLs;
    private List<String> listOfDeadLinks;
    private List<String> listOfImagesURLs;

    public Crawler(int Task, TextArea URLTa, int time) {
        this.Task = Task;
        this.URLTa = URLTa;
        listOfPendingURLs = new ArrayList<>();
        listOfTraversedURLs = new ArrayList<>();
        listOfDeadLinks = new ArrayList<>();
        listOfImagesURLs = new ArrayList<>();
        this.time = time;
    }

    @Override
    public void run() {
        switch (Task) {
            case 1:
                try {
                    crawl();
                    URLTa.appendText("wait for scraping...\n");
                    Scraping scraping = new Scraping(listOfTraversedURLs);
                    URLTa.appendText("done.\n");
                } catch (IOException ex) {
                    System.out.println("Exception from crawl method or scraping class");
                }
                break;
            case 2:
                try {
                    crawlForImages();
                    SaveImages.listOfImagesURLs = listOfImagesURLs;
                } catch (IOException ex) {
                    System.out.println("Exception from crawlForImages method");
                }
                break;
            case 3:
                try {
                    crawl();
                } catch (IOException ex) {
                    System.out.println("Exception from crawl method");
                }

                if (listOfDeadLinks.isEmpty()) {
                    URLTa.appendText("There are no dead links");
                } else {
                    URLTa.setText("Dead links:\n");
                    for (int i = 0; i < listOfDeadLinks.size(); i++) {
                        URLTa.appendText(listOfDeadLinks.get(i) + "\n");
                    }
                }
                break;
        }
    }

    public void crawl() throws IOException {
        listOfPendingURLs.add(URL);
        long timer = System.currentTimeMillis() + (time * 60 * 1000);
        while (!listOfPendingURLs.isEmpty() && System.currentTimeMillis() < timer) {
            String urlString = listOfPendingURLs.remove(0);
            if (!listOfTraversedURLs.contains(urlString)) {
                listOfTraversedURLs.add(urlString);
                URLTa.appendText("crawling " + urlString + "\n");
                try {
                    Document doc = Jsoup.connect(urlString)
                            .userAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.46 Safari/536.5")
                            .get();
                    for (Element s : doc.select("a[href]")) {
                        if (!listOfTraversedURLs.contains(s.attr("abs:href"))) {
                            listOfPendingURLs.add(s.attr("abs:href"));
                        }
                    }
                } catch (HttpStatusException e) {
                    listOfDeadLinks.add(e.getStatusCode() + " | " + urlString);
                } catch (Exception e) {
                }
            }
        }
    }

    public void crawlForImages() throws IOException {
        listOfPendingURLs.add(URL);
        long timer = System.currentTimeMillis() + (time * 60 * 1000);
        while (!listOfPendingURLs.isEmpty()
                && System.currentTimeMillis() < timer) {
            String urlString = listOfPendingURLs.remove(0);
            if (!listOfTraversedURLs.contains(urlString)) {
                listOfTraversedURLs.add(urlString);
                try {
                    Document doc = Jsoup.connect(urlString).get();
                    for (Element s : doc.select("a[href]")) {
                        if (!listOfTraversedURLs.contains(s.attr("abs:href"))) {
                            listOfPendingURLs.add(s.attr("abs:href"));
                            for (Element e : doc.select("img")) {
                                if (!listOfImagesURLs.contains(e.absUrl("src"))&&!e.absUrl("src").equals("")) {
                                    Thread.sleep(100);
                                    URLTa.appendText("Image: " + e.absUrl("src") + "\n");
                                    listOfImagesURLs.add(e.absUrl("src"));

                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                }
            }
        }
        URLTa.appendText("done.\n");
    }
}
