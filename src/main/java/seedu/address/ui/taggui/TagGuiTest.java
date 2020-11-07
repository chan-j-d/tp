package seedu.address.ui.taggui;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.model.AddressBook;
import seedu.address.model.ContactTagIntegrationManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.ReadOnlyTagTree;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagTree;
import seedu.address.model.tag.TagTreeImpl;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.ui.UiPart;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TagGuiTest {

    private final TagGraph graph;
    private final Stage stage;
    private final ReadOnlyTagTree tagTree;
    private final ReadOnlyAddressBook addressBook;

    public TagGuiTest(ReadOnlyTagTree tagTree, ReadOnlyAddressBook addressBook) {
        stage = new Stage();
        graph = new TagGraph();
        this.tagTree = tagTree;
        this.addressBook = addressBook;
        start(stage);
        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                hide();
            }
        });

    }

    private void updateGraph() {
        graph.addTagTree(tagTree, addressBook);
        Map<Integer, Set<Tag>> levelTagMap =
                GraphLevelAlgorithm.calculateLevels(tagTree.getTagSubTagMap(), tagTree.getTagSuperTagMap());
        Layout layout = new GraphLayout(graph, levelTagMap);
        layout.execute();
        graph.addEdges(tagTree);
    }

    public void show() {
        updateGraph();
        stage.show();
    }

    public void focus() {
        updateGraph();
        stage.requestFocus();
    }

    public void hide() {
        stage.hide();
    }

    public boolean isShowing() {
        return stage.isShowing();
    }


    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        root.setCenter(graph.getScrollPane());

        Scene scene = new Scene(root, 1024, 768);


        primaryStage.setScene(scene);
    }

}