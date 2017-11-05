Scenario: User starts fake local mail servers smtp_imap
Given start fake local mail servers

Scenario: User want to use SMTP and imap protocols in testing
Given send protocol 'smtp' and receive protocol 'imap'

Scenario: Send email to smtp server smtp_imap
When 'acceptancetestSMTP_imap@tapack.io' send email to test mailbox with subject 'Test mailbox SMTP imap' and body 'Some text for test email body part!'
And 'acceptancetestSMTP_imap@tapack.io' send email to test mailbox with subject 'Test mailbox SMTP imap' and body 'Some text for negative scenario'
And 'acceptancetestSMTP_imap@tapack.io' send email to test mailbox with subject 'Test negative mailbox SMTP imap' and body 'Some text for test email body part!'
And 'acceptancetestSMTP_imap@tapack.io' send email to test mailbox with subject 'Test mailbox SMTP imap not' and body 'Some text for test email body part!'

Scenario: Check inbox and verify received email smtp_imap
Then verify that inbox contains letter from 'acceptancetestSMTP_imap@tapack.io' with 'Test mailbox SMTP imap' and 'Some text for test email body part!'

Scenario: Sent email to test mailbox with body from file smtp_imap
When 'acceptancetestSMTP_imap@tapack.io' send email to test mailbox with subject 'Html content SMTP imap' and body from file '/example.html'

Scenario: Receive email and compare body to file smtp_imap
Then verify that inbox contains letter from 'acceptancetestSMTP_imap@tapack.io' with 'Html content SMTP imap' and body from file '/example.html'