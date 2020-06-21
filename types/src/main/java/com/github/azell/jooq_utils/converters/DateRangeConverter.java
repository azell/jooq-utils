package com.github.azell.jooq_utils.converters;

import com.google.common.collect.Range;
import java.time.LocalDate;
import org.jooq.Converter;

public class DateRangeConverter implements Converter<Object, Range<LocalDate>> {
  @Override
  public Range<LocalDate> from(Object obj) {
    return RangeHandler.INSTANCE.from(obj, LocalDate::parse);
  }

  @Override
  public Class<Object> fromType() {
    return Object.class;
  }

  @Override
  public Object to(Range<LocalDate> range) {
    return RangeHandler.INSTANCE.to(range, LocalDate::toString);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public Class<Range<LocalDate>> toType() {
    return (Class) Range.class;
  }
}
