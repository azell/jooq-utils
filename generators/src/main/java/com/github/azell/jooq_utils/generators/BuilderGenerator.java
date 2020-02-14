package com.github.azell.jooq_utils.generators;

import java.util.List;
import org.jooq.codegen.GeneratorStrategy.Mode;
import org.jooq.codegen.JavaGenerator;
import org.jooq.codegen.JavaWriter;
import org.jooq.meta.Definition;
import org.jooq.meta.EmbeddableDefinition;
import org.jooq.meta.RoutineDefinition;
import org.jooq.meta.TableDefinition;
import org.jooq.meta.TypedElementDefinition;
import org.jooq.meta.UDTDefinition;
import org.jooq.tools.StringUtils;

public class BuilderGenerator extends JavaGenerator {
  private final boolean scala = false;

  @Override
  protected void generatePojoMultiConstructor(Definition tableOrUDT, JavaWriter out) {
    final String className = getStrategy().getJavaClassName(tableOrUDT, Mode.POJO);

    int maxLength = 0;
    for (TypedElementDefinition<?> column : getTypedElements(tableOrUDT))
      maxLength =
          Math.max(
              maxLength,
              out.ref(getJavaType(column.getType(resolver(Mode.POJO)), Mode.POJO)).length());

    if (scala) {
    }

    // [#3010] Invalid UDTs may have no attributes. Avoid generating this constructor in that case
    // [#3176] Avoid generating constructors for tables with more than 255 columns (Java's method
    // argument limit)
    else if (getTypedElements(tableOrUDT).size() > 0 && getTypedElements(tableOrUDT).size() < 256) {
      out.println();

      // Immutables annotation
      out.tab(1).println("@%s", out.ref("org.immutables.builder.Builder.Constructor"));
      out.tab(1).print("public %s(", className);

      String separator1 = "";
      for (TypedElementDefinition<?> column : getTypedElements(tableOrUDT)) {
        out.println(separator1);

        // [#5128] defaulted columns are nullable in Java
        if (column.getType(resolver()).isNullable()
            || column.getType(resolver()).isDefaulted()
            || column.getType(resolver()).isIdentity()) {
          out.tab(2).println("@%s", out.ref("org.jetbrains.annotations.Nullable"));
        }

        out.tab(2)
            .print(
                "%s %s",
                StringUtils.rightPad(
                    out.ref(getJavaType(column.getType(resolver(Mode.POJO)), Mode.POJO)),
                    maxLength),
                getStrategy().getJavaMemberName(column, Mode.POJO));
        separator1 = ",";
      }

      out.println();
      out.tab(1).println(") {");

      for (TypedElementDefinition<?> column : getTypedElements(tableOrUDT)) {
        final String columnMember = getStrategy().getJavaMemberName(column, Mode.POJO);

        out.tab(2).println("this.%s = %s;", columnMember, columnMember);
      }

      out.tab(1).println("}");

      // Immutables builder method
      out.println();
      out.tab(1).println("public static %s%s builder() {", className, "Builder");
      out.tab(2).println("return new %s%s();", className, "Builder");
      out.tab(1).println("}");
    }
  }

  private List<? extends TypedElementDefinition<? extends Definition>> getTypedElements(
      Definition definition) {
    if (definition instanceof TableDefinition) return ((TableDefinition) definition).getColumns();
    else if (definition instanceof EmbeddableDefinition)
      return ((EmbeddableDefinition) definition).getColumns();
    else if (definition instanceof UDTDefinition)
      return ((UDTDefinition) definition).getAttributes();
    else if (definition instanceof RoutineDefinition)
      return ((RoutineDefinition) definition).getAllParameters();
    else throw new IllegalArgumentException("Unsupported type : " + definition);
  }
}
