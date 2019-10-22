package com.github.azell.jooq_utils.sample;

import com.github.azell.jooq_utils.sample.data.tables.interfaces.IPerson;
import com.github.azell.jooq_utils.sample.data.tables.pojos.PersonBuilder;

public class App {
  public static void main(String[] args) {
    IPerson person = new PersonBuilder()
      .id(123)
      .firstName("Mickey")
      .lastName("Mouse")
      .build();

    System.out.println(person);
  }
}
