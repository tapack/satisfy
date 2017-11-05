Scenario: Send SOAP message to web service with secured sertificate and verify response using files
Given send SOAP web service through https
Given web service endpoint 'https://localhost:9091/WheatherServicePort' and web service soap action ''
When send SOAP msg from file '/GetWeatherInformationRequestToFake.xml'
Then verify that response is equal to file '/GetWeatherInformationResponseFromFake.xml'