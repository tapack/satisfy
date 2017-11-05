Scenario: use steps for checkbox widget
When open 'checkbox-example.html' page
Then 'header' contains 'Checkbox Example' text
And 'status' contains 'Unchecked' text
When make checkbox 'check' checked
Then 'status' contains 'Checked' text
When make checkbox 'check' unchecked
Then 'status' contains 'Unchecked' text
