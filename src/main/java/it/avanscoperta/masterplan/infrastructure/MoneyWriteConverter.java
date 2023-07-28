package it.avanscoperta.masterplan.infrastructure;

import org.javamoney.moneta.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class MoneyWriteConverter implements Converter<Money, String> {
    @Override
    public String convert(Money money) {
        return money.toString();
    }
}
