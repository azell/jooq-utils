package com.github.azell.jooq_utils.bindings;

import com.github.azell.jooq_utils.converters.Int8RangeConverter;
import com.google.common.collect.Range;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;
import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.Converter;
import org.jooq.impl.DSL;

public class PostgresInt8RangeBinding implements Binding<Object, Range<Long>> {
  @Override
  public Converter<Object, Range<Long>> converter() {
    return new Int8RangeConverter();
  }

  @Override
  public void get(BindingGetResultSetContext<Range<Long>> ctx) throws SQLException {
    ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()));
  }

  @Override
  public void get(BindingGetSQLInputContext<Range<Long>> ctx) throws SQLException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public void get(BindingGetStatementContext<Range<Long>> ctx) throws SQLException {
    ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
  }

  @Override
  public void register(BindingRegisterContext<Range<Long>> ctx) throws SQLException {
    ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
  }

  @Override
  public void set(BindingSetSQLOutputContext<Range<Long>> ctx) throws SQLException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public void set(BindingSetStatementContext<Range<Long>> ctx) throws SQLException {
    ctx.statement()
        .setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
  }

  @Override
  public void sql(BindingSQLContext<Range<Long>> ctx) {
    ctx.render().visit(DSL.val(ctx.convert(converter()).value())).sql("::int8range");
  }
}
