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
package com.volcengine.veadk.utils;

import com.google.adk.agents.InvocationContext;
import com.google.adk.agents.ReadonlyContext;
import java.lang.reflect.Field;

public class ReadonlyContextAccessorUtil {

    private static final Field INVOCATION_CONTEXT_FIELD;

    static {
        try {
            INVOCATION_CONTEXT_FIELD = ReadonlyContext.class.getDeclaredField("invocationContext");
            INVOCATION_CONTEXT_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("change ReadonlyContext invocationContext failed", e);
        }
    }

    public static InvocationContext getInvocationContext(ReadonlyContext readonlyContext) {
        try {
            return (InvocationContext) INVOCATION_CONTEXT_FIELD.get(readonlyContext);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("get invocationContext failed", e);
        }
    }
}
