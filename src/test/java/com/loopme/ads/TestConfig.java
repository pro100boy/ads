package com.loopme.ads;

import com.google.gson.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {AdsApplication.class})
public class TestConfig {

    class CustomLocalDateTimeSerializer implements JsonSerializer < LocalDateTime > {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
            return new JsonPrimitive(formatter.format(localDateTime));
        }
    }

    private final Gson gson =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDateTime.class, new CustomLocalDateTimeSerializer())
                    .create();

    String toJson(Object o) {
        return gson.toJson(o);
    }

}
