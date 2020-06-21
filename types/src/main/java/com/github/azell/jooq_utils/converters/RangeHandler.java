package com.github.azell.jooq_utils.converters;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import java.util.function.Function;

enum RangeHandler {
  INSTANCE;

  <T extends Comparable> Range<T> from(Object obj, Function<String, T> mapper) {
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

    var lower = lowerStr.isEmpty() ? null : mapper.apply(lowerStr);
    var upper = upperStr.isEmpty() ? null : mapper.apply(upperStr);

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

  <T extends Comparable> Object to(Range<T> range, Function<T, String> mapper) {
    if (range == null) {
      return null;
    }

    var sb = new StringBuilder();

    sb.append(range.hasLowerBound() && range.lowerBoundType() == BoundType.CLOSED ? '[' : '(')
        .append(range.hasLowerBound() ? mapper.apply(range.lowerEndpoint()) : "")
        .append(",")
        .append(range.hasUpperBound() ? mapper.apply(range.upperEndpoint()) : "")
        .append(range.hasUpperBound() && range.upperBoundType() == BoundType.CLOSED ? ']' : ')');

    return sb.toString();
  }
}
