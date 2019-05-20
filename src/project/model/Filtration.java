package project.model;

public class Filtration {

    public String [] filter(String words){
        words = words.replaceAll("[^A-Za-z]", " ");
        words = words.replaceAll("( )+", " ");        
        return words.split(" ");  
    }
}