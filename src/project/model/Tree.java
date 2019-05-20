package project.model;

import java.util.ArrayList;
import java.util.List;

public class Tree {

    private List<String> list;
    private List<Node> listOfNodes;

    public Tree() {
        list = new ArrayList();
        listOfNodes = new ArrayList();
    }

    public void setChild(String URL, String parentURL) {
        list.add(URL);
        listOfNodes.add(new Node(list.indexOf(URL), list.indexOf(parentURL)));
    }

    public int getParent(String child) {
        return listOfNodes.get(list.indexOf(child)).getParent();

    }

    public List getPath(String URL) {
        List<String> pathList = new ArrayList();
        pathList.add(URL);
        int parent = list.indexOf(URL);
        while (parent != 0) {
           pathList.add(list.get(listOfNodes.get(parent).getParent()));
           parent = listOfNodes.get(parent).getParent();
        }
        return pathList;
    }
}
