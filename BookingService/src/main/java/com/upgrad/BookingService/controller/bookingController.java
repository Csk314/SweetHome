package com.upgrad.BookingService.controller;


import com.upgrad.BookingService.dto.BookingDto;
import com.upgrad.BookingService.dto.PaymentDto;
import com.upgrad.BookingService.entity.BookingInfoEntity;
import com.upgrad.BookingService.exceptions.invalidBookingIdException;
import com.upgrad.BookingService.exceptions.invalidPaymentModeException;
import com.upgrad.BookingService.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping(value = "/hotel")
public class bookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestTemplate restTemplate;

    /**
     * HTTP METHOD: POST to save Booking DATA
     * URL : http://localhost:9191/hotel/booking
     *
     * Request Body
     *
     *          {
     *          "fromDate": "2021-06-10",
     *          "toDate": "2021-06-15",
     *           "aadharNumber": "Sample-Aadhar-Number",
     *           "numOfRooms": 3
     *          }
     *
     *
     * Note that http://localhost:9191 is API GATE WAY URL
     * actual URI of the end point is
     *
     * http://localhost:8081/hotel/booking
     *
     */

    @PostMapping(value = "/booking",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addBooking(@RequestBody BookingDto bookingDto) {

        BookingInfoEntity newBooking = modelMapper.map(bookingDto, BookingInfoEntity.class);

        BookingInfoEntity savedBooking = bookingService.addBooking(newBooking);

        BookingDto savedBookingDto = modelMapper.map(savedBooking, BookingDto.class);

        return new ResponseEntity(savedBookingDto, HttpStatus.CREATED);
    }

    /**
     * HTTP METHOD: POST to call the Payment service
     * FROM Booking service using rest template
     * to get the transactionID
     *
     * URL: http://localhost:9191/hotel/booking/{bookingId}/transaction
     *
     * Request Body
     * {
     *     "paymentMode": "CARD",
     *     "bookingId": 1,
     *     "upiId": "",
     *     "cardNumber": "sadsda"
     * }
     *
     * Note that http://localhost:9191 is API GATE WAY URL
     * actual URI of the end point is
     *
     * http://localhost:8081/hotel/booking/{bookingId}/transaction
     *
     */
    @PostMapping(value = "/booking/{bookingId}/transaction",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPaymentData(@PathVariable(name = "bookingId") int bookingId, @RequestBody PaymentDto paymentDto) throws invalidBookingIdException, invalidPaymentModeException {

         BookingInfoEntity updatedpayment = bookingService.addPaymentData(bookingId, paymentDto);

        return new ResponseEntity(updatedpayment, HttpStatus.CREATED);
    }



}
