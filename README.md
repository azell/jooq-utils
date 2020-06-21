# jooq-utils
Support for the [Postgres range type](https://www.postgresql.org/docs/devel/rangetypes.html).

```bash
docker run --rm --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 postgres:11.8 -c fsync=off &
docker run -it --rm --link some-postgres postgres:11.8 psql -h some-postgres -U postgres
```

```
drop database if exists sampledb;
create database sampledb;
```
