Scenario: User starts fake local mail servers smtp_imaps
Given start fake local mail servers

Scenario: User want to use SMTP and imaps protocols in testing
Given send protocol 'smtp' and receive protocol 'imaps'

Scenario: Send email to smtp server smtp_imaps
When 'acceptancetestSMTP_imaps@tapack.io' send email to test mailbox with subject 'Test mailbox SMTP imaps' and body 'Some text for test email body part!'
And 'acceptancetestSMTP_imaps@tapack.io' send email to test mailbox with subject 'Test mailbox SMTP imaps' and body 'Some text for negative scenario'
And 'acceptancetestSMTP_imaps@tapack.io' send email to test mailbox with subject 'Test negative mailbox SMTP imaps' and body 'Some text for test email body part!'
And 'acceptancetestSMTP_imaps@tapack.io' send email to test mailbox with subject 'Test mailbox SMTP imaps not' and body 'Some text for test email body part!'

Scenario: Check inbox and verify received email smtp_imaps
Then verify that inbox contains letter from 'acceptancetestSMTP_imaps@tapack.io' with 'Test mailbox SMTP imaps' and 'Some text for test email body part!'

Scenario: Sent email to test mailbox with body from file smtp_imaps
When 'acceptancetestSMTP_imaps@tapack.io' send email to test mailbox with subject 'Html content SMTP imaps' and body from file '/example.html'

Scenario: Receive email and compare body to file smtp_imaps
Then verify that inbox contains letter from 'acceptancetestSMTP_imaps@tapack.io' with 'Html content SMTP imaps' and body from file '/example.html'