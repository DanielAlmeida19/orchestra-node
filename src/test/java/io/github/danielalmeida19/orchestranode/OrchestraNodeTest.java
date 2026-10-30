package io.github.danielalmeida19.orchestranode;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import io.github.danielalmeida19.orchestranode.annotations.PriorityLevel;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.BooleanField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.Collection;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.ListField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.NumberField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.ObjectField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.StringField;

public class OrchestraNodeTest {

    @Test
    public void contextLoads() {

    }

    // @Test
    // public void isCollectionsExtracting() {
    // List<Collection> collections = OrchestraNode
    // .extractCollections("io.github.danielalmeida19.orchestranode.model");
    //
    // Collection expectedPerson = new Collection();
    // expectedPerson.setName("Persons");
    // expectedPerson.setPriorityLevel(PriorityLevel.NORMAL);
    // expectedPerson.setFields(List.of(
    // new StringField("name", "", false, true),
    // new NumberField("age", "", true, true),
    // new BooleanField("active", "", true, true)));
    //
    // assertEquals(expectedPerson, collections.getFirst());
    // }

    @Test
    public void isComplexCollectionExtracting() {

        List<Collection> collections = OrchestraNode
                .extractCollections("io.github.danielalmeida19.orchestranode.model");

        Collection expectedPerson = new Collection();
        expectedPerson.setName("Persons");
        expectedPerson.setPriorityLevel(PriorityLevel.NORMAL);
        expectedPerson.setFields(List.of(
                new StringField("name", "", false, true),
                new StringField("surname", "", true, true),
                new ObjectField("address", List.of(
                        new StringField("street", "", true, true),
                        new NumberField("number", "", true, true)),
                        "", true, true),
                new BooleanField("active"),
                new ListField<String>("habilities", "", true, true, String.class)));

        System.out.println(collections.getFirst());
        assertEquals(expectedPerson, collections.getFirst());

    }
}
