Scenario: use table steps
When open 'tables-example.html' page
Then wait 'table_simple' is visible
And wait 'table_advanced' is visible

Scenario: use table steps for simple table
Then verify that in table 'table_simple' present row where:
|Table_Head2|
|Value2.2|
When in 'table_simple' table click on './/button[text()='Table Simple']' for row with parameters:
|Table_Head2|Table_Head3|
|Value2.2|Value3.3|
Then verify that in table 'table_simple' present row where:
|Table_Head2|Table_Head3|
|New Value2.2|New Value3.3|
Then verify that in table 'table_simple' is absent row where:
|Table_Head2|Table_Head3|
|Value2.2|Value3.3|
Then verify that in table 'table_simple' present row where:
|Table_Head2|Table_Head3|
|||

Scenario: use table steps for advanced table
Then verify that in table 'table_advanced' present row where:
|Column1|Column5|
|Subtable_1|Value5.3|
When save text from 'test_value' to 'save_value' variable
Then verify that in table 'table_advanced' present row where:
|Column1|Column5|
|Subtable_1|${save_value}|
When in 'table_advanced' table click on './/button[text()='Table Advanced']' for row with parameters:
|Column1|Column5|
|Subtable_1|Value5.3|
Then verify that in table 'table_advanced' present row where:
|Column1|Column5|
|New Subtable_1|New Value5.3|
Then verify that in table 'table_advanced' is absent row where:
|Column1|Column5|
|Subtable_1|Value5.3|