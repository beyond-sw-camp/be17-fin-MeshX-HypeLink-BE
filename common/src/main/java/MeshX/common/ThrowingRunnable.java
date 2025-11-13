package MeshX.common;

@FunctionalInterface
public interface ThrowingRunnable {
    void run() throws Exception;
}