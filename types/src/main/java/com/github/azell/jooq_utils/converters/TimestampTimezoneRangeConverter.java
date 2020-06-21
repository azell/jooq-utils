package com.github.azell.jooq_utils.converters;

import com.google.common.collect.Range;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import org.jooq.Converter;

public class TimestampTimezoneRangeConverter implements Converter<Object, Range<ZonedDateTime>> {
  private static final DateTimeFormatter FORMATTER =
      new DateTimeFormatterBuilder()
          .appendLiteral('"')
          .appendPattern("yyyy-MM-dd HH:mm:ss")
          .optionalStart()
          .appendPattern(".")
          .appendFraction(ChronoField.NANO_OF_SECOND, 1, 6, false)
          .optionalEnd()
          .appendPattern("X")
          .appendLiteral('"')
          .toFormatter(Locale.ENGLISH);

  @Override
  public Range<ZonedDateTime> from(Object obj) {
    return RangeHandler.INSTANCE.from(obj, t -> ZonedDateTime.parse(t, FORMATTER));
  }

  @Override
  public Class<Object> fromType() {
    return Object.class;
  }

  @Override
  public Object to(Range<ZonedDateTime> range) {
    return RangeHandler.INSTANCE.to(range, FORMATTER::format);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public Class<Range<ZonedDateTime>> toType() {
    return (Class) Range.class;
  }
}
