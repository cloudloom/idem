# idem

Curl command to get access token

    $ curl -v -X POST -H "Content-Type: application/json" -H "Authorization: Basic aWRlbS1hZG1pbjppZGVtLWFkbWluLXNlY3JldA==" "http://192.168.1.24:40080/idem/oauth/token?grant_type=password&username=user&password=password"

    {"access_token":"example_access_token","token_type":"bearer","refresh_token":"example_refresh_token","expires_in":41784,"scope":"idem-write idem-read","jti":"24939648-3cbb-42dd-aefd-7b46e11b860a"}

    $

Curl command to get authorities

    $ curl -v -X GET -H "Content-Type: application/json" -H "Authorization: Bearer example_access_token" "http://192.168.1.24:40080/idem/admin/authorities"

    [{"uid":"12285c20-85eb-4d57-98f0-ae4d3b12d855","passive":false,"role":"IDEM_ADMINISTRATOR"}]

    $

