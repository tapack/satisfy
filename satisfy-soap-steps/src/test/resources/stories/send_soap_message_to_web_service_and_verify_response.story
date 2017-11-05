Scenario: Send SOAP message and verify response using files
Given start SOAP webservice port '9090'
Given web service endpoint 'http://localhost:9090/WheatherServicePort' and web service soap action 'getWeatherInformation'
When send SOAP msg from file '/GetWeatherInformationRequestToFake.xml'
Then verify that response is equal to file '/GetWeatherInformationResponseFromFake.xml'
Then verify that response is similar to file '/GetWeatherInformationSimilarResponseFromFake.xml'

Scenario: Send wrong SOAP message and verify response code
Given web service endpoint 'http://localhost:9090/WheatherServicePort' and web service soap action ''
When send SOAP msg from file '/GetWeatherInformationRequestWrongToFake.xml'
Then verify that 'fault string' is equal to 'Fault occurred while processing.'

Scenario: Send SOAP message for published on base URL webservice and verify response using files
Given published service endpoint 'WheatherServicePort' with port '9090' and protocol 'http'
Given web service SOAP Action '"getWeatherInformation"'
When send SOAP msg from file '/GetWeatherInformationRequestToFake.xml'
Then verify that response is equal to file '/GetWeatherInformationResponseFromFake.xml'
Then verify that response is similar to file '/GetWeatherInformationSimilarResponseFromFake.xml'

Scenario: Send SOAP message for fake SOAP service using ftl file
Given web service endpoint 'http://localhost:9090/WheatherServicePort' and web service soap action 'getWeatherInformation'
When send SOAP msg from file '/GetWeatherInformationRequestToFake.ftl'
Then verify that response is equal to file '/GetWeatherInformationResponseFromFake.xml'
Then verify that response is similar to file '/GetWeatherInformationSimilarResponseFromFake.xml'