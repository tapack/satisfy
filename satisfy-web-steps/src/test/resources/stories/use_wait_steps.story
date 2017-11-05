Scenario: use steps for wait web elements
When open 'show-hide-element-example.html' page
Then page has 'Show and Hide Elements' title
And wait 'text-area-image' is not visible
And wait 'showElementButton' is visible

Scenario: use wait steps with id selector
When click 'showElementButton'
Then wait 'text-area-loader' is not visible
And wait 'text-area-image' is visible

Scenario: use wait steps with css selector
When click 'showElementButton'
Then wait '.ui-text-loader' is not visible
And wait '.ui-text-image' is visible

Scenario: use wait steps with css selector
When click 'showElementButton'
Then wait '//*[@id='text-area-loader']' is not visible
And wait '//*[@id='text-area-image']' is visible