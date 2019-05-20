package project.model;

import java.io.*;
import java.util.Map;

public class Print {
        private int n = 0;
        
    public void output (String link ,Map<String, Integer> map) throws IOException {
        try {
                File folder = new File("Result");
                folder.mkdir();
                File file = new File("Result/file" + ++n +".txt");
                FileWriter writer = new FileWriter(file);
                BufferedWriter buffer = new BufferedWriter(writer);
                buffer.write(link+"\n");
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    buffer.write( entry.getKey() + "   " + entry.getValue()+"\n");
                }
                buffer.flush();
                writer.close();
           
        }catch (FileNotFoundException ex){
            System.out.println(ex.toString());
            
        }
    }
}