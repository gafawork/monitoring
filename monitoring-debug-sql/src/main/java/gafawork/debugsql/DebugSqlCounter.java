package gafawork.debugsql;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DebugSqlCounter {
    private static final ThreadLocal<DebugSqlCounter> COUNTER = ThreadLocal.withInitial(DebugSqlCounter::new);

    private static final Map<String, SqlCounter> sqlCountsMax = new ConcurrentHashMap<>();

    private int selectCount = 0;
    private int insertCount = 0;
    private int updateCount = 0;
    private int deleteCount = 0;

    public static void clear() {
        COUNTER.remove();
    }

    public void logSql(String nameMethod, SqlCounter sqlCounter) {
        sqlCountsMax.compute(nameMethod, (key, existing) -> {
            if (existing == null || sqlCounter.getTotal() > existing.getTotal()) {
                return sqlCounter;
            }
            return existing;
        });
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