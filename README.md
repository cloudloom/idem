# idem

<h5> Run as a stand alone server : </h5>
Download or clone from git and then use maven(3.*) and Java(1.8 or better)

    $ git clone https://github.com/tracebucket/idem.git
    $ mvn spring-boot:run 
  
<h5>Add to an existing project : </h5>  
 Add as a dependency like

    <dependency>
        <groupId>com.tracebucket.idem</groupId>
        <artifactId>idem</artifactId>
        <version>1.2</version>
    </dependency>
        
        <repository>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <id>bintray-tracebucket-X1</id>
            <name>bintray</name>
            <url>http://dl.bintray.com/tracebucket/X1</url>
        </repository>
        
        
add @EnableIdem (multiple X1 components can be enabled in the same config file) in a config class like below 

     @Configuration
     @EnableIdem
     public class X1Configuration{
     }
     
and the necessary beans for Idem will be enabled.

- Jpa configuration
- Service configuration
- Controller and Assembler configuration

<i> Curl command to get access token : </i>

    $ curl -v -X POST -H "Content-Type: application/json" -H "Authorization: Basic aWRlbS1hZG1pbjppZGVtLWFkbWluLXNlY3JldA==" "http://192.168.1.24:40080/idem/oauth/token?grant_type=password&username=user&password=password"

    {"access_token":"example_access_token","token_type":"bearer","refresh_token":"example_refresh_token","expires_in":41784,"scope":"idem-write idem-read","jti":"24939648-3cbb-42dd-aefd-7b46e11b860a"}

    $

<i> Curl command to get authorities : </i>

    $ curl -v -X GET -H "Content-Type: application/json" -H "Authorization: Bearer example_access_token" "http://192.168.1.24:40080/idem/admin/authorities"

    [{"uid":"12285c20-85eb-4d57-98f0-ae4d3b12d855","passive":false,"role":"IDEM_ADMINISTRATOR"}]

    $
    
<h5>Endpoints</h5>

