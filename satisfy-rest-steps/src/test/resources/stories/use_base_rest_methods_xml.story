Scenario: use get method and check status code
When use GET 'http://localhost:18082/xml/all-users' url
Then verify status code '200' in REST response

Scenario: use get method with params and check response
When use GET 'http://localhost:18082/xml/single-user' with params 'id=2'
Then verify REST response equals to '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ns2:user xmlns:ns2="http://www.example.org/bar"><email>marta@hascode.com</email><firstName>Marta</firstName><id>2</id><lastName>Stevens</lastName></ns2:user>' in response

Scenario: use get method with params and check content type
When use GET 'http://localhost:18082/xml/single-user' with params 'id=2'
Then verify content type 'application/xml' in REST response

Scenario: check put method for status code
When use PUT 'http://localhost:18082/xml/single-user' with params 'id=2, fisrtName=Antonio, lastName=Banderos'
When use GET 'http://localhost:18082/xml/all-users' url
Then verify REST response has '/users/user[firstName='Antonio' or lastName='Banderos']' XPath
Then verify REST response has '(//users/user)[2]/lastName' XPath with 'Bander'

Scenario: check delete method for status code
When use DELETE 'http://localhost:18082/xml/single-user' with params 'id=3'
When use GET 'http://localhost:18082/xml/all-users' url
Then verify REST response hasn't '(//users/user)[3]' XPath
Then verify REST response hasn't '(//users/user)[2]/lastName' XPath with 'Antonio'

Scenario: check post method for status code
Given custom header 'super-token' with value '{1a2b3c49d8e76f23f4d65a87}' for all requests
When use POST 'http://localhost:18082/xml/single-user' with params 'id=4, fisrtName=Fabricio, lastName=Junasti, email=fabricio@gmail.com'
When use GET 'http://localhost:18082/xml/all-users' url
Then verify REST response has '/users/user[firstName='Fabricio' or lastName='Junasti']' XPath

Scenario: check put method with body from file
Given custom header 'token' with value '12345678' for next request
When use POST 'http://localhost:18082/xml/single-user-by-xml' with body from '/user-5.xml'
When use GET 'http://localhost:18082/xml/all-users' url
Then verify REST-XML response is equal to '/users-list.xml'
Then get value from REST-XML response by '//user[4]/id' and save to 'existing_user'

Scenario: check post method with body from file
When use PUT 'http://localhost:18082/xml/single-user-by-xml' with body from '/user-update.xml.ftl'
When use GET 'http://localhost:18082/xml/all-users' url
Then verify REST-XML response is similar to '/users-list.xml'