package com.github.azell.jooq_utils.app;

import com.github.azell.jooq_utils.sample.data.tables.pojos.Person;
import org.mapstruct.factory.Mappers;

public class App {
  public static void main(String[] args) {
    var person = Person.builder().id(null).firstName("Mickey").lastName("Mouse").build();

    System.out.println(person);

    var employee =
        ImmutableEmployee.builder().employeeId(123).firstName("Donald").lastName("Duck").build();

    System.out.println(employee);

    var mapper = Mappers.getMapper(AppMapper.class);

    System.out.println(mapper.toPerson(employee));
    System.out.println(mapper.toPersonRecord(employee));
  }
}
