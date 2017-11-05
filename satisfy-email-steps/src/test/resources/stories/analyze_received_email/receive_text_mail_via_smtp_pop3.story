Scenario: User starts fake local mail servers smtp_pop3
Given start fake local mail servers

Scenario: User want to use SMTP and POP3 protocols in testing
Given send protocol 'smtp' and receive protocol 'pop3'

Scenario: Send email to smtp server smtp_pop3
When 'acceptancetestSMTP_POP3@tapack.io' send email to test mailbox with subject 'Test mailbox SMTP POP3' and body 'Some text for test email body part!'
And 'acceptancetestSMTP_POP3@tapack.io' send email to test mailbox with subject 'Test mailbox SMTP POP3' and body 'Some text for negative scenario'
And 'acceptancetestSMTP_POP3@tapack.io' send email to test mailbox with subject 'Test negative mailbox SMTP POP3' and body 'Some text for test email body part!'
And 'acceptancetestSMTP_POP3@tapack.io' send email to test mailbox with subject 'Test mailbox SMTP POP3 not' and body 'Some text for test email body part!'

Scenario: Check inbox and verify received email smtp_pop3
Then verify that inbox contains letter from 'acceptancetestSMTP_POP3@tapack.io' with 'Test mailbox SMTP POP3' and 'Some text for test email body part!'

Scenario: Sent email to test mailbox with body from file smtp_pop3
When 'acceptancetestSMTP_POP3@tapack.io' send email to test mailbox with subject 'Html content SMTP POP3' and body from file '/example.html'

Scenario: Receive email and compare body to file smtp_pop3
Then verify that inbox contains letter from 'acceptancetestSMTP_POP3@tapack.io' with 'Html content SMTP POP3' and body from file '/example.html'