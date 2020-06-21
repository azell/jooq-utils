package com.github.azell.jooq_utils.converters;

import com.google.common.collect.Range;
import org.jooq.Converter;

public class Int4RangeConverter implements Converter<Object, Range<Integer>> {
  @Override
  public Range<Integer> from(Object obj) {
    return RangeHandler.INSTANCE.from(obj, Integer::parseInt);
  }

  @Override
  public Class<Object> fromType() {
    return Object.class;
  }

  @Override
  public Object to(Range<Integer> range) {
    return RangeHandler.INSTANCE.to(range, v -> v.toString());
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public Class<Range<Integer>> toType() {
    return (Class) Range.class;
  }
}
