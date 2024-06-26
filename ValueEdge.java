package sol;

import src.ITreeNode;

/**
 * A class that represents the edge of an attribute node in the decision tree
 */
public class  ValueEdge {
    // TODO: add more fields if needed
    private ITreeNode child;
    private Object distinctValue;

    public ValueEdge(Object distinctValue, ITreeNode child){
        this.distinctValue = distinctValue;
        this.child = child;
    }

    public Object getDistinctValue(){
        return this.distinctValue;
    }

    public ITreeNode getChild(){
        return this.child;
    }



}
