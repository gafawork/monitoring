package debugsql;


import org.hibernate.resource.jdbc.spi.StatementInspector;

public class CountStatementInspector implements StatementInspector {

    @Override
    public String inspect(String sql) {
        String sqlLower = sql.toLowerCase();
        DebugSqlCounter counter = DebugSqlCounter.get();

        if (sqlLower.startsWith("select")) {
            int selectCount = counter.getSelectCount();
            counter.setSelectCount(selectCount++);

        } else if (sqlLower.startsWith("insert")) {
            int insertCount = counter.getInsertCount();
            counter.setInsertCount(insertCount++);
        } else if (sqlLower.startsWith("update")) {
            int updateCount = counter.getUpdateCount();
            counter.setUpdateCount(updateCount++);

        } else if (sqlLower.startsWith("delete")) {
            int deleteCount = counter.getDeleteCount();
            counter.setDeleteCount(deleteCount++);
        }

        return sql;
    }
}