<h5>Create Authority</h5>

    $curl -v -X POST -H "Content-Type: application/json" -d "{\"role\":\"ROLE_USER\"}" -H "Authorization: Bearer example_access_token" "http://localhost:40080/idem/admin/authority"
    
    * Adding handle: conn: 0x2013818
    * Adding handle: send: 0
    * Adding handle: recv: 0
    * Curl_addHandleToPipeline: length: 1
    * - Conn 0 (0x2013818) send_pipe: 1, recv_pipe: 0
    * About to connect() to localhost port 40080 (#0)
    *   Trying 127.0.0.1...
    * Connected to localhost (127.0.0.1) port 40080 (#0)
    > POST /idem/admin/authority HTTP/1.1
    > User-Agent: curl/7.30.0
    > Host: localhost:40080
    > Accept: */*
    > Content-Type: application/json
    > Authorization: Bearer example_access_token
    > Content-Length: 20
    >
    * upload completely sent off: 20 out of 20 bytes
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
    < Date: Wed, 03 Jun 2015 06:33:34 GMT
    <
    {"uid":"33c4e199-b5b1-474c-bd66-baff658ecb96","role":"ROLE_USER"}* Connection #0 to host localhost left intact
     
     $

<h5>Update Authority</h5>

    $curl -v -X PUT -H "Content-Type: application/json" -d "{\"uid\":\"33c4e199-b5b1-474c-bd66-baff658ecb96\", \"role\":\"ROLE_ADMIN\"}" -H "Authorization: Bearer example_access_token" "http://localhost:40080/idem/admin/authority"
    
    * Adding handle: conn: 0x953868
    * Adding handle: send: 0
    * Adding handle: recv: 0
    * Curl_addHandleToPipeline: length: 1
    * - Conn 0 (0x953868) send_pipe: 1, recv_pipe: 0
    * About to connect() to localhost port 40080 (#0)
    *   Trying 127.0.0.1...
    * Connected to localhost (127.0.0.1) port 40080 (#0)
    > PUT /idem/admin/authority HTTP/1.1
    > User-Agent: curl/7.30.0
    > Host: localhost:40080
    > Accept: */*
    > Content-Type: application/json
    > Authorization: Bearer example_access_token
    > Content-Length: 25
    >
    * upload completely sent off: 25 out of 25 bytes
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
    < Date: Wed, 03 Jun 2015 06:59:30 GMT
    <
    {"uid":"33c4e199-b5b1-474c-bd66-baff658ecb96","role":"ROLE_ADMIN\"}* Connection #0 to host localhost left intact
    
    $

<h5>Get Authority</h5>

    $curl -v -X GET -H "Content-Type: application/json" -H "Authorization: Bearer example_access_token" "http://localhost:40080/idem/admin/authority/33c4e199-b5b1-474c-bd66-baff658ecb96"
    
    * Adding handle: conn: 0x2113910
    * Adding handle: send: 0
    * Adding handle: recv: 0
    * Curl_addHandleToPipeline: length: 1
    * - Conn 0 (0x2113910) send_pipe: 1, recv_pipe: 0
    * About to connect() to localhost port 40080 (#0)
    *   Trying 127.0.0.1...
    * Connected to localhost (127.0.0.1) port 40080 (#0)
    > GET /idem/admin/authority/33c4e199-b5b1-474c-bd66-baff658ecb96 HTTP/1.1
    > User-Agent: curl/7.30.0
    > Host: localhost:40080
    > Accept: */*
    > Content-Type: application/json
    > Authorization: Bearer example_access_token
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
    < Date: Wed, 03 Jun 2015 07:09:03 GMT
    <
    {"uid":"33c4e199-b5b1-474c-bd66-baff658ecb96","role":"ROLE_ADMIN"}* Connection #0 to host localhost left intact
    
    $

<h5>Get Authorities</h5>

    $curl -v -X GET -H "Content-Type: application/json" -H "Authorization: Bearer example_access_token" "http://localhost:40080/idem/admin/authorities"
    
    * Adding handle: conn: 0x1f43778
    * Adding handle: send: 0
    * Adding handle: recv: 0
    * Curl_addHandleToPipeline: length: 1
    * - Conn 0 (0x1f43778) send_pipe: 1, recv_pipe: 0
    * About to connect() to localhost port 40080 (#0)
    *   Trying 127.0.0.1...
    * Connected to localhost (127.0.0.1) port 40080 (#0)
    > GET /idem/admin/authorities HTTP/1.1
    > User-Agent: curl/7.30.0
    > Host: localhost:40080
    > Accept: */*
    > Content-Type: application/json
    > Authorization: Bearer example_access_token
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
    < Date: Wed, 03 Jun 2015 07:13:33 GMT
    <
    [{"uid":"33c4e199-b5b1-474c-bd66-baff658ecb96","role":"ROLE_ADMIN\"}]* Connection #0 to host localhost left intact
    
    $

<h5>Delete Authority</h5>

    $curl -v -X DELETE -H "Content-Type: application/json" -H "Authorization: Bearer example_access_token" "http://localhost:40080/idem/admin/authority/33c4e199-b5b1-474c-bd66-baff658ecb96"
    
    * Adding handle: conn: 0x2143910
    * Adding handle: send: 0
    * Adding handle: recv: 0
    * Curl_addHandleToPipeline: length: 1
    * - Conn 0 (0x2143910) send_pipe: 1, recv_pipe: 0
    * About to connect() to localhost port 40080 (#0)
    *   Trying 127.0.0.1...
    * Connected to localhost (127.0.0.1) port 40080 (#0)
    > DELETE /idem/admin/authority/33c4e199-b5b1-474c-bd66-baff658ecb96 HTTP/1.1
    > User-Agent: curl/7.30.0
    > Host: localhost:40080
    > Accept: */*
    > Content-Type: application/json
    > Authorization: Bearer example_access_token
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
    < Date: Wed, 03 Jun 2015 07:31:23 GMT
    <
    true* Connection #0 to host localhost left intact
    
    $

<h5>Create Client</h5>

    $curl -v -X POST -H "Content-Type: application/json" -d "{\"clientId\":\"gateway\",\"clientSecret\":\"gateway-secret\",\"scope\":[\"read\",\"write\"],\"resourceIds\":[],\"authorizedGrantTypes\":[\"authorization_code\",\"refresh_token\",\"password\"],\"authorities\":[{\"role\":\"GATEWAY_USER\"}],\"additionalInformation\":{}}" -H "Authorization: Bearer example_access_token" "http://localhost:40080/idem/admin/client"
    
    * Adding handle: conn: 0xb03c78
    * Adding handle: send: 0
    * Adding handle: recv: 0
    * Curl_addHandleToPipeline: length: 1
    * - Conn 0 (0xb03c78) send_pipe: 1, recv_pipe: 0
    * About to connect() to localhost port 40080 (#0)
    *   Trying 127.0.0.1...
    * Connected to localhost (127.0.0.1) port 40080 (#0)
    > POST /idem/admin/client HTTP/1.1
    > User-Agent: curl/7.30.0
    > Host: localhost:40080
    > Accept: */*
    > Content-Type: application/json
    > Authorization: Bearer example_access_token
    > Content-Length: 236
    >
    * upload completely sent off: 236 out of 236 bytes
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
    < Date: Wed, 03 Jun 2015 07:38:55 GMT
    <
    {"uid":"064033ec-01e5-4c94-9e66-d6bf0b43046a","clientId":"gateway","clientSecret":"gateway-secret","scope":["write","read"],"resourceIds":[],"authorizedGrantTypes":["refresh_token","password","authorization_code"],"registeredRedirectUris":[],"autoApproveScopes":[],"authorities":[{"uid":"4f8895cd-93ca-4ce8-b872-bd74188444fd","role":"GATEWAY_USER"}],"additionalInformation":{}}* Connection #0 to host localhost left intact
    
    $

<h5>Update Client</h5> 

    $curl -v -X PUT -H "Content-Type: application/json" -d "{\"uid\":\"064033ec-01e5-4c94-9e66-d6bf0b43046a\",\"clientId\":\"gateway\",\"clientSecret\":\"gateway-secret\",\"scope\":[],\"resourceIds\":[],\"authorizedGrantTypes\":[],\"registeredRedirectUris\":[],\"autoApproveScopes\":[],\"authorities\":[{\"uid\":\"4f8895cd-93ca-4ce8-b872-bd74188444fd\",\"role\":\"GATEWAY_USER\"}],\"additionalInformation\":{}}" -H "Authorization: Bearer example_access_token" "http://localhost:40080/idem/admin/client"
    
    * Adding handle: conn: 0x2013de8
    * Adding handle: send: 0
    * Adding handle: recv: 0
    * Curl_addHandleToPipeline: length: 1
    * - Conn 0 (0x2013de8) send_pipe: 1, recv_pipe: 0
    * About to connect() to localhost port 40080 (#0)
    *   Trying 127.0.0.1...
    * Connected to localhost (127.0.0.1) port 40080 (#0)
    > PUT /idem/admin/client HTTP/1.1
    > User-Agent: curl/7.30.0
    > Host: localhost:40080
    > Accept: */*
    > Content-Type: application/json
    > Authorization: Bearer example_access_token
    > Content-Length: 316
    >
    * upload completely sent off: 316 out of 316 bytes
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
    < Date: Wed, 03 Jun 2015 07:46:10 GMT
    <
    {"uid":"064033ec-01e5-4c94-9e66-d6bf0b43046a","clientId":"gateway","clientSecret":"gateway-secret","scope":[],"resourceIds":[],"authorizedGrantTypes":[],"registeredRedirectUris":[],"autoApproveScopes":[],"authorities":[{"uid":"4f8895cd-93ca-4ce8-b872-bd74188444fd","role":"GATEWAY_USER"}],"additionalInformation":{}}* Connection #0 to host localhost left intact
    
    $
    
<h5>Get Client</h5>

    $curl -v -X GET -H "Content-Type: application/json" -H "Authorization: Bearer example_access_token" "http://localhost:40080/idem/admin/client/gateway"
    
    * Adding handle: conn: 0x9537d0
    * Adding handle: send: 0
    * Adding handle: recv: 0
    * Curl_addHandleToPipeline: length: 1
    * - Conn 0 (0x9537d0) send_pipe: 1, recv_pipe: 0
    * About to connect() to localhost port 40080 (#0)
    *   Trying 127.0.0.1...
    * Connected to localhost (127.0.0.1) port 40080 (#0)
    > GET /idem/admin/client/gateway HTTP/1.1
    > User-Agent: curl/7.30.0
    > Host: localhost:40080
    > Accept: */*
    > Content-Type: application/json
    > Authorization: Bearer example_access_token
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
    < Date: Wed, 03 Jun 2015 07:53:07 GMT
    <
    {"uid":"064033ec-01e5-4c94-9e66-d6bf0b43046a","clientId":"gateway","clientSecret":"gateway-secret","scope":[],"resourceIds":[],"authorizedGrantTypes":[],"registeredRedirectUris":[],"autoApproveScopes":[],"authorities":[{"uid":"4f8895cd-93ca-4ce8-b872-bd74188444fd","role":"GATEWAY_USER"}],"additionalInformation":{}}* Connection #0 to host localhost left intact
    
    $

<h5>Get Clients</h5>

    $curl -v -X GET -H "Content-Type: application/json" -H "Authorization: Bearer example_access_token" "http://localhost:40080/idem/admin/clients"
    
    * Adding handle: conn: 0x21e3768
    * Adding handle: send: 0
    * Adding handle: recv: 0
    * Curl_addHandleToPipeline: length: 1
    * - Conn 0 (0x21e3768) send_pipe: 1, recv_pipe: 0
    * About to connect() to localhost port 40080 (#0)
    *   Trying 127.0.0.1...
    * Connected to localhost (127.0.0.1) port 40080 (#0)
    > GET /idem/admin/clients HTTP/1.1
    > User-Agent: curl/7.30.0
    > Host: localhost:40080
    > Accept: */*
    > Content-Type: application/json
    > Authorization: Bearer example_access_token
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
    < Date: Wed, 03 Jun 2015 07:55:02 GMT
    <
    [{"uid":"064033ec-01e5-4c94-9e66-d6bf0b43046a","clientId":"gateway","clientSecret":"gateway-secret","scope":[],"resourceIds":[],"authorizedGrantTypes":[],"registeredRedirectUris":[],"autoApproveScopes":[],"authorities":[{"uid":"4f8895cd-93ca-4ce8-b872-bd74188444fd","role":"GATEWAY_USER"}],"additionalInformation":{}}]*Connection #0 to host localhost left intact
    
    $
    
<h5>Update Client Secret</h5>

    $curl -v -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer example_access_token" "http://localhost:40080/idem/admin/client/gateway/secret?clientSecret=updated-gateway-secret"
    
    * Adding handle: conn: 0x2193b98
    * Adding handle: send: 0
    * Adding handle: recv: 0
    * Curl_addHandleToPipeline: length: 1
    * - Conn 0 (0x2193b98) send_pipe: 1, recv_pipe: 0
    * About to connect() to localhost port 40080 (#0)
    *   Trying 127.0.0.1...
    * Connected to localhost (127.0.0.1) port 40080 (#0)
    > PUT /idem/admin/client/gateway/secret?clientSecret=updated-gateway-secret HTTP
    /1.1
    > User-Agent: curl/7.30.0
    > Host: localhost:40080
    > Accept: */*
    > Content-Type: application/json
    > Authorization: Bearer example_access_token
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
    < Date: Wed, 03 Jun 2015 08:00:23 GMT
    <
    {"uid":"064033ec-01e5-4c94-9e66-d6bf0b43046a","clientId":"gateway","clientSecret":"updated-gateway-secret","scope":[],"resourceIds":[],"authorizedGrantTypes":[],"registeredRedirectUris":[],"autoApproveScopes":[],"authorities":[{"uid":"4f8895cd-93ca-4ce8-b872-bd74188444fd","role":"GATEWAY_USER"}],"additionalInformation":{}}* Connection #0 to host localhost left intact
    
    $
    
<h5>Delete Client</h5>

    $curl -v -X DELETE -H "Content-Type: application/json" -H "Authorization: Bearer example_access_token" "http://localhost:40080/idem/admin/client/gateway"
    
    * Adding handle: conn: 0x22a37d0
    * Adding handle: send: 0
    * Adding handle: recv: 0
    * Curl_addHandleToPipeline: length: 1
    * - Conn 0 (0x22a37d0) send_pipe: 1, recv_pipe: 0
    * About to connect() to localhost port 40080 (#0)
    *   Trying 127.0.0.1...
    * Connected to localhost (127.0.0.1) port 40080 (#0)
    > DELETE /idem/admin/client/gateway HTTP/1.1
    > User-Agent: curl/7.30.0
    > Host: localhost:40080
    > Accept: */*
    > Content-Type: application/json
    > Authorization: Bearer example_access_token
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
    < Date: Wed, 03 Jun 2015 08:03:27 GMT
    <
    true* Connection #0 to host localhost left intact
    
    $
    
<h5>Create Group</h5>

    $curl -v -X POST -H "Content-Type: application/json" -d "{\"name\":\"Admin\",\"description\":\"Admin Group\"}" -H "Authorization: Bearer example_access_token" "http://localhost:40080/idem/admin/group"
    
    * Adding handle: conn: 0x2133888
    * Adding handle: send: 0
    * Adding handle: recv: 0
    * Curl_addHandleToPipeline: length: 1
    * - Conn 0 (0x2133888) send_pipe: 1, recv_pipe: 0
    * About to connect() to localhost port 40080 (#0)
    *   Trying 127.0.0.1...
    * Connected to localhost (127.0.0.1) port 40080 (#0)
    > POST /idem/admin/group HTTP/1.1
    > User-Agent: curl/7.30.0
    > Host: localhost:40080
    > Accept: */*
    > Content-Type: application/json
    > Authorization: Bearer example_access_token
    > Content-Length: 44
    >
    * upload completely sent off: 44 out of 44 bytes
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
    < Date: Wed, 03 Jun 2015 08:18:23 GMT
    <
    {"uid":"9d3e41b7-ceda-46a0-8f18-8d5d10bebc78","name":"Admin","authorities":[],"members":[]}* Connection #0 to host localhost left intact
    
    $
