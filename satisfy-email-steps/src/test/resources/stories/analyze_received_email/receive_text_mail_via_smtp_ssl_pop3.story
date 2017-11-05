Scenario: User starts fake local mail servers smtps_pop3
Given start fake local mail servers

Scenario: User want to use smtp ssl and pop3 protocols in testing
Given send protocol 'smtps' and receive protocol 'pop3'

Scenario: Send email smtps_pop3
When 'acceptancetestSMTPS_POP3@tapack.io' send email to test mailbox with subject 'Test mailbox' and body 'Some text for test email body part!'
And 'acceptancetestSMTPS_POP3@tapack.io' send email to test mailbox with subject 'Test mailbox' and body 'Some text for negative scenario'
And 'acceptancetestSMTPS_POP3@tapack.io' send email to test mailbox with subject 'Test negative mailbox' and body 'Some text for test email body part!'
And 'badacceptancetestSMTPS_POP3@tapack.io' send email to test mailbox with subject 'Test mailbox' and body 'Some text for test email body part!'

Scenario: Check inbox and verify received email smtps_pop3
Then verify that inbox contains letter from 'acceptancetestSMTPS_POP3@tapack.io' with 'Test mailbox' and 'Some text for test email body part!'

Scenario: Sent email to test mailbox with body from file smtps_pop3
When 'acceptancetestSMTPS_POP3@tapack.io' send email to test mailbox with subject 'Html content' and body from file '/example.html'

Scenario: Receive email and compare body to file smtps_pop3
Then verify that inbox contains letter from 'acceptancetestSMTPS_POP3@tapack.io' with 'Html content' and body from file '/example.html'