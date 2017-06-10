package com.vd;

import com.vd.fx.ReturnInDolar;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@EnableAutoConfiguration(exclude = { EmbeddedMongoAutoConfiguration.class })
public class ReturnCalculatorConfiguration {

    @Bean
    public ReturnInDolar returnInDolar() {
        return (currency ->  BigDecimal.ONE);
    }


}
