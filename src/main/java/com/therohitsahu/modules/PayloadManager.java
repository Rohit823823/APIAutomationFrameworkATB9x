package com.therohitsahu.modules;

import com.google.gson.Gson;
import com.therohitsahu.pojos.Auth;
import com.therohitsahu.pojos.Booking;
import com.therohitsahu.pojos.Bookingdates;
import com.therohitsahu.pojos.BookingResponse;
import com.therohitsahu.pojos.TokenResponse;

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
    // Method to convert response JSON string to Booking object
    public Booking getResponseFromJSON(String response) {
        try {
            return gson.fromJson(response, Booking.class); // Parse JSON string to Booking object
        } catch (Exception e) {
            throw new RuntimeException("Error parsing response to Booking object: " + e.getMessage(), e);
        }
    }

    // Method to generate authorization payload as JSON string
    public String setAuthPayload() {
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");

        return gson.toJson(auth); // Convert Auth object to JSON string
    }

    // Method to extract token from response JSON string
    public String getTokenFromJSON(String tokenResponse) {
        try {
            TokenResponse token = gson.fromJson(tokenResponse, TokenResponse.class); // Parse JSON string to TokenResponse
            return token.getToken(); // Return token value
        } catch (Exception e) {
            throw new RuntimeException("Error parsing token response: " + e.getMessage(), e);
        }
    }

    // Method to generate full update payload as JSON string
    public String fullUpdatePayloadAsString() {
        Bookingdates bookingDates = new Bookingdates();
        bookingDates.setCheckin("2024-02-15");
        bookingDates.setCheckout("2024-02-20");

        Booking booking = new Booking();
        booking.setFirstname("Pramod");
        booking.setLastname("Dutta");
        booking.setTotalprice(112);
        booking.setDepositpaid(true);
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("Lunch");

        return gson.toJson(booking); // Convert updated Booking object to JSON string
    }
}
