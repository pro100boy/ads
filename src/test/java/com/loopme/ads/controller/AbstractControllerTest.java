package com.loopme.ads.controller;

import com.google.gson.*;
import com.loopme.ads.AdsApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdsApplication.class)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:db/schema-h2.sql", "classpath:db/data-h2.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public abstract class AbstractControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }

    class LocalDateTimeJsonConverter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

        private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(localDateTime.format(dateTimeFormatter));
        }

        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(), dateTimeFormatter);
        }
    }

    private final Gson gson =
            new GsonBuilder()
                    //.setPrettyPrinting()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter())
                    .create();

    String toJson(Object o) {
        return gson.toJson(o);
    }

    public Gson getGson() {
        return gson;
    }
}
