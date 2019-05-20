package project.model;

import java.io.IOException;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
public class Scraping {
    
    public Scraping(List<String> links) throws IOException{
        scrap(links);
    }
    
    public void scrap(List<String> links) throws IOException{
        List<String> words = new ArrayList<>();
        for (String s:links){
            try {
                Document doc = Jsoup.connect(s).get();
                words.add(doc.wholeText());
 
            } catch (IOException ex) {
                System.out.println ("IOException: " + ex.toString());  
            }
        }

        Filtration f = new Filtration();
        CountWords CW= new CountWords();
        Print p= new Print();
        for (int i = 0; i<words.size();i++){
            p.output(links.get(i),CW.getcountText(f.filter(words.get(i))));
        }
        
        
    }
}