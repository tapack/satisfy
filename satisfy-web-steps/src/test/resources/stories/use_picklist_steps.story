Scenario: open page with picklist widget
When open 'picklist-example.html' page
Then 'header' contains 'Picklist Example' text

Scenario: use steps for picklist widget
When double click on item 'Option 1' from '//*[contains(@class, 'pickList')]' picklist
Then 'Option 1,Option 2,Option 4' appeared in '//*[contains(@class, 'pickList')]' picklist target list
When select 'Option 1,Option 2' from '//*[contains(@class, 'pickList')]' picklist
When press remove button in '//*[contains(@class, 'pickList')]' picklist
Then 'Option 1,Option 2,Option 3,Option 5' appeared in '//*[contains(@class, 'pickList')]' picklist source list
When select 'Option 3' from '//*[contains(@class, 'pickList')]' picklist
When press add button in '//*[contains(@class, 'pickList')]' picklist
When press remove all button in '//*[contains(@class, 'pickList')]' picklist
When press add all button in '//*[contains(@class, 'pickList')]' picklist
Then 'Option 1,Option 2,Option 3,Option 4,Option 5' appeared in '//*[contains(@class, 'pickList')]' picklist target list