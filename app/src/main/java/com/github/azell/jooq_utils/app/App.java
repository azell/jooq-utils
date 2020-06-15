package com.github.azell.jooq_utils.app;

import static com.github.azell.jooq_utils.sample.data.Tables.PERSON;

import com.github.azell.jooq_utils.sample.data.tables.pojos.Person;
import com.google.common.collect.Range;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.jpa.TypedParameterValue;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
  private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private final AppMapper mapper = Mappers.getMapper(AppMapper.class);
  private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("app");

  public static void main(String[] args) throws SQLException {
    var userName = "postgres";
    var password = "mysecretpassword";
    var url = "jdbc:postgresql://localhost/sampledb"; // ?loggerLevel=DEBUG

    try (var conn = DriverManager.getConnection(url, userName, password)) {
      var app = new App();

      var person =
          Person.builder()
              .id(null)
              .firstName("Mickey")
              .lastName("Mouse")
              .nicknames("Bob Cratchit", "King Mickey")
              .build();

      app.jooq(conn, List.of(person), "King Mickey");

      var employee =
          ImmutableEmployee.builder()
              .employeeId(null)
              .firstName("Donald")
              .lastName("Duck")
              .nicknames(List.of("Maui Mallard", "Frank Duck", "Fred"))
              .ages(Range.atLeast(LocalDate.of(1934, Month.JUNE, 9)))
              .build();

      app.hibernate(List.of(employee), "Fred");
    }
  }

  private void hibernate(List<Employee> employees, String nickName) {
    logger.info("employees: {}", employees);

    var em = factory.createEntityManager();
    var txn = em.getTransaction();

    txn.begin();
    employees.stream().map(mapper::toPersonEntity).forEach(em::persist);
    txn.commit();

    var cls = com.github.azell.jooq_utils.sample.entities.Person.class;
    var result =
        em.createNativeQuery("select p.* from Person p where p.nicknames @> :arrayValues", cls)
            .setParameter(
                "arrayValues",
                new TypedParameterValue(StringArrayType.INSTANCE, new String[] {nickName}))
            .getResultList();

    result.stream()
        .map(o -> mapper.toEmployee(cls.cast(o)))
        .forEach(e -> logger.info("employee: {}", e));
  }

  private void jooq(Connection conn, List<Person> people, String nickName) {
    logger.info("people: {}", people);

    var create = DSL.using(conn, SQLDialect.POSTGRES);
    var records =
        people.stream()
            .map(person -> create.newRecord(PERSON, person))
            .collect(Collectors.toList());

    // create.batchInsert(records).execute();

    var result =
        create
            .selectFrom(PERSON)
            .where(PERSON.NICKNAMES.contains(DSL.cast(DSL.array(nickName), PERSON.NICKNAMES)))
            .fetch();

    logger.info("Query: {}", result);
  }
}
