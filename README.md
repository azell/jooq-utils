# jooq-utils
jOOQ utilities including a builder generator for use with [Immutables](https://immutables.github.io/) and
[MapStruct](https://mapstruct.org/), and JPA entity code generation.

MapStruct's built-in support for Immutables hinges on whether or not Immutables is found in the classpath.
As Immutables is needed only at code generation time, for most uses it will not be present in the classpath.

See the **postgres** branch for JPA support.
