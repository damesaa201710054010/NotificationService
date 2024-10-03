package co.com.distributed.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.*;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.reactivecommons.utils.ObjectMapperImp;
import org.springframework.context.annotation.*;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

@Configuration
@ComponentScan(basePackages = "co.com.distributed.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
@Log4j2
public class UseCasesConfig {
        @Bean
        public ObjectMapper objectMapper() {
                return new ObjectMapperImp();
        }

        @Bean
        public Gson gson() {
                return new GsonBuilder().setPrettyPrinting()
                        .registerTypeAdapter(LocalDateTime.class,
                                (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> parse(json.getAsString(),
                                        ofPattern("yyyy-MM-dd HH:mm:ss"))
                        )
                        .registerTypeAdapter(LocalDateTime.class,
                                (JsonSerializer<LocalDateTime>) (dateTime, srcType, context) -> new JsonPrimitive(
                                        ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime)))
                        .create();
        }

        @Bean
        @Primary
        public com.fasterxml.jackson.databind.ObjectMapper objectMapperJson() {
                var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                return objectMapper;
        }
}
