# jooq-utils
jOOQ utilities including a builder generator for use with [Immutables](https://immutables.github.io/) and
[MapStruct](https://mapstruct.org/), and JPA entity code generation.

MapStruct's built-in support for Immutables hinges on whether or not Immutables is found in the classpath.
As Immutables is needed only at code generation time, for most uses it will not be present in the classpath.

## JPA Code Generation
Switching to Postgres for JPA functionality, as the [Reverse Mapping Tool](https://openjpa.apache.org/builds/3.1.0/apache-openjpa/docs/ref_guide_pc_reverse.html) does not support H2.

```bash
docker run --rm --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 postgres:11.7 -c fsync=off &
docker run -it --rm --link some-postgres postgres:11.7 psql -h some-postgres -U postgres
```

```
drop database if exists sampledb;
create database sampledb;
```
