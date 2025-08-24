package gafawork.debugsql;


import org.hibernate.resource.jdbc.spi.StatementInspector;

public class CountStatementInspector implements StatementInspector {

    @Override
    public String inspect(String sql) {
        String sqlLower = sql.toLowerCase();
        DebugSqlCounter counter = DebugSqlCounter.get();

        if (sqlLower.startsWith("select")) {
            counter.setSelectCount(counter.getSelectCount() + 1);

        } else if (sqlLower.startsWith("insert")) {
            counter.setSelectCount(counter.getSelectCount() + 1);
        } else if (sqlLower.startsWith("update")) {
            counter.setUpdateCount(counter.getUpdateCount() + 1);
        } else if (sqlLower.startsWith("delete")) {
            counter.setDeleteCount(counter.getDeleteCount() + 1);
        }

        return sql;
    }
}
