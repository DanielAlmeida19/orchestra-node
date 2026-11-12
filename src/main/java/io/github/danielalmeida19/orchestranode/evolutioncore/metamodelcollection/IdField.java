package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

import io.github.danielalmeida19.orchestranode.annotations.FillingPolicy;

/**
 * Class to identify an Field like an Id of a mapped collection
 *
 * @author Daniel
 */
public class IdField<T> extends CollectionField<T> {

    private FillingPolicy fillingPolicy;

    public IdField(String name, FillingPolicy fillingPolicy) {
        super(name, null, false, false);
        this.fillingPolicy = fillingPolicy;
    }

    public FillingPolicy getFillingPolicy() {
        return fillingPolicy;
    }

    public void setFillingPolicy(FillingPolicy fillingPolicy) {
        this.fillingPolicy = fillingPolicy;
    }
}
