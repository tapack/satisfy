Scenario: User starts fake local mail servers smtps_imap
Given start fake local mail servers

Scenario: User want to use smtp ssl and imap protocols in testing
Given send protocol 'smtps' and receive protocol 'imap'

Scenario: Send email smtps_imap
When 'acceptancetestSMTPS_imap@tapack.io' send email to test mailbox with subject 'Test mailbox' and body 'Some text for test email body part!'
And 'acceptancetestSMTPS_imap@tapack.io' send email to test mailbox with subject 'Test mailbox' and body 'Some text for negative scenario'
And 'acceptancetestSMTPS_imap@tapack.io' send email to test mailbox with subject 'Test negative mailbox' and body 'Some text for test email body part!'
And 'badacceptancetestSMTPS_imap@tapack.io' send email to test mailbox with subject 'Test mailbox' and body 'Some text for test email body part!'

Scenario: Check inbox and verify received email smtps_imap
Then verify that inbox contains letter from 'acceptancetestSMTPS_imap@tapack.io' with 'Test mailbox' and 'Some text for test email body part!'

Scenario: Sent email to test mailbox with body from file smtps_imap
When 'acceptancetestSMTPS_imap@tapack.io' send email to test mailbox with subject 'Html content' and body from file '/example.html'

Scenario: Receive email and compare body to file smtps_imap
Then verify that inbox contains letter from 'acceptancetestSMTPS_imap@tapack.io' with 'Html content' and body from file '/example.html'