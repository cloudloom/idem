# idem

Curl command to get access token
$ curl -v -X POST -H "Content-Type: application/json" -H "Authorization: Basic aWRlbS1hZG1pbjppZGVtLWFkbWluLXNlY3JldA==" "http://192.168.1.24:40080/idem/oauth/token?grant_type=password&username=user&password=password"
* Hostname was NOT found in DNS cache
*   Trying 192.168.1.24...
* Connected to 192.168.1.24 (192.168.1.24) port 40080 (#0)
> POST /idem/oauth/token?grant_type=password&username=user&password=password HTTP/1.1
> User-Agent: curl/7.37.1
> Host: 192.168.1.24:40080
> Accept: */*
> Content-Type: application/json
> Authorization: Basic aWRlbS1hZG1pbjppZGVtLWFkbWluLXNlY3JldA==
> 
< HTTP/1.1 200 OK
* Server Apache-Coyote/1.1 is not blacklisted
< Server: Apache-Coyote/1.1
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< X-Application-Context: application:40080
< Cache-Control: no-store
< Pragma: no-cache
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Thu, 14 May 2015 13:01:12 GMT
< 
* Connection #0 to host 192.168.1.24 left intact
{"access_token":"eyJhbGciOiJSUzI1NiJ9.eyJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsiaWRlbS13cml0ZSIsImlkZW0tcmVhZCJdLCJURU5BTlRfTkFNRSI6IlRuVCIsIlRFTkFOVF9JRCI6IjEyMzQ1IiwiZXhwIjoxNDMxNjUwMjU3LCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImp0aSI6IjI0OTM5NjQ4LTNjYmItNDJkZC1hZWZkLTdiNDZlMTFiODYwYSIsImNsaWVudF9pZCI6ImlkZW0tYWRtaW4ifQ.gmYQbXZvQfOAYk0Jn97-8w4a2x54jBWFC0BV-RNVXvsfjTgUf0selmKH6089uYPJ1ccQwR1lhL-eOu48XrtgxePK8ntkrTj1mjY0qr3HQ9sIS1VLMgFYCQhaTGM99u_Jn9sb9L8WEB74TLj1uJFqXLGdGHJrxnFqG5OixcQOUuDbvi5Wff07c02egi4wCfBetbmdmzWfcFOEuNRlZSovrijTcVtvlIigMA2hOnQXolUya-4WI4V7MoV7tCyxGpBe4MFvCF7PHU5AZKrppFWoQsfQkA4pjP14te2S62zmd3yhhNSUS1zHVa7lc_0uicS_sOqjy0DJLNpOptt8PLOeJQ","token_type":"bearer","refresh_token":"eyJhbGciOiJSUzI1NiJ9.eyJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsiaWRlbS13cml0ZSIsImlkZW0tcmVhZCJdLCJhdGkiOiIyNDkzOTY0OC0zY2JiLTQyZGQtYWVmZC03YjQ2ZTExYjg2MGEiLCJURU5BTlRfTkFNRSI6IlRuVCIsIlRFTkFOVF9JRCI6IjEyMzQ1IiwiZXhwIjoxNDM0MTk5MDU3LCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImp0aSI6ImQwYWI3M2MyLWYxZDYtNDdlYy05NDc1LTZhMTE1Y2I3YTYzOCIsImNsaWVudF9pZCI6ImlkZW0tYWRtaW4ifQ.hsesAmkjJziGFrZyR63g0q06WWfY0aYDRI2E8lrJV_-dbZo2HAOKR44ZDkWMnSdf1MSW3dmZV7EA4gqBCUG97gfc3M6yjS2DBytzzICO6EKcPlyS_YWzVdrPoV-8WZAyO6NoMIlpGLMJD_oYramcdsLe4dq6IUXJ2DVzI3-LEGAUCjg6iIZGjjbUCRj8uUIH_X1sXs7Kihc8IonpR_jYqOUnkODYVHvMvmU6yX51QlqZx9eM_soAuat6NjAxOZ1oLtqqaGzI-sM0JxspKRiknJXuoxnVpbsNXuk6wsmMUmrONx7OIedXKRc7aR125whcVlm-aXFK7lz1-7FaSylEZA","expires_in":41784,"scope":"idem-write idem-read","TENANT_NAME":"TnT","TENANT_ID":"12345","jti":"24939648-3cbb-42dd-aefd-7b46e11b860a"}
$
Curl command to get authorities
$ curl -v -X GET -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsiaWRlbS13cml0ZSIsImlkZW0tcmVhZCJdLCJURU5BTlRfTkFNRSI6IlRuVCIsIlRFTkFOVF9JRCI6IjEyMzQ1IiwiZXhwIjoxNDMxNjUwMjU3LCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImp0aSI6IjI0OTM5NjQ4LTNjYmItNDJkZC1hZWZkLTdiNDZlMTFiODYwYSIsImNsaWVudF9pZCI6ImlkZW0tYWRtaW4ifQ.gmYQbXZvQfOAYk0Jn97-8w4a2x54jBWFC0BV-RNVXvsfjTgUf0selmKH6089uYPJ1ccQwR1lhL-eOu48XrtgxePK8ntkrTj1mjY0qr3HQ9sIS1VLMgFYCQhaTGM99u_Jn9sb9L8WEB74TLj1uJFqXLGdGHJrxnFqG5OixcQOUuDbvi5Wff07c02egi4wCfBetbmdmzWfcFOEuNRlZSovrijTcVtvlIigMA2hOnQXolUya-4WI4V7MoV7tCyxGpBe4MFvCF7PHU5AZKrppFWoQsfQkA4pjP14te2S62zmd3yhhNSUS1zHVa7lc_0uicS_sOqjy0DJLNpOptt8PLOeJQ" "http://192.168.1.24:40080/idem/admin/authorities"
* Hostname was NOT found in DNS cache
*   Trying 192.168.1.24...
* Connected to 192.168.1.24 (192.168.1.24) port 40080 (#0)
> GET /idem/admin/authorities HTTP/1.1
> User-Agent: curl/7.37.1
> Host: 192.168.1.24:40080
> Accept: */*
> Content-Type: application/json
> Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsiaWRlbS13cml0ZSIsImlkZW0tcmVhZCJdLCJURU5BTlRfTkFNRSI6IlRuVCIsIlRFTkFOVF9JRCI6IjEyMzQ1IiwiZXhwIjoxNDMxNjUwMjU3LCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImp0aSI6IjI0OTM5NjQ4LTNjYmItNDJkZC1hZWZkLTdiNDZlMTFiODYwYSIsImNsaWVudF9pZCI6ImlkZW0tYWRtaW4ifQ.gmYQbXZvQfOAYk0Jn97-8w4a2x54jBWFC0BV-RNVXvsfjTgUf0selmKH6089uYPJ1ccQwR1lhL-eOu48XrtgxePK8ntkrTj1mjY0qr3HQ9sIS1VLMgFYCQhaTGM99u_Jn9sb9L8WEB74TLj1uJFqXLGdGHJrxnFqG5OixcQOUuDbvi5Wff07c02egi4wCfBetbmdmzWfcFOEuNRlZSovrijTcVtvlIigMA2hOnQXolUya-4WI4V7MoV7tCyxGpBe4MFvCF7PHU5AZKrppFWoQsfQkA4pjP14te2S62zmd3yhhNSUS1zHVa7lc_0uicS_sOqjy0DJLNpOptt8PLOeJQ
> 
< HTTP/1.1 200 OK
* Server Apache-Coyote/1.1 is not blacklisted
< Server: Apache-Coyote/1.1
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< X-Application-Context: application:40080
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Thu, 14 May 2015 13:04:58 GMT
< 
* Connection #0 to host 192.168.1.24 left intact
[{"uid":"12285c20-85eb-4d57-98f0-ae4d3b12d855","passive":false,"role":"IDEM_ADMINISTRATOR"}]
$

