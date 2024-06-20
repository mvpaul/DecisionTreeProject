package sol;

import org.junit.Assert;
import org.junit.Test;
import src.AttributeSelection;
import src.DecisionTreeCSVParser;
import src.Row;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

/**
 * A class to test basic decision tree functionality on a basic training dataset
 */
public class BasicDatasetTest {
    // IMPORTANT: for this filepath to work, make sure the project is open as the top-level directory in IntelliJ
    // (See the first yellow information box in the handout testing section for details)
    String trainingPath = "data/decisionTreeData.csv";
    String targetAttribute = "Output";
    TreeGenerator testGenerator;
    Dataset training;

    /**
     * Constructs the decision tree for testing based on the input file and the target attribute.
     */
    @Before
    public void buildTreeForTest() {
        List<Row> dataObjects = DecisionTreeCSVParser.parse(this.trainingPath);
        List<String> attributeList = new ArrayList<>(dataObjects.get(0).getAttributes());
        this.training = new Dataset(attributeList, dataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);
        // builds a TreeGenerator object and generates a tree for "foodType"
        this.testGenerator = new TreeGenerator();
        this.testGenerator.generateTree(training, this.targetAttribute);
    }

    /**
     * Tests the expected classification of the "tangerine" row is a fruit
     */
    @Test
    public void testClassification() {
        // makes a new (partial) Row representing the tangerine from the example
        // TODO: make your own rows based on your dataset
        Row courtCase = new Row("test row (court case)");
        courtCase.setAttributeValue("name", "Defendant1");
        courtCase.setAttributeValue("age", "<18");
        courtCase.setAttributeValue("citizen", "yes");
        courtCase.setAttributeValue("witness testimony", "harmful");
        courtCase.setAttributeValue("1st conviction", "yes");
        // TODO: make your own assertions based on the expected classifications
        // TODO: Uncomment this once you've implemented getDecision
       //Assert.assertEquals("guilty", this.testGenerator.getDecision(tangerine));
    }


}
