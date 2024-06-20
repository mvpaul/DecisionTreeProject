package sol;

import src.ITreeGenerator;
import src.ITreeNode;
import src.Row;

import javax.xml.crypto.Data;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A class that implements the ITreeGenerator interface used to generate a decision tree
 */

public class TreeGenerator implements ITreeGenerator<Dataset> {
    private AttributeNode root;
    private Dataset data;

    private List<String> removedSplittingAttributes;

    private String target;

    private ITreeNode tree;



    /**
     * Generates the decision tree for a given training dataset.
     *
     * @param trainingData    the dataset to train on
     * @param targetAttribute the attribute to predict
     */

    @Override
    public void generateTree(Dataset trainingData, String targetAttribute) {
        this.data = trainingData;
        this.removedSplittingAttributes = new ArrayList<>();
        targetAttribute = this.data.getAttributeList().get(data.getAttributeList().size()-1);
        this.data.getAttributeList().remove(targetAttribute); //remove Outcome
        this.root = new AttributeNode(this.data.getAttributeToSplitOn());
        this.root.provideSubset(this.data);


        this.tree = growTree(this.data, targetAttribute);
        parseTree(tree);

    }

    public ITreeNode growTree(Dataset subset1, String targetAttribute){
        List<Row> generationRows = subset1.getDataObjects();
        //Base-case: if there is one row to generate on, make a leaf
        if(generationRows.size() == 0 || subset1.checkSameOutcome(subset1)){
            DecisionLeaf decisionLeaf = new DecisionLeaf();
            decisionLeaf.setDecision(subset1.getDataObjects().get(0).getAttributeValue(targetAttribute)); //assigns outcome
            System.out.println("Arrives here" + subset1.getDataObjects().get(0).getAttributeValue(targetAttribute));
            return decisionLeaf;
        }
        else{
            String splittingAttribute = subset1.getAttributeToSplitOn();
            List<String> attributeColumn =subset1.getAttributeColumn(splittingAttribute); //column of attribute field
            AttributeNode attributeNode = new AttributeNode(splittingAttribute);
            for(Object distinctValue: this.getDistinct(attributeColumn)){
                System.out.println(splittingAttribute + ", value of: " + distinctValue);
                Dataset subset = subset1.filter(splittingAttribute, distinctValue);
                subset.getAttributeList().remove(splittingAttribute);
                attributeNode.addEdge(new ValueEdge(distinctValue, growTree(subset, targetAttribute)));
                System.out.println(subset.getDataObjects()); //print the rows
            }
            return attributeNode;
        }
    }

    public List<String> getDistinct(List<String> inputValues){
        List<String> distinctAttributeValues = new ArrayList<>();
        for (String value: inputValues) {
            if (!distinctAttributeValues.contains(value)) {
                distinctAttributeValues.add(value);
            }

        }
        return new ArrayList<>(distinctAttributeValues);
    }

    /**
     * Looks up the decision for a datum in the decision tree.
     *
     * @param datum the datum to lookup a decision for
     * @return the decision of the row
     */
    @Override
    public String getDecision(Row datum) {
        return tree.getDecision(datum);
    }

    public void parseTree(ITreeNode tree){
        if(tree instanceof AttributeNode){
            String attributeValue = ((AttributeNode) tree).getAttributeValue();
            System.out.println("ROOT VALUE" + attributeValue);
            System.out.println("VALEDGES" + ((AttributeNode) tree).getOutgoingEdges());
            for(ValueEdge edge : ((AttributeNode) tree).getOutgoingEdges()){
                System.out.println(edge.getDistinctValue());
                System.out.println("CHILD" + edge.getChild());
            }
        }
        if(tree instanceof DecisionLeaf){
            System.out.println("IT IS A LEAD");
        }
    }


}
