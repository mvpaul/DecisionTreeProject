package sol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import src.AttributeSelection;
import src.IDataset;
import src.Row;

import javax.xml.crypto.Data;

/**
 * A class representing a training dataset for the decision tree
 */
// TODO: Uncomment this once you've implemented the methods in the IDataset interface!
public class Dataset implements IDataset  {

    private List<String> attributeList;
    private AttributeSelection selectionType;
    private List<Row> dataObjects;

    /**
     * Constructor for a Dataset object
     * @param attributeList - a list of attributes
     * @param dataObjects -  a list of rows
     * @param attributeSelection - an enum for which way to select attributes
     */
    public Dataset(List<String> attributeList, List<Row> dataObjects, AttributeSelection attributeSelection) {
        this.attributeList = attributeList;
        this.selectionType = attributeSelection;
        this.dataObjects = dataObjects;
    }

    public String getAttributeToSplitOn() {
        switch (this.selectionType) {
            case ASCENDING_ALPHABETICAL -> {
                return this.attributeList.stream().sorted().toList().get(0);
            }
            case DESCENDING_ALPHABETICAL -> {
                return this.attributeList.stream().sorted().toList().get(this.attributeList.size() - 1);
            }
            case RANDOM -> {
                Random rand = new Random();
                int selection = rand.nextInt(attributeList.size());
                return this.attributeList.get(selection);
            }
        }
        throw new RuntimeException("Non-Exhaustive Switch Case");
    }

    public List<String> getAttributeColumn(String attributeHeader) {
        List<String> attributesCol = new ArrayList<>();
        for (Row row : this.dataObjects) {
            attributesCol.add(row.getAttributeValue(attributeHeader));
        }
        return attributesCol;
    }

    /**
     * Method that filters the dataset into a smaller subset of only the rows that correspond with a certain field.
     * @param specificValue
     * @return
     */
    public Dataset filter(Object specificValue, Object distinctValue){
        Dataset subset;
        List<Row> subsetRows = new ArrayList<>();
        for(Row row: this.dataObjects){
            if(row.getAttributeValue(specificValue.toString()).equals(distinctValue)){ //if a row has the distinct value
                subsetRows.add(row);
            }
        }
        System.out.println("LIST" + this.attributeList);
        subset = new Dataset(new ArrayList<>(this.attributeList),subsetRows,this.getSelectionType());
        return subset;
    }
    /**
     * Gets list of attributes in the dataset
     *
     * @return a list of strings
     */
    @Override
    public List<String> getAttributeList() {
        return this.attributeList;
    }

    /**
     * Gets list of data objects (row) in the dataset
     *
     * @return a list of Rows
     */
    @Override
    public List<Row> getDataObjects() {
        return this.dataObjects;
    }

    /**
     * Returns the attribute selection type (alphabetical, reverse alphabetical, random) for this Dataset
     *
     * @return the attribute selection type
     */
    @Override
    public AttributeSelection getSelectionType() {
        return this.selectionType;
    }

    /**
     * finds the size of the dataset (number of rows)
     *
     * @return the number of rows in the dataset
     */
    @Override
    public int size() {
        return this.dataObjects.size();
    }

    public void restoreAttributeList(List<String> removedAttributes){
        for(String item: removedAttributes){
            this.attributeList.add(item);
            //System.out.println("RESTORED" + this.attributeList);
        }

    }


    public Boolean checkSameOutcome(Dataset data){
        for(Row row: data.getDataObjects()){
            if (!row.getAttributeValue("Outcome").equals(getDataObjects().
                    get(0).getAttributeValue("Outcome"))){
                return false;
            }
        }
        return true;
    }
}
