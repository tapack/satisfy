Scenario: use get method and check status code
When use GET 'http://localhost:18082/json/all-users' url
Then verify status code '200' in REST response

Scenario: use get method with params and check response
When use GET 'http://localhost:18082/json/single-user' with params 'id=2'
Then verify REST response equals to '{"email":"marta@hascode.com","firstName":"Marta","id":"2","lastName":"Stevens"}' in response

Scenario: use get method with params and check content type
When use GET 'http://localhost:18082/json/single-user' with params 'id=2'
Then verify content type 'application/json' in REST response

Scenario: check put method for status code
When use PUT 'http://localhost:18082/json/single-user' with params 'id=2, fisrtName=Antonio, lastName=Banderos'
When use GET 'http://localhost:18082/json/all-users' url
Then verify REST response has 'user[1].id' JsonPath
Then verify REST response has 'user[1].lastName' JsonPath with 'Bander'
Then verify REST response has 'user.lastName' JsonPath with 'Bander'

Scenario: check delete method for status code
When use DELETE 'http://localhost:18082/json/single-user' with params 'id=3'
When use GET 'http://localhost:18082/json/all-users' url
Then verify REST response hasn't 'user[3]' JsonPath
Then verify REST response hasn't 'user[1].lastName' JsonPath with 'Antonio'
Then verify REST response hasn't 'user.lastName' JsonPath with 'Antonio'

Scenario: check post method for status code
Given custom header 'super-token' with value '{1a2b3c49d8e76f23f4d65a87}' for all requests
When use POST 'http://localhost:18082/json/single-user' with params 'id=4, fisrtName=Fabricio, lastName=Junasti, email=fabricio@gmail.com'
When use GET 'http://localhost:18082/json/all-users' url
Then verify REST response has 'user[2]' JsonPath

Scenario: check put method with body from file
Given custom header 'token' with value '87654321' for next request
When use POST 'http://localhost:18082/json/single-user-by-json' with body from '/user-5.json'
When use GET 'http://localhost:18082/json/all-users' url
Then verify REST-JSON response is equal to '/users-list.json'
Then get value from REST-JSON response by 'user[3].id' and save to 'existing_user'

Scenario: check post method with body from file
When use PUT 'http://localhost:18082/json/single-user-by-json' with body from '/user-update.json.ftl'
When use GET 'http://localhost:18082/json/all-users' url
Then verify REST-JSON response is similar to '/users-list.json'