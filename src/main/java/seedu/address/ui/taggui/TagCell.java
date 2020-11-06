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
        Label upperHalf = new Label(text);
        upperHalf.setTextAlignment(TextAlignment.CENTER);
        upperHalf.setBackground(getColoredBackground(Color.CADETBLUE));
        Label lowerHalf = new Label(contactsDetail);
        lowerHalf.setTextAlignment(TextAlignment.CENTER);
        lowerHalf.setBackground(getColoredBackground(Color.SKYBLUE));
        verticalBox.getChildren().addAll(upperHalf, lowerHalf);

        setView(verticalBox);
    }

    private Background getColoredBackground(Color color) {
        CornerRadii cornerRadii = new CornerRadii(0);
        BackgroundFill fill = new BackgroundFill(color, cornerRadii, new Insets(0));
        return new Background(fill);

    }



}
