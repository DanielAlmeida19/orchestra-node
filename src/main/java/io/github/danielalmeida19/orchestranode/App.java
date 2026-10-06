package io.github.danielalmeida19.orchestranode;

import java.util.List;

import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.Collection;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.ObjectField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.StringField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodeldata.ArrayValue;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodeldata.BooleanValue;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodeldata.ObjectValue;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodeldata.Value;

/**
 * A simple test
 *
 */
public class App {
    public static void main(String[] args) {
        // Criando a estrutura JSON
        // Value pessoa = new ObjectValue()
        // .put("nome", "Maria")
        // .put("idade", 28)
        // .put("ativo", new BooleanValue(true))
        // .put("endereco", new ObjectValue()
        // .put("cidade", "São Paulo")
        // .put("uf", "SP"))
        // .put("habilidades", new ArrayValue()
        // .add("Java")
        // .add("Spring Boot"));
        //
        // // 1. Exportando para JSON String
        // System.out.println(pessoa.toValueString());

        Collection collection = new Collection();
        collection.setName("pessoa");

        ObjectField body = new ObjectField();
        body.setValue(List.of(
                new StringField("nome", "Maria")));

        System.out.println(body);
    }
}
