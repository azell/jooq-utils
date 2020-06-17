package com.github.azell.jooq_utils.app;

import com.github.azell.jooq_utils.sample.data.tables.pojos.Person;
import com.github.azell.jooq_utils.sample.data.tables.records.PersonRecord;
import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import java.time.LocalDate;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AppMapper {
  @Mapping(target = "id", source = "employeeId")
  Person toPerson(Employee value);

  @InheritInverseConfiguration
  Employee toEmployee(Person value);

  @Mapping(target = "id", source = "employeeId")
  PersonRecord toPersonRecord(Employee value);

  @InheritInverseConfiguration
  Employee toEmployee(PersonRecord value);

  @Mapping(target = "id", source = "employeeId")
  com.github.azell.jooq_utils.sample.entities.Person toPersonEntity(Employee value);

  @InheritInverseConfiguration
  Employee toEmployee(com.github.azell.jooq_utils.sample.entities.Person value);

  default com.vladmihalcea.hibernate.type.range.Range<LocalDate> map(Range<LocalDate> value) {
    if (value == null) {
      return null;
    } else if (value.hasLowerBound()) {
      var lower = value.lowerEndpoint();

      if (value.hasUpperBound()) {
        var upper = value.upperEndpoint();

        if (value.lowerBoundType() == BoundType.OPEN) {
          return value.upperBoundType() == BoundType.OPEN
              ? com.vladmihalcea.hibernate.type.range.Range.open(lower, upper)
              : com.vladmihalcea.hibernate.type.range.Range.openClosed(lower, upper);
        } else {
          return value.upperBoundType() == BoundType.OPEN
              ? com.vladmihalcea.hibernate.type.range.Range.closedOpen(lower, upper)
              : com.vladmihalcea.hibernate.type.range.Range.closed(lower, upper);
        }
      } else {
        return value.lowerBoundType() == BoundType.OPEN
            ? com.vladmihalcea.hibernate.type.range.Range.openInfinite(lower)
            : com.vladmihalcea.hibernate.type.range.Range.closedInfinite(lower);
      }
    } else if (value.hasUpperBound()) {
      var upper = value.upperEndpoint();

      return value.upperBoundType() == BoundType.OPEN
          ? com.vladmihalcea.hibernate.type.range.Range.infiniteOpen(upper)
          : com.vladmihalcea.hibernate.type.range.Range.infiniteClosed(upper);
    } else {
      return com.vladmihalcea.hibernate.type.range.Range.infinite(LocalDate.class);
    }
  }

  default Range<LocalDate> map(com.vladmihalcea.hibernate.type.range.Range<LocalDate> value) {
    if (value == null) {
      return null;
    } else if (value.hasLowerBound()) {
      var lower = value.lower();

      if (value.hasUpperBound()) {
        var upper = value.upper();

        if (!value.contains(lower)) {
          return !value.contains(upper) ? Range.open(lower, upper) : Range.openClosed(lower, upper);
        } else {
          return !value.contains(upper)
              ? Range.closedOpen(lower, upper)
              : Range.closed(lower, upper);
        }
      } else {
        return !value.contains(lower) ? Range.greaterThan(lower) : Range.atLeast(lower);
      }
    } else if (value.hasUpperBound()) {
      var upper = value.upper();

      return !value.contains(upper) ? Range.lessThan(upper) : Range.atMost(upper);
    } else {
      return Range.all();
    }
  }
}
