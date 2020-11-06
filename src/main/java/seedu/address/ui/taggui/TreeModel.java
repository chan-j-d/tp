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

        // clear model, create lists
        clear();
    }

    public void clear() {
        cells = new ArrayList<>();
        edges = new ArrayList<>();
        cellMap = new HashMap<>(); // <id,cell>
    }

    public void addCell(Tag tag, String contactDetails) {
        TagCell tagCell = new TagCell(tag, contactDetails);
        cells.add(tagCell);

        cellMap.put(tagCell.getTag(), tagCell);

    }

    public void addEdge(Tag parentTag, Tag childTag) {

        Cell sourceCell = cellMap.get(parentTag);
        Cell targetCell = cellMap.get(childTag);

        Edge edge = new Edge( sourceCell, targetCell);

        edges.add(edge);
    }


}