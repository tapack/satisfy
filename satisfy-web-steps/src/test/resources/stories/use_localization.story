GivenStories: preconditions/open_example_page.story

Scenario: test multiple locale support
When fill 'sayInput' with '@name'
And click 'sayButton'
Then 'saidWord' contains '@name' text