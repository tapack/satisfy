Scenario: User starts fake local mail servers smtps_pop3s
Given start fake local mail servers

Scenario: User want to use smtps and pop3s protocols in testing
Given send protocol 'smtps' and receive protocol 'pop3s'

Scenario: Send email to smtps server smtps_pop3s
When 'acceptancetestsmtps_pop3s@tapack.io' send email to test mailbox with subject 'Test mailbox smtps pop3s' and body 'Some text for test email body part!'
And 'acceptancetestsmtps_pop3s@tapack.io' send email to test mailbox with subject 'Test mailbox smtps pop3s' and body 'Some text for negative scenario'
And 'acceptancetestsmtps_pop3s@tapack.io' send email to test mailbox with subject 'Test negative mailbox smtps pop3s' and body 'Some text for test email body part!'
And 'acceptancetestsmtps_pop3s@tapack.io' send email to test mailbox with subject 'Test mailbox smtps pop3s not' and body 'Some text for test email body part!'

Scenario: Check inbox and verify received email smtps_pop3s
Then verify that inbox contains letter from 'acceptancetestsmtps_pop3s@tapack.io' with 'Test mailbox smtps pop3s' and 'Some text for test email body part!'

Scenario: Sent email to test mailbox with body from file smtps_pop3s
When 'acceptancetestsmtps_pop3s@tapack.io' send email to test mailbox with subject 'Html content smtps pop3s' and body from file '/example.html'

Scenario: Receive email and compare body to file smtps_pop3s
Then verify that inbox contains letter from 'acceptancetestsmtps_pop3s@tapack.io' with 'Html content smtps pop3s' and body from file '/example.html'