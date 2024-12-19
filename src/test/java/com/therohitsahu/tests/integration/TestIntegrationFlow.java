package com.therohitsahu.tests.integration;

import com.therohitsahu.base.BaseTest;
import com.therohitsahu.endpoints.APIConstants;
import com.therohitsahu.pojos.Booking;
import com.therohitsahu.pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestIntegrationFlow extends BaseTest {

    // Test Integration Scenario Steps:
    // 1. Create a booking -> bookingID
    // 2. Generate a token -> token
    // 3. Verify booking details with GET request
    // 4. Update the booking (requires bookingID and token)
    // 5. Delete the booking (requires bookingID and token)

    @Test(groups = "qa", priority = 1)
    @Owner("Promode")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be Created")
    public void testCreateBooking(ITestContext iTestContext) {
        // Set base path for the API endpoint
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        // Send POST request to create a new booking
        response = RestAssured.given(requestSpecification)
                .when().body(payloadManager.createPayloadBookingAsString()).post();

        // Validate response and status code
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        // Parse response into BookingResponse object
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        // Assert that the firstname is as expected
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "James");

        // Log the booking ID
        System.out.println(bookingResponse.getBookingid());

        // Save the booking ID in the test context
        iTestContext.setAttribute("bookingid", bookingResponse.getBookingid());
    }

    @Test(groups = "qa", priority = 2)
    @Owner("Promode")
    @Description("TC#INT1 - Step 2. Verify that the Booking By ID")
    public void testVerifyBookingId(ITestContext iTestContext) {
        // Retrieve the booking ID from the test context
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        // GET request URL to fetch booking details
        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathGET);

        // Send GET request
        requestSpecification.basePath(basePathGET);
        response = RestAssured.given(requestSpecification).when().get();

        // Validate response and status code
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        // Parse response into Booking object
        Booking booking = payloadManager.getResponseFromJSON(response.asString());

        // Validate booking details
        assertThat(booking.getFirstname()).isNotNull().isNotBlank();
        assertThat(booking.getFirstname()).isEqualTo("James");
    }

    @Test(groups = "qa", priority = 3)
    @Owner("Promode")
    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID")
    public void testUpdateBookingByID(ITestContext iTestContext) {
        // Retrieve the booking ID from the test context
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        // Generate a token
        String token = getToken();
        iTestContext.setAttribute("token", token);

        // PUT request URL to update booking details
        String basePathPUTPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathPUTPATCH);

        // Send PUT request with the updated payload and token
        requestSpecification.basePath(basePathPUTPATCH);
        response = RestAssured.given(requestSpecification).cookie("token", token)
                .when().body(payloadManager.fullUpdatePayloadAsString()).put();

        // Validate response and status code
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        // Parse response into Booking object
        Booking booking = payloadManager.getResponseFromJSON(response.asString());

        // Validate updated booking details
        assertThat(booking.getFirstname()).isNotNull().isNotBlank();
        assertThat(booking.getFirstname()).isEqualTo("Pramod");
        assertThat(booking.getLastname()).isEqualTo("Dutta");
    }

    @Test(groups = "qa", priority = 4)
    @Owner("Promode")
    @Description("TC#INT1 - Step 4. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext) {
        // Retrieve the booking ID and token from the test context
        String token = (String) iTestContext.getAttribute("token");
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        // DELETE request URL to delete the booking
        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        // Send DELETE request with the token
        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();

        // Validate response status code
        validatableResponse.statusCode(201);
    }
}

