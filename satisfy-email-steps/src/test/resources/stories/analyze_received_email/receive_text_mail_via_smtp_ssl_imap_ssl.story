Scenario: User starts fake local mail servers smtps_imaps
Given start fake local mail servers

Scenario: User want to use smtp ssl and imaps protocols in testing
Given send protocol 'smtps' and receive protocol 'imaps'

Scenario: Send email smtps_imaps
When 'acceptancetestSMTPS_imaps@tapack.io' send email to test mailbox with subject 'Test mailbox' and body 'Some text for test email body part!'
And 'acceptancetestSMTPS_imaps@tapack.io' send email to test mailbox with subject 'Test mailbox' and body 'Some text for negative scenario'
And 'acceptancetestSMTPS_imaps@tapack.io' send email to test mailbox with subject 'Test negative mailbox' and body 'Some text for test email body part!'
And 'badacceptancetestSMTPS_imaps@tapack.io' send email to test mailbox with subject 'Test mailbox' and body 'Some text for test email body part!'

Scenario: Check inbox and verify received email smtps_imaps
Then verify that inbox contains letter from 'acceptancetestSMTPS_imaps@tapack.io' with 'Test mailbox' and 'Some text for test email body part!'

Scenario: Sent email to test mailbox with body from file smtps_imaps
When 'acceptancetestSMTPS_imaps@tapack.io' send email to test mailbox with subject 'Html content' and body from file '/example.html'

Scenario: Receive email and compare body to file smtps_imaps
Then verify that inbox contains letter from 'acceptancetestSMTPS_imaps@tapack.io' with 'Html content' and body from file '/example.html'