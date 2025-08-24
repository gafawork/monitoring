package debugsql;

import java.util.HashMap;
import java.util.Map;

public class DebugSqlCounter {
    private static final ThreadLocal<DebugSqlCounter> COUNTER = ThreadLocal.withInitial(DebugSqlCounter::new);

    private static final HashMap<String, SqlCounter> sqlCountsMax = new HashMap<>();

    private int selectCount = 0;
    private int insertCount = 0;
    private int updateCount = 0;
    private int deleteCount = 0;


    public void logSql(String nameMethod, SqlCounter sqlCounter) {
        if (sqlCountsMax.containsKey(nameMethod)) {
            // remove if the new one is greater
            SqlCounter existing = sqlCountsMax.get(nameMethod);
            if (sqlCounter.getTotal() > existing.getTotal()) {
                sqlCountsMax.put(nameMethod, sqlCounter);
            }
        } else {
            sqlCountsMax.put(nameMethod, sqlCounter);
        }
    }

    public static Map<String, SqlCounter> getSqlCountsMax() {
        return sqlCountsMax;
    }
    public static DebugSqlCounter get() {
        return COUNTER.get();
    }

    public static void reset() {
        COUNTER.set(new DebugSqlCounter());
    }

    public int getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    public int getInsertCount() {
        return insertCount;
    }

    public void setInsertCount(int insertCount) {
        this.insertCount = insertCount;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public int getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(int deleteCount) {
        this.deleteCount = deleteCount;
    }
}
