package it.avanscoperta.masterplan.infrastructure;

import org.javamoney.moneta.Money;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class MoneyReadConverter implements Converter<String, Money> {
    @Override
    public Money convert(@NotNull String moneyString) {
        return Money.parse(moneyString);
    }
}

