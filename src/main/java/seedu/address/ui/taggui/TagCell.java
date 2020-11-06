package seedu.address.ui.taggui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import seedu.address.model.tag.Tag;

public class TagCell extends Cell {

    public static final double NODE_WIDTH = 500;
    public static final double NODE_HEIGHT = 200;

    public TagCell(Tag tag, String contactsDetail) {
        super(tag);

        VBox verticalBox = new VBox();
        verticalBox.setMaxWidth(NODE_WIDTH);
        verticalBox.setMinWidth(NODE_WIDTH);
        verticalBox.setMaxHeight(NODE_HEIGHT);
        verticalBox.setMinHeight(NODE_HEIGHT);
        String text = tag.toString();
        Label upperHalf = new Label(text);
        Label lowerHalf = new Label(contactsDetail);

        setView(verticalBox);
    }



}
