/**
 * Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.volcengine.veadk.integration.vikingmemory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {

    @JsonProperty("default_user_id")
    private String defaultUserId;

    @JsonProperty("default_assistant_id")
    private String defaultAssistantId;

    @JsonProperty("time")
    private Long time;

    public Metadata(String defaultUserId, String defaultAssistantId, Long time) {
        this.defaultUserId = defaultUserId;
        this.defaultAssistantId = defaultAssistantId;
        this.time = time;
    }

    public String getDefaultUserId() {
        return defaultUserId;
    }

    public void setDefaultUserId(String defaultUserId) {
        this.defaultUserId = defaultUserId;
    }

    public String getDefaultAssistantId() {
        return defaultAssistantId;
    }

    public void setDefaultAssistantId(String defaultAssistantId) {
        this.defaultAssistantId = defaultAssistantId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
