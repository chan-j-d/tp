package seedu.address.ui.taggui;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import seedu.address.model.tag.Tag;

//@@author Roland
//Reused from https://stackoverflow.com/questions/30679025/graph-visualisation-like-yfiles-in-javafx
public class Cell extends Pane {

    Tag tag;

    List<Cell> children = new ArrayList<>();
    List<Cell> parents = new ArrayList<>();

    Node view;

    public Cell(Tag tag) {
        this.tag = tag;
    }

    public void addCellChild(Cell cell) {
        children.add(cell);
    }

    public List<Cell> getCellChildren() {
        return children;
    }

    public void addCellParent(Cell cell) {
        parents.add(cell);
    }

    public List<Cell> getCellParents() {
        return parents;
    }

    public void removeCellChild(Cell cell) {
        children.remove(cell);
    }

    public void setView(Node view) {

        this.view = view;
        getChildren().add(view);

    }

    public Node getView() {
        return this.view;
    }

    public Tag getTag() {
        return tag;
    }
}
//@@author