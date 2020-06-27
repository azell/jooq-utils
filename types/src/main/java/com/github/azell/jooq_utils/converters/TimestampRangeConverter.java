package com.github.azell.jooq_utils.converters;

import com.google.common.collect.Range;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import org.jooq.Converter;

public class TimestampRangeConverter implements Converter<Object, Range<LocalDateTime>> {
  private static final DateTimeFormatter FORMATTER =
      new DateTimeFormatterBuilder()
          .appendLiteral('"')
          .appendPattern("yyyy-MM-dd HH:mm:ss")
          .optionalStart()
          .appendLiteral(".")
          .appendFraction(ChronoField.NANO_OF_SECOND, 1, 6, false)
          .optionalEnd()
          .appendLiteral('"')
          .toFormatter(Locale.ENGLISH);

  @Override
  public Range<LocalDateTime> from(Object obj) {
    return RangeHandler.INSTANCE.from(obj, t -> LocalDateTime.parse(t, FORMATTER));
  }

  @Override
  public Class<Object> fromType() {
    return Object.class;
  }

  @Override
  public Object to(Range<LocalDateTime> range) {
    return RangeHandler.INSTANCE.to(range, FORMATTER::format);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public Class<Range<LocalDateTime>> toType() {
    return (Class) Range.class;
  }
}
