package com.github.azell.jooq_utils.converters;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import java.time.LocalDate;
import org.jooq.Converter;

public class DateRangeConverter implements Converter<Object, Range<LocalDate>> {
  @Override
  public Range<LocalDate> from(Object obj) {
    if (obj == null) {
      return null;
    }

    var str = obj.toString();

    var lowerBound = str.charAt(0) == '[' ? BoundType.CLOSED : BoundType.OPEN;
    var upperBound = str.charAt(str.length() - 1) == ']' ? BoundType.CLOSED : BoundType.OPEN;

    var delim = str.indexOf(',');

    if (delim == -1) {
      throw new IllegalArgumentException("Cannot find comma character");
    }

    var lowerStr = str.substring(1, delim);
    var upperStr = str.substring(delim + 1, str.length() - 1);

    var lower = lowerStr.isEmpty() ? null : LocalDate.parse(lowerStr);
    var upper = upperStr.isEmpty() ? null : LocalDate.parse(upperStr);

    if (lower != null) {
      if (upper != null) {
        if (lowerBound == BoundType.OPEN) {
          return upperBound == BoundType.OPEN
              ? Range.open(lower, upper)
              : Range.openClosed(lower, upper);
        } else {
          return upperBound == BoundType.OPEN
              ? Range.closedOpen(lower, upper)
              : Range.closed(lower, upper);
        }
      } else {
        return lowerBound == BoundType.OPEN ? Range.greaterThan(lower) : Range.atLeast(lower);
      }
    } else if (upper != null) {
      return upperBound == BoundType.OPEN ? Range.lessThan(upper) : Range.atMost(upper);
    } else {
      return Range.all();
    }
  }

  @Override
  public Class<Object> fromType() {
    return Object.class;
  }

  @Override
  public Object to(Range<LocalDate> range) {
    if (range == null) {
      return null;
    }

    StringBuilder sb = new StringBuilder();

    sb.append(range.hasLowerBound() && range.lowerBoundType() == BoundType.CLOSED ? '[' : '(')
        .append(range.hasLowerBound() ? range.lowerEndpoint() : "")
        .append(",")
        .append(range.hasUpperBound() ? range.upperEndpoint() : "")
        .append(range.hasUpperBound() && range.upperBoundType() == BoundType.CLOSED ? ']' : ')');

    return sb.toString();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public Class<Range<LocalDate>> toType() {
    return (Class) Range.class;
  }
}
