GivenStories: preconditions/open_example_page.story

Scenario: use variables from data module
When save text from 'variableLabel' to 'data' variable
And fill 'sayInput' with 'Hello ${data}!'
And click '@say'
Then 'saidWord' contains 'Hello ${my.var}!' text