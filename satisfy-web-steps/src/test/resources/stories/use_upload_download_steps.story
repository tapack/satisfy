Scenario: use steps for download file
When open 'download-upload-example.html' page
Then page has 'Upload and Download example' title
When user downloads file by click on 'downloadLink' and save as 'someKey'

Scenario: use steps for upload file
Then wait 'fileupload' is visible
When user upload '/static-test-example/test-image.jpg' to field 'fileupload'
When click 'submit'