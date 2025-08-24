package debugsql;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DebugSqlAspect {
    @Around("@annotation(DebugSql)")
    public Object debugSql(ProceedingJoinPoint joinPoint) throws Throwable {
        DebugSqlCounter.reset();
        Object result = joinPoint.proceed();
        String methodName = joinPoint.getSignature().toShortString();
        DebugSqlCounter counter = DebugSqlCounter.get();
        System.out.print("[DEBUG SQL] - " );
        System.out.print("[" + methodName +"]");
        System.out.print(" - SELECT: " + counter.getSelectCount());
        System.out.print(", INSERT: " + counter.getInsertCount());
        System.out.print(", DELETE: " + counter.getDeleteCount());
        System.out.println(", UPDATE: " + counter.getUpdateCount());

        monitor(methodName);

        return result;
    }

    protected void monitor(String methodName) {
        DebugSqlCounter counter = DebugSqlCounter.get();
        SqlCounter sqlCounter = new SqlCounter();
        sqlCounter.setDeleteCount(counter.getDeleteCount());
        sqlCounter.setInsertCount(counter.getInsertCount());
        sqlCounter.setSelectCount(counter.getSelectCount());
        sqlCounter.setUpdateCount(counter.getUpdateCount());
        sqlCounter.setTotal(counter.getDeleteCount() + counter.getInsertCount() + counter.getSelectCount() + counter.getUpdateCount());
        counter.logSql(methodName,sqlCounter);

        }


}