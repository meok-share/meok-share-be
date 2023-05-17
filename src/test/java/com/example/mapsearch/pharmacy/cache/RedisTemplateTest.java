package com.example.mapsearch.pharmacy.cache;

import com.example.mapsearch.AbstractIntegrationContainerBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class RedisTemplateTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void redisTemplateStringTest() {
        // given
        final ValueOperations valueOperations = redisTemplate.opsForValue();

        String key = "stringKey";
        String value = "hello";

        // when
        valueOperations.set(key, value);

        // then
        assertThat(valueOperations.get(key)).isEqualTo(value);
    }

    @Test
    void redisTemplateSetOperations() {

        // given
        final SetOperations setOperations = redisTemplate.opsForSet();

        String key = "setKey";

        // when

        setOperations.add(key, "h", "e", "l", "l", "o");

        // then
        assertThat(setOperations.size(key)).isEqualTo(4);
    }

    @Test
    void redisTemplateSetOperations2() {

        // given
        final HashOperations hashOperations = redisTemplate.opsForHash();

        String key = "hashKey";

        // when
        hashOperations.put(key, "subKey", "value");
        String result = "value";

        // then
        final Map entries = hashOperations.entries(key);

        entries.keySet().contains("subKey");
        entries.values().contains("value");

        assertThat(result).isEqualTo(hashOperations.get(key, "subKey"));
        assertThat(hashOperations.size(key)).isEqualTo(1);

    }
}
