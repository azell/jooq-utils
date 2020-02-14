package com.github.azell.jooq_utils.app;

import com.github.azell.jooq_utils.sample.data.tables.pojos.Person;

public class App {
  public static void main(String[] args) {
    var person = Person.builder().id(null).firstName("Mickey").lastName("Mouse").build();

    System.out.println(person);
  }
}
