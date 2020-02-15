package com.github.azell.jooq_utils.app;

import com.github.azell.jooq_utils.sample.data.tables.pojos.Person;
import com.github.azell.jooq_utils.sample.data.tables.records.PersonRecord;
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
}
