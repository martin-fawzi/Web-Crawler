
package project.model;
public class Node {
    private int index;
    private int parent;
    public Node(int index, int parent){
        this.index = index;
        this.parent = parent; 
    }   

    public int getParent(){
        return parent;
    }
    
}
