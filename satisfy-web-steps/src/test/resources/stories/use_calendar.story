Scenario: use steps for calendar widget
When open 'datepicker-example.html' page
Then page has 'DatePicker Example' title
And wait 'datepicker' is visible
When choose date '5/16/2012' in 'datepicker' calendar
Then selected date '5/16/2012' appears in the 'datepicker' calendar
When choose date '5/15/2011' in 'datepicker' date picker
Then selected date '05/15/2011' appears in the 'datepicker' calendar
When choose date '6/15/2011' in 'datepicker' date picker
Then selected date '06/15/2011' appears in the 'datepicker' calendar
