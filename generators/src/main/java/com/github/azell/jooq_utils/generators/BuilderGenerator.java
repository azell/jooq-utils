package com.github.azell.jooq_utils.generators;

import org.jooq.codegen.JavaGenerator;
import org.jooq.codegen.JavaWriter;
import org.jooq.meta.Definition;

public class BuilderGenerator extends JavaGenerator {
  @Override
  protected void generatePojoMultiConstructor(Definition tableOrUDT, JavaWriter out) {
    super.generatePojoMultiConstructor(tableOrUDT, out);
  }
}
