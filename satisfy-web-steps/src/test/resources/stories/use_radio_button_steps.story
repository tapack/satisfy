GivenStories: preconditions/open_example_page.story

Scenario: check radio button
When click radio button with label 'RadioButton'
Then wait 'checked' is visible