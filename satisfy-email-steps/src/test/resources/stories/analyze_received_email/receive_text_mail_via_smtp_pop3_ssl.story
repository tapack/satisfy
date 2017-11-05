Scenario: User starts fake local mail servers smtp_pop3s
Given start fake local mail servers

Scenario: User want to use smtp and pop3 ssl protocols in testing
Given send protocol 'smtp' and receive protocol 'pop3s'

Scenario: Send email smtp_pop3s
When 'acceptancetestSMTP_POP3S@tapack.io' send email to test mailbox with subject 'Test mailbox' and body 'Some text for test email body part!'
And 'acceptancetestSMTP_POP3S@tapack.io' send email to test mailbox with subject 'Test mailbox' and body 'Some text for negative scenario'
And 'acceptancetestSMTP_POP3S@tapack.io' send email to test mailbox with subject 'Test negative mailbox' and body 'Some text for test email body part!'
And 'acceptancetestSMTP_POP3S@tapack.io' send email to test mailbox with subject 'Test mailbox' and body 'Some text for test email body part!'

Scenario: Check inbox and verify received email smtp_pop3s
Then verify that inbox contains letter from 'acceptancetestSMTP_POP3S@tapack.io' with 'Test mailbox' and 'Some text for test email body part!'

Scenario: Sent email to test mailbox with body from file smtp_pop3s
When 'acceptancetestSMTP_POP3S@tapack.io' send email to test mailbox with subject 'Html content' and body from file '/example.html'

Scenario: Receive email and compare body to file smtp_pop3s
Then verify that inbox contains letter from 'acceptancetestSMTP_POP3S@tapack.io' with 'Html content' and body from file '/example.html'