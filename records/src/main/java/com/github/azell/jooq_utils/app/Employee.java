package com.github.azell.jooq_utils.app;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(get = {"get*", "is*"})
public interface Employee {
  Integer getEmployeeId();

  String getFirstName();

  String getLastName();
}
