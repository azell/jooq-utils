package com.github.azell.jooq_utils.app;

import com.google.common.collect.Range;
import java.time.LocalDate;
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

  @Nullable
  List<String> getNicknames();

  @Nullable
  Range<LocalDate> getAges();
}
