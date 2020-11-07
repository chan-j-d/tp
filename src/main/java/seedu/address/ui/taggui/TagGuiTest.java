package seedu.address.ui.taggui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
        start(stage);
        graph = new TagGraph();
        this.tagTree = tagTree;
        this.addressBook = addressBook;
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

        ContactTagIntegrationManager manager = buildTestContactTagIntegrationManager();
        graph.addTagTree(manager.getTagTree(), manager.getAddressBook());
        TagTree tagTree = manager.getTagTree();

        root.setCenter(graph.getScrollPane());

        Scene scene = new Scene(root, 1024, 768);

        primaryStage.setScene(scene);
    }

    public static final Tag TAG_NUS = new Tag("nus");
    public static final Set<Tag> SET_NUS = new HashSet<>(Set.of(TAG_NUS));

    public static final Tag TAG_COMPUTING = new Tag("computing");
    public static final Tag TAG_SCIENCE = new Tag("science");
    public static final Tag TAG_ARCHITECTURE = new Tag("architecture");
    public static final Set<Tag> SET_FACULTIES = new HashSet<>(Set.of(TAG_COMPUTING, TAG_SCIENCE, TAG_ARCHITECTURE));
    public static final Set<Tag> SET_SCIENCE_COMP_SUPERTAGS = new HashSet<>(Set.of(TAG_SCIENCE, TAG_COMPUTING));

    public static final Tag TAG_SCIENCE_COMP = new Tag("sciencecomp");
    public static final Set<Tag> SET_SCIENCE_COMP = new HashSet<>(Set.of(TAG_SCIENCE_COMP));

    public static final Tag TAG_MA1101R = new Tag("ma1101r");
    public static final Tag TAG_CS1231S = new Tag("cs1231s");
    public static final Set<Tag> SET_MODULES = new HashSet<>(Set.of(TAG_MA1101R, TAG_CS1231S));

    public static final Tag TAG_NOT_IN_TREE = new Tag("notintree");
    public static final Tag TAG_CS2040S_NOT_TREE = new Tag("cs2040s");

    /**
     * Returns a sample tag tree for testing.
     */
    public static TagTreeImpl buildTestTree() {
        Map<Tag, Set<Tag>> mapSubTag = new HashMap<>();
        Map<Tag, Set<Tag>> mapSuperTag = new HashMap<>();

        mapSubTag.put(TAG_NUS, new HashSet<>(SET_FACULTIES));

        mapSubTag.put(TAG_COMPUTING, new HashSet<>(SET_SCIENCE_COMP));
        mapSubTag.put(TAG_SCIENCE, new HashSet<>(SET_SCIENCE_COMP));

        mapSubTag.put(TAG_SCIENCE_COMP, new HashSet<>(SET_MODULES));

        mapSuperTag.put(TAG_CS1231S, new HashSet<>(SET_SCIENCE_COMP));
        mapSuperTag.put(TAG_MA1101R, new HashSet<>(SET_SCIENCE_COMP));

        mapSuperTag.put(TAG_SCIENCE_COMP, new HashSet<>(SET_SCIENCE_COMP_SUPERTAGS));

        mapSuperTag.put(TAG_ARCHITECTURE, new HashSet<>(SET_NUS));
        mapSuperTag.put(TAG_COMPUTING, new HashSet<>(SET_NUS));
        mapSuperTag.put(TAG_SCIENCE, new HashSet<>(SET_NUS));

        return new TagTreeImpl(new HashMap<>(mapSubTag), new HashMap<>(mapSuperTag));
    }
    public static final Person PERSON_CS1231S_1 = new PersonBuilder()
            .withName("person1")
            .withTags("cs1231s").build();
    public static final Person PERSON_CS1231S_2 = new PersonBuilder()
            .withName("person2")
            .withTags("cs1231s").build();
    public static final Person PERSON_MA1101R = new PersonBuilder()
            .withName("person3")
            .withTags("ma1101r").build();
    public static final Person PERSON_SCIENCECOMP = new PersonBuilder()
            .withName("person4")
            .withTags("sciencecomp").build();
    public static final Person PERSON_COMPUTING = new PersonBuilder()
            .withName("person5")
            .withTags("computing").build();
    public static final Person PERSON_COMPUTING_SCIENCE = new PersonBuilder()
            .withName("person6")
            .withTags("computing", "science").build();
    public static final Person PERSON_CS2040S_NOT_IN = new PersonBuilder()
            .withName("person7")
            .withTags("CS2040S").build();
    public static final List<Person> TEST_PERSONS = List.of(PERSON_CS1231S_1, PERSON_CS1231S_2,
            PERSON_MA1101R, PERSON_SCIENCECOMP, PERSON_COMPUTING, PERSON_COMPUTING_SCIENCE);

    public static AddressBook buildTestIntegrationAddressBook() {
        AddressBook addressBook = new AddressBook();
        addressBook.setPersons(TEST_PERSONS);
        return addressBook;
    }

    public static ContactTagIntegrationManager buildTestContactTagIntegrationManager() {
        return new ContactTagIntegrationManager(buildTestIntegrationAddressBook(), buildTestTree());
    }
    /**
     * A utility class to help with building Person objects.
     */
    public static class PersonBuilder {

        public static final String DEFAULT_NAME = "Alice Pauline";
        public static final String DEFAULT_PHONE = "85355255";
        public static final String DEFAULT_EMAIL = "alice@gmail.com";
        public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        /**
         * Creates a {@code PersonBuilder} with the default details.
         */
        public PersonBuilder() {
            name = new Name(DEFAULT_NAME);
            phone = new Phone(DEFAULT_PHONE);
            email = new Email(DEFAULT_EMAIL);
            address = new Address(DEFAULT_ADDRESS);
            tags = new HashSet<>();
        }

        /**
         * Initializes the PersonBuilder with the data of {@code personToCopy}.
         */
        public PersonBuilder(Person personToCopy) {
            name = personToCopy.getName();
            phone = personToCopy.getPhone();
            email = personToCopy.getEmail();
            address = personToCopy.getAddress();
            tags = new HashSet<>(personToCopy.getTags());
        }

        /**
         * Sets the {@code Name} of the {@code Person} that we are building.
         */
        public PersonBuilder withName(String name) {
            this.name = new Name(name);
            return this;
        }

        /**
         * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
         */
        public PersonBuilder withTags(String ... tags) {
            this.tags = SampleDataUtil.getTagSet(tags);
            return this;
        }

        /**
         * Sets the {@code Address} of the {@code Person} that we are building.
         */
        public PersonBuilder withAddress(String address) {
            this.address = new Address(address);
            return this;
        }

        /**
         * Sets the {@code Phone} of the {@code Person} that we are building.
         */
        public PersonBuilder withPhone(String phone) {
            this.phone = new Phone(phone);
            return this;
        }

        /**
         * Sets the {@code Email} of the {@code Person} that we are building.
         */
        public PersonBuilder withEmail(String email) {
            this.email = new Email(email);
            return this;
        }

        public Person build() {
            return new Person(name, phone, email, address, tags);
        }

    }

}