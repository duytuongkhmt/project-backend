package project.util.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFutures {
    private CompletableFutures() {}
    public static <R, A, B> CompletionStage<R> combine(CompletableFuture<A> a, CompletableFuture<B> b, Function2<A, B, R> function) {
        return CompletableFuture.allOf(a, b).thenApply((ignored) -> {
            return function.apply(a.join(), b.join());
        });
    }
}
