package seedu.address.ui.taggui;

import javafx.scene.layout.Pane;
import seedu.address.model.Model;
import javafx.scene.Group;
        import javafx.scene.control.ScrollPane;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagManager;
import seedu.address.model.tag.TagTree;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TagGraph {

    public static final String MESSAGE_NO_CONTACTS = "No tagged contacts";
    public static final String MESSAGE_NUM_REMAINING_CONTACTS = "%s other contacts";

    private TreeModel model;

    private Group canvas;

    private ZoomableScrollPane scrollPane;

    /**
     * the pane wrapper is necessary or else the scrollpane would always align
     * the top-most and left-most child to the top and left eg when you drag the
     * top child down, the entire scrollpane would move down
     */
    //CellLayer cellLayer;

    public TagGraph() {

        this.model = new TreeModel();

        canvas = new Group();
        //cellLayer = new CellLayer();

        //canvas.getChildren().add(cellLayer);

        scrollPane = new ZoomableScrollPane(canvas);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        canvas.getChildren().add(scrollPane);
    }

    public void addTagTree(TagTree tagTree, TagManager manager) {
        Map<Tag, Set<Tag>> tagSubTagMap = tagTree.getTagSubTagMap();
        for (Map.Entry<Tag, Set<Tag>> entry : tagSubTagMap.entrySet()) {
            Tag key = entry.getKey();
            if (!model.containsTag(key)) {
                model.addCell(key, getSummaryOfTag(manager, key));
            }

            entry.getValue().forEach(tag -> {
                if (!model.containsTag(tag)) {
                    model.addCell(tag, getSummaryOfTag(manager, tag));
                }
                model.addEdge(key, tag);
            });
        }
    }

    private String getSummaryOfTag(TagManager manager, Tag tag) {
        Set<Person> personSet = manager.getPersonsUnderTag(tag);
        if (personSet.size() == 0) {
            return MESSAGE_NO_CONTACTS;
        }
        int fullCount = personSet.size();
        int currentCount = 0;
        String string = "";
        boolean first = true;
        for (Person person : personSet) {
            if (!first) {
                string = string + ", ";
                first = false;
            }

            if (string.length() + person.getName().toString().length() > TagCell.LIMIT_CHARACTER) {
                string = string + String.format(MESSAGE_NUM_REMAINING_CONTACTS, fullCount - currentCount);
                break;
            }

            string = string + person.getName().toString();
            currentCount++;
        }
        return string;
    }

    public void setCellLocation(Tag tag, double xCoordinate, double yCoordinate) {
        model.setCellLocation(tag, xCoordinate, yCoordinate);
    }

    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public Pane getCellLayer() {
        //return this.cellLayer;
        return null;
    }
    public TreeModel getModel() {
        return model;
    }


    public double getScale() {
        return this.scrollPane.getScaleValue();
    }
}