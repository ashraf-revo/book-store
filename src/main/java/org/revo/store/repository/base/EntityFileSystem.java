package org.revo.store.repository.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public abstract class EntityFileSystem<T> {
    private final ObjectMapper objectMapper = cachingObjectMapper();


    public static ObjectMapper cachingObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();



//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
//
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
//


//        mapper.setDateFormat(new StdDateFormat());
//        mapper.registerModule(new Hibernate5Module()
//                .configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true)
//                .configure(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true)
//                .configure(Hibernate5Module.Feature.REQUIRE_EXPLICIT_LAZY_LOADING_MARKER, true)
//                .configure(Hibernate5Module.Feature.REPLACE_PERSISTENT_COLLECTIONS, true)
//        );


        return mapper;
    }

    protected void init() {
        try {
            if (Files.exists(this.getDump())) {
                String result = Files.readString(this.getDump());
                if (result != null && !result.trim().isEmpty()) {
                    TypeReference<Data<T>> ref = new TypeReference<>() {
                    };
                    List<T> list = objectMapper.readValue(result, ref).getData();
                    setData(list);
                }
            }
        } catch (IOException e) {
            log.error("error reading file ", e);
        }
    }

    public void flush() {
        try {
            String result = objectMapper.writeValueAsString(new Data<>(this.getData()));
            Files.writeString(this.getDump(), result);
        } catch (IOException e) {
            log.error("error storing file ", e);
        }
    }

    protected abstract Path getDump();

    protected abstract List<T> getData();

    protected abstract void setData(List<T> data);

}
