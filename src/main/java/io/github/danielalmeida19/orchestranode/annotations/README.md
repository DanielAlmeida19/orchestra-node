# Annotations Module

This module serves as an annotation declarer to enable the creation of modifications that the _OrchestraNode_ accepts in its class composition. In this way, it is possible to define which mapped attributes and have the _OrchestraNode_ recognize the difference between the mapped schemas when there are changes.

***

## Class Level

***

# `@Collection`

Defines a class as a collection of data, where the structural metamodel will store the schema of an instance.

| Field | Meaning | Default Value |
| --- | --- | --- |
| `name` | The name that will be mapped from the class to the data collection | Original class name |
| `migration_priority` | Level of priority that the _OrchestraNode_ should give to the data collection during a migration | PriorityLevel.NORMAL |


***

## Attribute Level

# `@Field`

Defines a non-static attribute as a field within a collection (or nested within a field), allowing it to be part of the structural metamodel of a class.

| Field | Meaning | Default Value |
| --- | --- | --- |
| `name` | The name of the field mapped from the data structure | Original attribute name |
| `external` | External collection that the attribute references (will cause an error if it does not point to an existing collection) | `null` |
| `nullable` | Whether it is allowed for the attribute to contain empty values (application-level check!!!) | `true` |
| `ignorable` | Whether it is allowed for the attribute to not be explicitly present in the data structure | `true` |
