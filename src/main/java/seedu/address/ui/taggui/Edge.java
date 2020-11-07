package seedu.address.ui.taggui;
import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.util.List;

public class Edge extends Group {

    private static final double ARROW_ANGLE = 0.4;
    private static final double ARROW_LINE_LENGTH = 5;

    protected Cell source;
    protected Cell target;

    Line line;

    public Edge(Cell source, Cell target) {

        this.source = source;
        this.target = target;

        source.addCellChild(target);
        target.addCellParent(source);

        line = new Line();

        line.startXProperty().bind(source.layoutXProperty().add(TagCell.NODE_WIDTH / 2.0));
        line.startYProperty().bind(source.layoutYProperty().add(TagCell.NODE_HEIGHT));

        line.endXProperty().bind(target.layoutXProperty().add(TagCell.NODE_WIDTH / 2.0));
        line.endYProperty().bind(target.layoutYProperty());

        getChildren().add(line);
        getChildren().addAll(createArrowLines());

    }

    public List<Line> createArrowLines() {
        Line arrow1 = new Line();
        Line arrow2 = new Line();

        double startX = line.getStartX();
        double startY = line.getStartY();
        double endX = line.getEndX();
        double endY = line.getEndY();

        arrow1.setEndX(endX);
        arrow1.setEndY(endY);
        arrow2.setEndX(endX);
        arrow2.setEndY(endY);

        if (endX == startX && endY == startY) {
            // arrow parts of length 0
            arrow1.setStartX(endX);
            arrow1.setStartY(endY);
            arrow2.setStartX(endX);
            arrow2.setStartY(endY);
        } else {
            double lineLength = Math.sqrt((endX - startX) * (endX - startX) + (endY - startY) * (endY - startY));
            double dX = (startX - endX) * ARROW_LINE_LENGTH / lineLength;
            double dY = (startY - endY) * ARROW_LINE_LENGTH / lineLength;

            double sinTheta = Math.sin(ARROW_ANGLE);
            double cosTheta = Math.cos(ARROW_ANGLE);

            arrow1.setStartX(dX * cosTheta - dY * sinTheta + endX);
            arrow1.setStartY(dX * sinTheta + dY * cosTheta + endY);

            sinTheta = Math.sin(-ARROW_ANGLE);
            cosTheta = Math.cos(-ARROW_ANGLE);
            arrow2.setStartX(dX * cosTheta - dY * sinTheta + endX);
            arrow2.setStartY(dX * sinTheta + dY * cosTheta + endY);
        }
        return List.of(arrow1, arrow2);
    }

    public Cell getSource() {
        return source;
    }

    public Cell getTarget() {
        return target;
    }


}