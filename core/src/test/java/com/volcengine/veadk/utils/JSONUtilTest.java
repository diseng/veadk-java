package com.volcengine.veadk.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Test;

class JSONUtilTest {

    @Test
    void toJson() {
        Map<String, String> map = Collections.singletonMap("key", "value");
        String json = JSONUtil.toJson(map);

        assertThat(json).isEqualTo("{\"key\":\"value\"}");
    }

    @Test
    void fromJson() throws JsonProcessingException {
        String json = "{\"key\":\"value\"}";
        Map<String, String> map =
                JSONUtil.fromJson(json, new TypeReference<Map<String, String>>() {});

        assertThat(map).hasSize(1).containsEntry("key", "value");
    }

    @Test
    void fromJson_withInvalidJson_shouldThrowException() {
        String invalidJson = "{\"key\":\"value\"";

        assertThatThrownBy(
                        () ->
                                JSONUtil.fromJson(
                                        invalidJson, new TypeReference<Map<String, String>>() {}))
                .isInstanceOf(JsonProcessingException.class);
    }

    @Test
    void parseJson_withBytes() throws IOException {
        byte[] content = "{\"key\":\"value\"}".getBytes();
        JsonNode jsonNode = JSONUtil.parseJson(content);

        assertThat(jsonNode.get("key").asText()).isEqualTo("value");
    }

    @Test
    void parseJson_withString() throws IOException {
        String content = "{\"key\":\"value\"}";
        JsonNode jsonNode = JSONUtil.parseJson(content);

        assertThat(jsonNode.get("key").asText()).isEqualTo("value");
    }

    @Test
    void valueToTree() {
        Map<String, String> map = Collections.singletonMap("key", "value");
        JsonNode jsonNode = JSONUtil.valueToTree(map);

        assertThat(jsonNode.get("key").asText()).isEqualTo("value");
    }

    @Test
    void convertValue() {
        Map<String, String> map = Collections.singletonMap("key", "value");
        Map<String, String> convertedMap =
                JSONUtil.convertValue(map, new TypeReference<Map<String, String>>() {});

        assertThat(convertedMap).hasSize(1).containsEntry("key", "value");
    }
}
