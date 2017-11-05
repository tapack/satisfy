Scenario: User opens Data Exporter page
When the user load '/static-test-example/data/cars.pdf' file with key 'fileDownloadKey'

Scenario: Verify downloaded PDF file and verify text content
Then verify that pdf file contains text 'Model Year Manufacturer Color' with 'fileDownloadKey'

Scenario: Verify  PDF does not contain tex
Then verify that pdf file does not contains text 'Oldsmobile' with 'fileDownloadKey'

Scenario: Verify downloaded PDF file and verify number of text occurrences
Then verify that pdf file has '1' occurrences of text 'Manufacturer' with 'fileDownloadKey'



