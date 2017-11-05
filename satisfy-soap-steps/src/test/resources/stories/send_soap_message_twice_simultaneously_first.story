Scenario: Send SOAP message with param for fake SOAP service using ftl file first
Given start SOAP webservice port '9092'
Given web service endpoint 'http://localhost:9092/WheatherServicePort' and web service soap action 'getWeatherFor'
Given save placeholder with 'city' and 'Boston' for freemarker template
When send SOAP msg from file '/GetWeatherForRequestToFake.ftl'
Then verify that response is equal to file '/GetWeatherResponseForRequestFirst.xml'

