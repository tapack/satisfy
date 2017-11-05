Scenario: Use steps for rich editor widgets
When open 'rich-editor-example.html' page
Then page has 'Rich editor sample' title

Scenario: Use steps for iframe rich editor widget
Then wait 'cke_editor1' is visible
When fill 'cke_editor1' rich editor with 'Hello world!' text
Then 'cke_editor1' rich editor contains 'Hello world!' text
When fill 'cke_editor1' rich editor with 'Goodbye world!' text
Then 'cke_editor1' rich editor does not contains 'Hello world!' text

Scenario: Use steps for inline rich editor widget
When fill 'inlineEditor' rich editor with 'Hello world!' text
Then 'inlineEditor' rich editor does not contains 'EDIT ME' text
Then 'inlineEditor' rich editor contains 'Hello world!' text