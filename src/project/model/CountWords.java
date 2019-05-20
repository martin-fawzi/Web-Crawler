package project.model;

import java.util.*;

public class CountWords{
    public Map<String, Integer> getcountText(String [] words) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        for (String temp1 : words) {
            Integer count = map.get(temp1);
            map.put(temp1, (count == null) ? 1 : count + 1);
        }
        Map<String, Integer> treeMap = new TreeMap<String, Integer>(map);
        return treeMap;
    }
}

