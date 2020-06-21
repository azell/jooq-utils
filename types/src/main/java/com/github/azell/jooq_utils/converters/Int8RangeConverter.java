package com.github.azell.jooq_utils.converters;

import com.google.common.collect.Range;
import org.jooq.Converter;

public class Int8RangeConverter implements Converter<Object, Range<Long>> {
  @Override
  public Range<Long> from(Object obj) {
    return RangeHandler.INSTANCE.from(obj, Long::parseLong);
  }

  @Override
  public Class<Object> fromType() {
    return Object.class;
  }

  @Override
  public Object to(Range<Long> range) {
    return RangeHandler.INSTANCE.to(range, v -> v.toString());
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public Class<Range<Long>> toType() {
    return (Class) Range.class;
  }
}
