package com.github.azell.jooq_utils.app;

import java.util.List;
import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

@Value.Immutable
@Value.Style(get = {"get*", "is*"})
public interface Employee {
  @Nullable
  Integer getEmployeeId();

  String getFirstName();

  String getLastName();

  List<String> getNicknames();
}
