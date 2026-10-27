package io.github.danielalmeida19.orchestranode.database.dao;

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

    // private static final Logger log =
    // LoggerFactory.getLogger(MongoEntityDAO.class);

    private MongoConnector connector;
    private Collection collection;

    public MongoEntityDAO(Connection connection, Class<T> clazz) {
        Collection newCollection = CollectionUtils.getCollectionByName(clazz.getName());

        if (newCollection == null) {

            throw new IllegalArgumentException(
                    "The class %s is not included in OrchestraNode auto manage. Impossible to use these DAO implementation"
                            .formatted(clazz.getName()));
        }

        connector = new MongoConnector(connection);
        collection = newCollection;

    }

    @Override
    public void save(T instance) {

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
