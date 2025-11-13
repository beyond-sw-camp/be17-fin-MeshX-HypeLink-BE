package MeshX.HypeLink.common;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TryCatchTemplate {

    private TryCatchTemplate() {
        // 인스턴스화 방지
    }

    public static <T> T parse(ThrowingCustomer<T> callback, Consumer<Exception> exceptionHandler) {
        try {
            return callback.parse();
        } catch (Exception e) {
            exceptionHandler.accept(e);
            throw new RuntimeException(e); // handler 이후 예외 전파
        }
    }

    public static void parse(ThrowingRunnable callback, Consumer<Exception> exceptionHandler) {
        try {
            callback.run();
        } catch (Exception e) {
            exceptionHandler.accept(e);
        }
    }

    public static void run(Runnable callback, Consumer<Exception> exceptionHandler) {
        try {
            callback.run();
        } catch (Exception e) {
            exceptionHandler.accept(e);
        }
    }

    /**
     * 락을 자동으로 획득/해제하고 예외를 처리하는 템플릿
     *
     * @param lock            Redisson RLock 객체
     * @param waitTime        락 대기 시간 (초)
     * @param leaseTime       락 점유 시간 (초)
     * @param logic           임계 구역 로직
     * @param exceptionHandler 예외 처리 로직
     */
    public static void withLock(
            RLock lock,
            long waitTime,
            long leaseTime,
            ThrowingRunnable logic,
            Consumer<Exception> exceptionHandler
    ) {
        try {
            if (lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)) {
                logic.run();
            } else {
                throw new RuntimeException("다른 요청이 이미 락을 점유 중입니다.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            exceptionHandler.accept(e);
        } catch (Exception e) {
            exceptionHandler.accept(e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
