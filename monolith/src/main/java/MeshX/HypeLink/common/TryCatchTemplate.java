package MeshX.HypeLink.common;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TryCatchTemplate {

    private TryCatchTemplate() {
        // 인스턴스화 방지
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
}
