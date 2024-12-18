package com.therohitsahu.asserts;

import io.restassured.response.Response;

import static org.testng.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AssertActions {

    /**
     * Verifies that the actual response body string matches the expected value.
     *
     * @param actual      The actual response body value.
     * @param expected    The expected value to match.
     * @param description Description of the assertion.
     */
    public void verifyResponseBody(String actual, String expected, String description) {
        assertEquals(actual, expected, description);
    }

    /**
     * Verifies that the actual response body integer matches the expected value.
     *
     * @param actual      The actual response body value.
     * @param expected    The expected value to match.
     * @param description Description of the assertion.
     */
    public void verifyResponseBody(int actual, int expected, String description) {
        assertEquals(actual, expected, description);
    }

    /**
     * Verifies the HTTP status code of the response.
     *
     * @param response The HTTP response.
     * @param expected The expected status code.
     */
    public void verifyStatusCode(Response response, Integer expected) {
        assertNotNull(response, "Response is null");
        assertEquals(response.getStatusCode(), expected.intValue(), "Status code mismatch");
    }

    /**
     * Verifies that two strings match and the expected key is non-null and non-blank.
     *
     * @param keyExpected The expected string value.
     * @param keyActual   The actual string value.
     */
    public void verifyStringKey(String keyExpected, String keyActual) {
        // AssertJ for enhanced validation
        assertThat(keyExpected)
                .as("Expected key should not be null or blank")
                .isNotNull()
                .isNotBlank()
                .isEqualTo(keyActual);

        assertThat(keyActual)
                .as("Actual key should not be null or blank")
                .isNotNull()
                .isNotBlank();
    }

    /**
     * Verifies that the response contains a specific header.
     *
     * @param response    The HTTP response.
     * @param headerName  The header name to check.
     * @param headerValue The expected header value.
     */
    public void verifyHeader(Response response, String headerName, String headerValue) {
        assertNotNull(response, "Response is null");
        String actualHeaderValue = response.getHeader(headerName);
        assertNotNull(actualHeaderValue, "Header " + headerName + " is missing in the response");
        assertEquals(actualHeaderValue, headerValue, "Header value mismatch for " + headerName);
    }
}
