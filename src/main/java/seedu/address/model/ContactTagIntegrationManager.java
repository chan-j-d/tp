package seedu.address.model;

import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.tag.ReadOnlyTagTree;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagTree;
import seedu.address.model.tag.TagTreeImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Class that controls {@code Tag} relationships and interactions between {@code Tag}s and {@Code Person}s.
 */
public class ContactTagIntegrationManager {

    private AddressBook addressBook;
    private TagTree tagTree;

    public ContactTagIntegrationManager(AddressBook addressBook) {
        this.addressBook = addressBook;
        tagTree = new TagTreeImpl();
    }

    public ContactTagIntegrationManager(AddressBook addressBook, ReadOnlyTagTree tagTree) {
        this(addressBook);
        this.tagTree.copy(tagTree);
    }

    /**
     * Returns a {@code set} or {@code person}s that falls under the given {@code tag} and all its sub-tags.
     *
     * @param tag {@code tag} to begin search for matching {@code person}s.
     * @return Set of Persons falling under either the argument {@code tag} or its sub-tags.
     */
    public Set<Person> getAllPersonsUnderTag(Tag tag) {
        Set<Person> finalSet = new HashSet<>();
        BiConsumer<Set<Person>, Tag> biConsumer = new BiConsumer<Set<Person>, Tag>() {
            @Override
            public void accept(Set<Person> personSet, Tag tag) {
                personSet.addAll(addressBook.getPersonsWithTag(tag));
                tagTree.getSubTagsOf(tag).stream().forEach(subTag -> accept(finalSet, subTag));
            }
        };
        return finalSet;
    }

    /**
     * Edits the sub-tags of a given {@code supertag}.
     * If a sub-tag to add is already present, no change will be performed.
     * If a sub-tag to be removed is not present, no change will be performed.
     *
     * @param superTag {@code tag} for which the sub-tags will be edited.
     * @param subTagsToAdd Set of sub-{@code tag}s to be added.
     * @param subTagsToRemove Set of sub-{@code tag}s to be removed.
     */
    public void editSubTagsOf(Tag superTag, Set<Tag> subTagsToAdd, Set<Tag> subTagsToRemove) {
        tagTree.removeSubTagsFrom(superTag, subTagsToRemove);
        tagTree.addSubTagsTo(superTag, subTagsToAdd);
    }

    /**
     * Deletes a {@code tag} from the existing tag tree.
     * {@code Person}s with the deleted {@code tag} will have the {@code tag} removed.
     *
     * @param tag {@code tag} to be deleted.
     */
    public void deleteTag(Tag tag) {
        tagTree.deleteTag(tag);
        removeTagFromContactsInAddressBook(tag);
    }

    /**
     * Replaces each instance of a {@code person} with {@code tag} with a {@code person} without it.
     */
    private void removeTagFromContactsInAddressBook(Tag tag) {
        addressBook.getPersonsWithTag(tag).stream()
                .forEach(person -> addressBook.setPerson(person, copyPersonWithoutTag(person, tag)));
    }

    /**
     * Deletes {@code tag} and all its sub-{@code tag}s directly below {@code tag} in the tag hierarchy.
     * For example, if {@code CS2030 -> COMPUTING -> NUS}, deleting NUS with this method
     * will delete {@code CS2030} and {@code COMPUTING}.
     * Affected {@code Person}s will be updated accordingly.
     *
     * @param tag {@code tag} to begin the deletion.
     */
    public void deleteTagRecursive(Tag tag) {
        removeTagFromContactsInAddressBook(tag);
        tagTree.getSubTagsOf(tag).stream().forEach(subTag -> deleteTagRecursive(subTag));
        tagTree.deleteTagAndAllSubTags(tag);
    }

    /**
     * Deletes {@code tag} and all {@code person} objects directly assigned to it.
     *
     * @param tag {@code tag} to be deleted together with the {@code person}s directly under this {@code tag}.
     */
    public void deleteTagAndDirectContacts(Tag tag) {
        tagTree.deleteTag(tag);
        removeContactsUnderTagFromAddressBook(tag);
    }

    /**
     * Removes all {@code person}s directly under {@code tag} from the {@code addressbook}.
     */
    private void removeContactsUnderTagFromAddressBook(Tag tag) {
        addressBook.getPersonsWithTag(tag).stream()
                .forEach(person -> addressBook.removePerson(person));
    }

    /**
     * Deletes {@code tag} and all its sub-{@code tag}s directly below {@code tag} in the tag hierarchy.
     * All {@code person}s under any deleted {@code tag} will also be deleted.
     *
     * @param tag {@code tag} to begin recursive deletion.
     */
    public void deleteTagAndDirectContactsRecursive(Tag tag) {
        removeContactsUnderTagFromAddressBook(tag);
        tagTree.getSubTagsOf(tag).stream().forEach(subTag -> deleteTagAndDirectContactsRecursive(tag));
        tagTree.deleteTagAndAllSubTags(tag);
    }

    /**
     * returns a new {@code person} object which is nearly identical to {@code persontocopy} except with
     * {@code tagRemoved} removed.
     */
    private Person copyPersonWithoutTag(Person personToCopy, Tag tagRemoved) {
        HashSet<Tag> newTagSet = new HashSet<>(personToCopy.getTags());
        newTagSet.remove(tagRemoved);
        return new Person(personToCopy.getName(),
                personToCopy.getPhone(),
                personToCopy.getEmail(),
                personToCopy.getAddress(),
                newTagSet);
    }

    /**
     * returns a new {@code person} object which is nearly identical to {@code persontocopy} except with
     * {@code tagAdded} added.
     */
    private Person copyPersonWithTag(Person personToCopy, Tag tagAdded) {
        HashSet<Tag> newTagSet = new HashSet<>(personToCopy.getTags());
        newTagSet.add(tagAdded);
        return new Person(personToCopy.getName(),
                personToCopy.getPhone(),
                personToCopy.getEmail(),
                personToCopy.getAddress(),
                newTagSet);
    }

}