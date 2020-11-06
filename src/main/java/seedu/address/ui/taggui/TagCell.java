package seedu.address.ui.taggui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import seedu.address.model.tag.Tag;

public class TagCell extends Cell {

    public static final double NODE_WIDTH = 200;
    public static final double NODE_HEIGHT = 50;

    public static final int LIMIT_CHARACTER = 50;

    public TagCell(Tag tag, String contactsDetail) {
        super(tag);

        VBox verticalBox = new VBox();
        verticalBox.setAlignment(Pos.CENTER);
        verticalBox.setMaxWidth(NODE_WIDTH);
        verticalBox.setMinWidth(NODE_WIDTH);
        verticalBox.setMaxHeight(NODE_HEIGHT);
        verticalBox.setMinHeight(NODE_HEIGHT);

        String text = tag.toString();

        Label upperHalf = createLabelBox(text, Color.CADETBLUE, NODE_HEIGHT / 2, NODE_WIDTH);
        Label lowerHalf = createLabelBox(contactsDetail, Color.SKYBLUE, NODE_HEIGHT / 2, NODE_WIDTH);

        verticalBox.getChildren().addAll(upperHalf, lowerHalf);

        setView(verticalBox);
    }

    private Label createLabelBox(String text, Color color, double height, double width) {
        Label label = new Label(text);
        label.setBackground(getColoredBackground(color));
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }

    private Background getColoredBackground(Color color) {
        CornerRadii cornerRadii = new CornerRadii(0);
        BackgroundFill fill = new BackgroundFill(color, cornerRadii, new Insets(0));
        return new Background(fill);
    }



}
