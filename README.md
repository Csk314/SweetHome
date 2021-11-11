# SweetHome
Restaurant Booking Application
Breaking the Hotel room booking application into three different microservices, which are as follows:

API-Gateway - This service is exposed to the outer world and is responsible for routing all requests to the microservices internally.
Booking service - This service is responsible for collecting all information related to user booking and sending a confirmation message once the booking is confirmed.
Payment service - This is a dummy payment service; this service is called by the booking service for initiating payment after confirming rooms.
 
 Initially, the API Gateway, Booking service and Payment service register themselves on the Eureka server. Note that the user does not directly interact with Booking or Payment service. All requests are sent to API Gateway which then sends the requests to the relevant microservice.
The user initiates room booking using the 'Booking' service by providing information such as toDate, fromDate, aadharNumber and number of rooms required (numOfRooms).
The 'Booking' service returns back the list of room numbers and price associated and prompts the user to enter the payment details if they wish to continue ahead with the booking. It also stores the details provided by the user in its database. 
If the user wants to go ahead with the booking, then they can simply provide the payment related details like bookingMode, upiId/ cardNumber to the 'Booking' service which will be further sent to the 'Payment' service. Based on this data, the payment service will perform a dummy transaction and return back the transaction Id associated with the transaction to the Booking service. All the information related to transactions is saved in the database of the payment service.
Once the transaction completes, booking is confirmed and therefore Booking service sends a confirmation message on the console.
