package com.github.azell.jooq_utils.converters;

import com.google.common.collect.Range;
import java.math.BigDecimal;
import org.jooq.Converter;

public class NumRangeConverter implements Converter<Object, Range<BigDecimal>> {
  @Override
  public Range<BigDecimal> from(Object obj) {
    return RangeHandler.INSTANCE.from(obj, BigDecimal::new);
  }

  @Override
  public Class<Object> fromType() {
    return Object.class;
  }

  @Override
  public Object to(Range<BigDecimal> range) {
    return RangeHandler.INSTANCE.to(range, BigDecimal::toString);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public Class<Range<BigDecimal>> toType() {
    return (Class) Range.class;
  }
}
