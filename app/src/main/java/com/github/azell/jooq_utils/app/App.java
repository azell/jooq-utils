package com.github.azell.jooq_utils.app;

import static com.github.azell.jooq_utils.sample.data.Tables.PERSON;

import com.github.azell.jooq_utils.sample.data.tables.records.PersonRecord;
import com.google.common.collect.Range;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
  private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @SuppressWarnings("deprecation")
  public static void main(String[] args) throws SQLException {
    var userName = "postgres";
    var password = "mysecretpassword";
    var url = "jdbc:postgresql://localhost/sampledb"; // ?loggerLevel=DEBUG

    try (var conn = DriverManager.getConnection(url, userName, password)) {
      var app = new App();
      var person = new PersonRecord();

      person.setFirstName("Mickey");
      person.setLastName("Mouse");
      person.setAges(Range.atLeast(LocalDate.of(1928, Month.NOVEMBER, 18)));
      person.setAgesInt4(Range.lessThan(2000));
      person.setAgesInt8(Range.openClosed(1L, 123456789L));
      person.setAgesNum(Range.closedOpen(BigDecimal.ONE, new BigDecimal("10.0123456789")));

      var lhs = LocalDateTime.of(1984, Month.SEPTEMBER, 2, 6, 30, 15, 999_999_999);
      var rhs = LocalDateTime.of(2000, Month.JANUARY, 1, 14, 15, 16);

      person.setAgesTs(Range.closed(lhs, rhs));

      var tz = ZoneId.of("America/Los_Angeles");

      person.setAgesTstz(Range.open(ZonedDateTime.of(lhs, tz), ZonedDateTime.of(rhs, tz)));

      app.jooq(conn, List.of(person), LocalDate.of(1999, Month.DECEMBER, 31));
    }
  }

  private void jooq(Connection conn, List<PersonRecord> people, LocalDate date) {
    logger.info("people: {}", people);

    var create = DSL.using(conn, SQLDialect.POSTGRES);
    create.batchInsert(people).execute();

    var result = create.selectFrom(PERSON).where(rangeContainsElem(PERSON.AGES, date)).fetch();
    logger.info("Query: {}", result);
  }

  private static Condition rangeContainsElem(Field<Range<LocalDate>> field, LocalDate date) {
    return DSL.condition("{0} @> {1}", field, DSL.val(date));
  }
}
