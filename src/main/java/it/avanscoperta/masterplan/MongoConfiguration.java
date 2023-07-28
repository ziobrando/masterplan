package it.avanscoperta.masterplan;

import it.avanscoperta.masterplan.infrastructure.MoneyReadConverter;
import it.avanscoperta.masterplan.infrastructure.MoneyWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        List<Converter> converters = new ArrayList<>();
        converters.add(new MoneyReadConverter());
        converters.add(new MoneyWriteConverter());
        return new MongoCustomConversions(converters);
    }
}
