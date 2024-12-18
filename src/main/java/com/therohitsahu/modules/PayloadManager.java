package com.therohitsahu.modules;

import com.google.gson.Gson;
import com.therohitsahu.pojos.Booking;
import com.therohitsahu.pojos.Bookingdates;
import com.therohitsahu.pojos.BookingResponse;

public class PayloadManager {

    private final Gson gson = new Gson(); // Initialize Gson instance

    // Method to generate payload as a JSON string for booking creation
    public String createPayloadBookingAsString() {
        Bookingdates bookingDates = new Bookingdates();
        bookingDates.setCheckin("2023-01-01");
        bookingDates.setCheckout("2023-01-10");

        Booking booking = new Booking();
        booking.setFirstname("James");
        booking.setLastname("Brown");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("Breakfast");

        return gson.toJson(booking); // Convert Booking object to JSON string
    }

    // Method to convert response JSON string to BookingResponse object
    public BookingResponse bookingResponseJava(String response) {
        try {
            return gson.fromJson(response, BookingResponse.class); // Parse JSON string to BookingResponse
        } catch (Exception e) {
            throw new RuntimeException("Error parsing response to BookingResponse object: " + e.getMessage(), e);
        }
    }
    public void getToken(){}
}
