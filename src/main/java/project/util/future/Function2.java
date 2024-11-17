package project.util.future;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function2<A, B, R> {
    R apply(A var1, B var2);

    default <V> Function2<A, B, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (a, b) -> {
            return after.apply(this.apply(a, b));
        };
    }
}
