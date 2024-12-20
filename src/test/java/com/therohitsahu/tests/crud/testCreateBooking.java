package com.therohitsahu.tests.crud;

import com.therohitsahu.base.BaseTest;
import com.therohitsahu.endpoints.APIConstants;
import com.therohitsahu.modules.PayloadManager;
import com.therohitsahu.pojos.BookingResponse;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

public class testCreateBooking extends BaseTest {

    @Owner("Promode")
    @TmsLink("https://google.com") // Updated TMS link as per the second code
    @Link(name = "Link to TC", url = "https://bugz.atlassian.net/browse/RBT-4")
    @Issue("JIRA_RBT-4")
    @Description("Verify that POST request is working fine.")
    @Test(groups = "qa")
    public void testVerifyCreateBookingPOST01() {
        PayloadManager payloadManager = new PayloadManager(); // Explicit instance creation

        // Set base path
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        // Make POST request
        response = RestAssured.given(requestSpecification)
                .when()
                .body(payloadManager.createPayloadBookingAsString())
                .post();

        // Validate response
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        // Parse and validate response body
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        // Added null check for booking response
        Assert.assertNotNull(bookingResponse.getBooking(), "Booking response is null");

        // Verify the firstname matches expected value
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "James");
    }
}
