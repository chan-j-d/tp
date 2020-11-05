package seedu.address.ui.taggui;

import javafx.scene.layout.Pane;
import seedu.address.model.Model;
import javafx.scene.Group;
        import javafx.scene.control.ScrollPane;

public class TagGraph {

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

    public void beginUpdate() {
    }

    public void endUpdate() {
        // add components to graph pane
        getCellLayer().getChildren().addAll(model.getAddedEdges());
        getCellLayer().getChildren().addAll(model.getAddedCells());

        // remove components from graph pane
        getCellLayer().getChildren().removeAll(model.getRemovedCells());
        getCellLayer().getChildren().removeAll(model.getRemovedEdges());

        // every cell must have a parent, if it doesn't, then the graphParent is
        // the parent
        getModel().attachOrphansToGraphParent(model.getAddedCells());

        // remove reference to graphParent
        getModel().disconnectFromGraphParent(model.getRemovedCells());

        // merge added & removed cells with all cells
        getModel().merge();

    }

    public double getScale() {
        return this.scrollPane.getScaleValue();
    }
}