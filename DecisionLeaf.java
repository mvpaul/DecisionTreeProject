package sol;

import src.ITreeNode;
import src.Row;

/**
 * A class representing a leaf in the decision tree.
 */
public class DecisionLeaf implements ITreeNode {

    private Object outcome;

    /**
     * Recursively traverses decision tree to return tree's decision for a row.
     *
     * @param forDatum the datum to lookup a decision for
     * @return the decision tree's decision
     */
    @Override
    public String getDecision(Row forDatum) {
        return outcome.toString();
    }

    public void setDecision(String decision){
        this.outcome = decision;
    }
    // TODO: add fields as needed

    // TODO: Implement the ITreeNode interface
}
