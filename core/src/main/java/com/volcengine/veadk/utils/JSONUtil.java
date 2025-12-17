/**
 * Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd. and/or its affiliates.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.volcengine.veadk.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONUtil {

    private static final Logger log = LoggerFactory.getLogger(JSONUtil.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule()) // TODO: echo sec module replace, locale
                .setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
    }

    private JSONUtil() {}

    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Failed to serialize object to JSON", e);
            return "";
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference)
            throws JsonProcessingException {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize JSON to object", e);
            throw e;
        }
    }

    public static JsonNode parseJson(byte[] content) throws IOException {
        try {
            return MAPPER.readTree(content);
        } catch (IOException e) {
            log.error("Failed to parse JSON string", e);
            throw e;
        }
    }

    public static JsonNode parseJson(String content) throws IOException {
        try {
            return MAPPER.readTree(content);
        } catch (IOException e) {
            log.error("Failed to parse JSON string", e);
            throw e;
        }
    }

    public static JsonNode valueToTree(Object obj) {
        try {
            return MAPPER.valueToTree(obj);
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert object to JsonNode", e);
            throw e;
        }
    }

    public static <T> T convertValue(Object from, TypeReference<T> typeReference) {
        try {
            return MAPPER.convertValue(from, typeReference);
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert value", e);
            throw e;
        }
    }
}
