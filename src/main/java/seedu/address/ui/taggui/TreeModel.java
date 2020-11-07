package seedu.address.ui.taggui;

import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeModel {

    List<Cell> cells;
    List<Edge> edges;

    Map<Tag,Cell> cellMap; // <id,cell>

    public TreeModel() {
        cells = new ArrayList<>();
        edges = new ArrayList<>();
        cellMap = new HashMap<>();
    }

    public void clear() {
        cells.clear();
        edges.clear();
        cellMap.clear();
    }

    public void addCell(Tag tag, String contactDetails) {
        TagCell tagCell = new TagCell(tag, contactDetails);
        cells.add(tagCell);

        cellMap.put(tagCell.getTag(), tagCell);

    }

    public boolean containsTag(Tag tag) {
        return cellMap.containsKey(tag);
    }

    public void addEdge(Tag parentTag, Tag childTag) {

        Cell sourceCell = cellMap.get(parentTag);
        Cell targetCell = cellMap.get(childTag);

        Edge edge = new Edge( sourceCell, targetCell);

        edges.add(edge);
    }

    public void setCellLocation(Tag tag, double xCoordinate, double yCoordinate) {
        cellMap.get(tag).relocate(xCoordinate, yCoordinate);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Cell> getCells() {
        return cells;
    }


}