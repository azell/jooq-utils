package com.github.azell.jooq_utils.custom;

import java.util.regex.Pattern;
import javax.lang.model.element.ExecutableElement;
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;

public class CustomAccessorNamingStrategy extends DefaultAccessorNamingStrategy {
  private static final Pattern JOOQ_RECORD_VALUES = Pattern.compile("value\\d+");

  @Override
  protected boolean isFluentSetter(ExecutableElement method) {
    String name = method.getSimpleName().toString();

    return super.isFluentSetter(method)
        && !("from".equals(name) || JOOQ_RECORD_VALUES.matcher(name).matches());
  }
}
