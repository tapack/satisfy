Scenario: use page steps
When open 'draggable-options.html' page
Then page has 'A Draggable Element with Options' title

Scenario: use drag and drop steps
When select '//*[@id="makeMeDraggable"]' and move with offset '50', '50' coordinate
!-- Then verify element '//*[@id="makeMeDraggable"]' has correct coordinate '50', '50'
