package sol;

import java.util.ArrayList;
import java.util.List;
import src.ITreeNode;
import src.Row;

import javax.xml.crypto.Data;

/**
 * A class representing an inner node in the decision tree.
 */
// TODO: Uncomment this once you've implemented the methods in the ITreeNode interface!
public class AttributeNode implements ITreeNode  {
    private List<ValueEdge> outgoingEdges;
    private String attributeValue;
    private Dataset subset;
    private List<ITreeNode> childrenNodes;

    private String target;

    public AttributeNode(String attribute){
        this.attributeValue = attribute;
        this.outgoingEdges = new ArrayList<>();
        this.childrenNodes = new ArrayList<>();
    }

    public void addEdge(ValueEdge edge){
        this.outgoingEdges.add(edge);
        //this.childrenNodes.add(child);
    }


    /**
     * Recursively traverses decision tree to return tree's decision for a row.
     *
     * @param forDatum the datum to lookup a decision for
     * @return the decision tree's decision
     */
    @Override
    public String getDecision(Row forDatum) {
        String value = this.getAttributeValue();
        String valueField = forDatum.getAttributeValue(value);
        for(ValueEdge edge : this.getOutgoingEdges()) {
            if (edge.getDistinctValue().equals(valueField)) {
                return edge.getChild().getDecision(forDatum);
            }
        }
        return this.getDefault().toString();
    }

    public String getDefault(){
        List<Row> outcome1 = new ArrayList<Row>();
        List<Row> outcome2 = new ArrayList<Row>();
        List<Row> totalList = this.subset.getDataObjects();
        if(totalList.size() == 1){
            outcome1.add(totalList.get(0));
            return outcome1.get(0).getAttributeValue(this.target);
        }
        else {
            outcome1.add(totalList.get(0));
            totalList.remove(0);
            for(Row row: totalList){
                if(row.getAttributeValue(this.target).equals(outcome1.get(0).
                        getAttributeValue(this.target))){
                    outcome1.add(row);
                }
                else {
                    outcome2.add(row);
                }
            }
            if(outcome1.size() >outcome2.size()){
                return outcome1.get(0).getAttributeValue(this.target);
            }
            else {
                return outcome2.get(0).getAttributeValue(this.target);
            }
        }
    }

    public void provideSubset(Dataset subset){
        this.subset = subset;
    }

    public void provideTarget(String targetAttribute){
        this.target = targetAttribute;
    }

    public String getAttributeValue(){
        return this.attributeValue;
    }

    public List<ValueEdge> getOutgoingEdges(){
        return this.outgoingEdges;
    }

    public List<ITreeNode> getChildrenNodes(){
        return this.childrenNodes;
    }

    public void addChild(ITreeNode child, ValueEdge edge){
        this.childrenNodes.add(child);
        this.outgoingEdges.add(edge);
    }

}
