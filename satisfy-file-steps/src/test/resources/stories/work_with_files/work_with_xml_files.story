Scenario: User opens Data Exporter page
When the user load '/static-test-example/data/cars.xml' file with key 'fileDownloadKey'

Scenario: Verify  XML is contain tex
Then verify XML file contains text '<model>a6371881</model>' with 'fileDownloadKey'

Scenario: Verify  XML does not contain tex
Then verify  XML file does not contain text 'This text is absent in XML' with 'fileDownloadKey'

Scenario: Verify local XML file and is similar to another xml
Then verify XML file content is similar to file '/static-test-example/data/different_cars.xml' with 'fileDownloadKey'

Scenario: Verify downloaded PDF file is equal  to another xml
Then verify XML file content is equal to file '/static-test-example/data/carscopy.xml' with 'fileDownloadKey'

