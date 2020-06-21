package com.github.azell.jooq_utils.bindings;

import com.github.azell.jooq_utils.converters.TimestampRangeConverter;
import com.google.common.collect.Range;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.time.LocalDateTime;
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

public class PostgresTimestampRangeBinding implements Binding<Object, Range<LocalDateTime>> {
  @Override
  public Converter<Object, Range<LocalDateTime>> converter() {
    return new TimestampRangeConverter();
  }

  @Override
  public void get(BindingGetResultSetContext<Range<LocalDateTime>> ctx) throws SQLException {
    ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()));
  }

  @Override
  public void get(BindingGetSQLInputContext<Range<LocalDateTime>> ctx) throws SQLException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public void get(BindingGetStatementContext<Range<LocalDateTime>> ctx) throws SQLException {
    ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
  }

  @Override
  public void register(BindingRegisterContext<Range<LocalDateTime>> ctx) throws SQLException {
    ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
  }

  @Override
  public void set(BindingSetSQLOutputContext<Range<LocalDateTime>> ctx) throws SQLException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public void set(BindingSetStatementContext<Range<LocalDateTime>> ctx) throws SQLException {
    ctx.statement()
        .setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
  }

  @Override
  public void sql(BindingSQLContext<Range<LocalDateTime>> ctx) {
    ctx.render().visit(DSL.val(ctx.convert(converter()).value())).sql("::tsrange");
  }
}
