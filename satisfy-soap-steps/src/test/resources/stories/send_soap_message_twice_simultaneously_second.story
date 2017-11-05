Scenario: Send SOAP message with param for fake SOAP service using ftl file second
Given start SOAP webservice port '9093'
Given web service endpoint 'http://localhost:9093/WheatherServicePort' and web service soap action 'getWeatherFor'
Given save placeholder with 'city' and 'Orlando' for freemarker template
When send SOAP msg from file '/GetWeatherForRequestToFake.ftl'
Then verify that response is equal to file '/GetWeatherResponseForRequestSecond.xml'