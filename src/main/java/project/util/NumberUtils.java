package project.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.function.ToDoubleFunction;

public class NumberUtils {
    private NumberUtils() {

    }

    public static <T> double calculateTotal(List<T> items, ToDoubleFunction<T> mapper) {
        return Optional.ofNullable(items)
                .map(list -> list.stream()
                        .mapToDouble(item -> {
                            try {
                                return mapper.applyAsDouble(item);
                            } catch (NullPointerException e) {
                                return 0.0;
                            }
                        }).sum()
                )
                .orElse(0.0);
    }

    public static <T> double calculateTotal(List<T> items, ToDoubleFunction<T> mapper, Double percent) {
        double validPercent = Optional.ofNullable(percent).orElse(1.0);
        double total = calculateTotal(items, mapper) * validPercent;
        return Math.round(total);
    }

    public static double roundDoubleToDouble(double value) {
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double roundDoubleToInteger(double value) {
        return Math.round(value);
    }

}
