package com.volcengine.veadk.integration.websearch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.volcengine.error.SdkError;
import com.volcengine.model.response.RawResponse;
import com.volcengine.veadk.utils.JSONUtil;
import java.io.IOException;
import java.util.List;
import org.apache.http.NameValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class WebSearchWrapperTest {

    private WebSearchWrapper webSearchWrapper;

    @BeforeEach
    void setUp() {
        webSearchWrapper = Mockito.spy(new WebSearchWrapper("ak", "sk"));
    }

    @Test
    void doWebSearch_success_and_verify_params_and_body() throws Exception {
        String responseData =
                "{\"Result\":{\"WebResults\":[{\"Summary\":\"sum1\"},{\"Summary\":\"sum2\"}]}}";
        RawResponse mockResponse =
                new RawResponse(responseData.getBytes(), SdkError.SUCCESS.getNumber(), null);
        when(webSearchWrapper.json(anyString(), anyList(), anyString())).thenReturn(mockResponse);

        List<String> summaries = webSearchWrapper.doWebSearch("cats");
        assertEquals(2, summaries.size());
        assertEquals("sum1", summaries.get(0));
        assertEquals("sum2", summaries.get(1));

        ArgumentCaptor<List> paramsCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
        verify(webSearchWrapper)
                .json(eq("WebSearch"), paramsCaptor.capture(), bodyCaptor.capture());

        @SuppressWarnings("unchecked")
        List<NameValuePair> params = paramsCaptor.getValue();
        assertTrue(
                params.stream()
                        .anyMatch(
                                p ->
                                        p.getName().equals("Action")
                                                && p.getValue().equals("WebSearch")));
        assertTrue(
                params.stream()
                        .anyMatch(
                                p ->
                                        p.getName().equals("Version")
                                                && p.getValue().equals("2025-01-01")));

        JsonNode body = parseBody(bodyCaptor.getValue());
        assertEquals("cats", body.get("Query").asText());
        assertEquals(5, body.get("Count").asInt());
        assertEquals("web", body.get("SearchType").asText());
        assertTrue(body.get("NeedSummary").asBoolean());
    }

    @Test
    void doWebSearch_error_shouldThrow() throws Exception {
        Exception error = new Exception("web search failed");
        RawResponse mockResponse = new RawResponse(null, SdkError.EHTTP.getNumber(), error);
        when(webSearchWrapper.json(anyString(), anyList(), anyString())).thenReturn(mockResponse);

        assertThrows(Exception.class, () -> webSearchWrapper.doWebSearch("dogs"));
    }

    @Test
    void doWebSearch_success_but_missing_webresults_returns_empty() throws Exception {
        String responseData = "{\"Result\":{}}";
        RawResponse mockResponse =
                new RawResponse(responseData.getBytes(), SdkError.SUCCESS.getNumber(), null);
        when(webSearchWrapper.json(anyString(), anyList(), anyString())).thenReturn(mockResponse);

        List<String> summaries = webSearchWrapper.doWebSearch("birds");
        assertTrue(summaries.isEmpty());
    }

    @Test
    void doWebSearch_success_but_webresults_not_array_returns_empty() throws Exception {
        String responseData = "{\"Result\":{\"WebResults\":{\"Summary\":\"sum\"}}}";
        RawResponse mockResponse =
                new RawResponse(responseData.getBytes(), SdkError.SUCCESS.getNumber(), null);
        when(webSearchWrapper.json(anyString(), anyList(), anyString())).thenReturn(mockResponse);

        List<String> summaries = webSearchWrapper.doWebSearch("fish");
        assertTrue(summaries.isEmpty());
    }

    private JsonNode parseBody(String json) throws IOException {
        return JSONUtil.parseJson(json);
    }
}
