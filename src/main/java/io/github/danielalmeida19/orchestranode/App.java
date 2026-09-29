package io.github.danielalmeida19.orchestranode;

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
        Value pessoa = new ObjectValue()
                .put("nome", "Maria")
                .put("idade", 28)
                .put("ativo", new BooleanValue(true))
                .put("endereco", new ObjectValue()
                        .put("cidade", "São Paulo")
                        .put("uf", "SP"))
                .put("habilidades", new ArrayValue()
                        .add("Java")
                        .add("Spring Boot"));

        // 1. Exportando para JSON String
        System.out.println(pessoa.toValueString());
    }
}
