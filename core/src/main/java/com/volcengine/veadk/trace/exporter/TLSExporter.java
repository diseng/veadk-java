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
package com.volcengine.veadk.trace.exporter;

import com.volcengine.veadk.utils.EnvUtil;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.trace.export.SpanExporter;

public class TLSExporter implements ExporterFactory {

    @Override
    public SpanExporter create() {
        OtlpGrpcSpanExporter spanExporter =
                OtlpGrpcSpanExporter.builder()
                        .setEndpoint(EnvUtil.getTLSEndpoint())
                        .addHeader("x-tls-otel-tracetopic", EnvUtil.getTLSServiceName())
                        .addHeader("x-tls-otel-region", EnvUtil.getTLSRegion())
                        .addHeader("x-tls-otel-ak", EnvUtil.getAccessKey())
                        .addHeader("x-tls-otel-sk", EnvUtil.getSecretKey())
                        .build();
        return spanExporter;
    }
}
