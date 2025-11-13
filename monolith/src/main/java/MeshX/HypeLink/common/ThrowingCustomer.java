package MeshX.HypeLink.common;

@FunctionalInterface
public interface ThrowingCustomer<T> {
    T parse() throws Exception;
}
