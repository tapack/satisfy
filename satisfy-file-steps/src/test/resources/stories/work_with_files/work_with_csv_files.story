Scenario: User opens Data Exporter page
When the user load '/static-test-example/data/engine_input_records.csv' file with key 'fileDownloadKey'

Scenario: Verify downloaded CSV file and verify text content
Then verify that csv file contains text '172534,FL,CLAY COUNTY,0,254281.5,0,254281.5,254281.5,246144.49,0,0,0,0,30.060614,-81.702675,Residential,Wood,1' with 'fileDownloadKey'

Scenario: Verify  CSV does not contain tex
Then verify that csv file does not contains text '673270054,4119331991320' with 'fileDownloadKey'

Scenario: Verify downloaded CSV file is equal  to another csv
Then verify that csv text content is equal to '/static-test-example/data/engine_input_records_copy.csv' text content in file with key 'fileDownloadKey'

Scenario: Create CSV file and verify it's content
Given csv file 'one' with content:
|policyID|statecode|county|eq_site_limit|hu_site_limit|fl_site_limit|fr_site_limit|tiv_2011|tiv_2012|eq_site_deductible|hu_site_deductible|fl_site_deductible|fr_site_deductible|point_latitude|point_longitude|line|construction|point_granularity|
|119736|FL|CLAY COUNTY|498960|498960|498960|498960|498960|792148.9|0|9979.2|0|0|30.102261|-81.711777|Residential|Masonry|1|
|448094|FL|CLAY COUNTY|1322376.3|1322376.3|1322376.3|1322376.3|1322376.3|1438163.57|0|0|0|0|30.063936|-81.707664|Residential|Masonry|3|
|206893|FL|CLAY COUNTY|190724.4|190724.4|190724.4|190724.4|190724.4|192476.78|0|0|0|0|30.089579|-81.700455|Residential|Wood|1|
|333743|FL|CLAY COUNTY|0|79520.76|0|0|79520.76|86854.48|0|0|0|0|30.063236|-81.707703|Residential|Wood|3|
|172534|FL|CLAY COUNTY|0|254281.5|0|254281.5|254281.5|246144.49|0|0|0|0|30.060614|-81.702675|Residential|Wood|1|
|785275|FL|CLAY COUNTY|0|515035.62|0|0|515035.62|884419.17|0|0|0|0|30.063236|-81.707703|Residential|Masonry|3|
|995932|FL|CLAY COUNTY|0|19260000|0|0|19260000|20610000|0|0|0|0|30.102226|-81.713882|Commercial|Reinforced Concrete|1|
|223488|FL|CLAY COUNTY|328500|328500|328500|328500|328500|348374.25|0|16425|0|0|30.102217|-81.707146|Residential|Wood|1|
|433512|FL|CLAY COUNTY|315000|315000|315000|315000|315000|265821.57|0|15750|0|0|30.118774|-81.704613|Residential|Wood|1|
|142071|FL|CLAY COUNTY|705600|705600|705600|705600|705600|1010842.56|14112|35280|0|0|30.100628|-81.703751|Residential|Masonry|1|
|253816|FL|CLAY COUNTY|831498.3|831498.3|831498.3|831498.3|831498.3|1117791.48|0|0|0|0|30.10216|-81.719444|Residential|Masonry|1|
Then verify that csv text content is equal to '/static-test-example/data/engine_input_records.csv' text content in file with key 'one'