package io.github.danielalmeida19.orchestranode.database.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import io.github.danielalmeida19.orchestranode.database.Connection;
import io.github.danielalmeida19.orchestranode.database.MongoConnector;
import io.github.danielalmeida19.orchestranode.database.mapper.DocumentMapper;
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
        Collection newCollection = CollectionUtils.getCollectionByClass(clazz);

        if (newCollection == null) {

            throw new IllegalArgumentException(
                    "The class %s is not included in OrchestraNode auto manage. Impossible to use these DAO implementation"
                            .formatted(clazz.getName()));
        }

        connector = new MongoConnector(connection);
        mongoCollection = connector.getDatabase().getCollection(newCollection.getName());
        collection = newCollection;
        this.clazz = clazz;

    }

    /**
     * Method that use the collection pre setted in database using the class name
     * and save the instance of the managed Class in MongoDB
     */
    @Override
    public void save(T instance) {

        try {
            Document documentInstance = DocumentMapper.getDocumentFromCollectionClass(collection.getFields(), instance,
                    this.clazz);
            mongoCollection.insertOne(documentInstance);

        } catch (Exception e) {
            log.error("Some error ocurred in transform or save the document", e);
        }
    }

    /**
     * Method to find an T instance from String ID in a Collection in MongoDB
     */
    @Override
    public T findById(String id) {
        Document document = mongoCollection.find(Filters.eq("_id", new ObjectId(id))).first();
        log.debug(document.toJson());

        if (document != null) {
            try {
                T instance = DocumentMapper.getInstanceFromDocumentCollectionMapped(document, this.clazz,
                        this.collection.getFields());

                // log.debug(instance.toString());
                return instance;

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

    /**
     * Method to find and delete an instance by the ID in a collection in MongoDB
     */
    @Override
    public void deleteById(String id) {
        try {
            mongoCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Method to find all instances in database in the collection
     */
    @Override
    public List<T> findAll() {
        try {
            List<Document> documents = new ArrayList<>();

            // Serching in the database
            mongoCollection.find().into(documents);

            List<T> instances = new ArrayList<>();
            for (Document document : documents) {

                // Converting the documents to in memories instances
                instances.add(DocumentMapper.getInstanceFromDocumentCollectionMapped(document, clazz,
                        collection.getFields()));
            }

            return instances;

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * Method to drop the collection of the database to empty the database
     */
    public void drop() {
        mongoCollection.drop();
    }
}
