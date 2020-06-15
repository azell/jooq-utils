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
  @Mapping(target = "ages", ignore = true)
  Employee toEmployee(Person value);

  @Mapping(target = "id", source = "employeeId")
  PersonRecord toPersonRecord(Employee value);

  @InheritInverseConfiguration
  @Mapping(target = "ages", ignore = true)
  Employee toEmployee(PersonRecord value);

  @Mapping(target = "id", source = "employeeId")
  @Mapping(target = "ages", ignore = true)
  com.github.azell.jooq_utils.sample.entities.Person toPersonEntity(Employee value);

  @InheritInverseConfiguration
  @Mapping(target = "ages", ignore = true)
  Employee toEmployee(com.github.azell.jooq_utils.sample.entities.Person value);
}
