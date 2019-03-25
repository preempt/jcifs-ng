package jcifs.util;

import org.joda.time.Duration;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Retrier<T>
{
    CompletableFuture<T> retry(Supplier<T> valueSupplier,
                               Predicate<T> valuePredicate,
                               int maxPollAttempts,
                               Duration pollInterval,
                               boolean retryOnException);
}
