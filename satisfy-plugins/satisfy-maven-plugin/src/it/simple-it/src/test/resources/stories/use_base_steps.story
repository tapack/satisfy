Scenario: use page steps
When open 'static-example.html' page
Then page has 'Static Example' title

Scenario: click button with spaces in text
When click '//*[text()='Test Button']'

Scenario: use assertion steps
Then 'header' contains 'Static Exa' text
Then 'header' does not contain 'This is not text you are looking for' text

Scenario: use action steps
When fill 'login' with 'John'
And fill 'password' with 'Doe'
And click 'verify'

Scenario: use drop-down steps
Then 'drop-down-header' contains 'Static drop - down example' text
When select 'Volkswagen' from 'cars' drop-down
Then 'Volkswagen' is selected from 'cars' drop-down

Scenario: use attribute steps
Then element 'cars' contains attribute 'name' with value 'dropDownExample'
Then element 'cars' does not contain attribute 'name' with value 'Cars'
Then element 'cars' contains attribute 'name' with value 'dropDownExampl'
Then element 'cars' does not contain attribute 'name' with value 'Ca'

Scenario: use switch page steps
When open 'switch-page-example.html' page
Then page has 'Switch Page Example' title
When click 'switchLink'
When switch to new window
Then 'drop-down-header' contains 'Static drop - down example' text
When return to base window
Then wait 'switchLink' is visible
Then 'switchLink' contains 'Switch to new!' text

Scenario: use switch iframe steps
When open 'rich-editor-example.html' page
When fill 'cke_editor1' rich editor with 'Hello iFrame!' text
When switch to 'cke_editor1' iframe
Then './/body' contains 'Hello iFrame!' text
When return to page from iframe
Then 'inlineEditor' contains 'EDIT ME' text
