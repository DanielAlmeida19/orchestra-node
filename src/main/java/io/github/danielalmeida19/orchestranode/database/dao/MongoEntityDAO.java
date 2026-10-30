package io.github.danielalmeida19.orchestranode.database.dao;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.github.danielalmeida19.orchestranode.database.Connection;
import io.github.danielalmeida19.orchestranode.database.MongoConnector;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.Collection;
import io.github.danielalmeida19.orchestranode.utils.CollectionUtils;

/**
 * Class that represents a generic repository to CRUD operations of any Entities
 * to MongoDB
 *
 * @author Daniel
 */
public class MongoEntityDAO<T> implements GenericDAO<String, T> {

    private static final Logger log = LoggerFactory.getLogger(MongoEntityDAO.class);

    private MongoConnector connector;
    private Collection collection;
    private final MongoCollection<Document> mongoCollection;
    private Class<T> clazz;

    public MongoEntityDAO(Connection connection, Class<T> clazz) {
        Collection newCollection = CollectionUtils.getCollectionByName(clazz.getName());

        if (newCollection == null) {

            throw new IllegalArgumentException(
                    "The class %s is not included in OrchestraNode auto manage. Impossible to use these DAO implementation"
                            .formatted(clazz.getName()));
        }

        connector = new MongoConnector(connection);
        mongoCollection = connector.getDatabase().getCollection(clazz.getSimpleName());
        collection = newCollection;
        this.clazz = clazz;

    }

    /**
     * Methor that use the collection pre setted in database using the class name
     * and save the instance of the managed Class in MongoDB
     */
    @Override
    public void save(T instance) {

        try {
            Document documentInstance = CollectionUtils.getDocumentFromCollectionClass(collection.getFields(), instance,
                    this.clazz);
            mongoCollection.insertOne(documentInstance);

        } catch (Exception e) {
            log.error("Some error ocurred in transform or save the document", e);
        }
    }

    @Override
    public T findById(String id) {
        // TODO: Auto-generated method stub
        return null;
    }

    @Override
    public void deleteById(String id) {
        // TODO: Auto-generated method stub
    }
}
