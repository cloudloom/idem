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
    
<h5>Idem Endpoints</h5>

    BasePath : http://localhost:40080/idem
    
    Endpoints : 
    
    {[/oauth/error],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}
    {[/oauth/confirm_access],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}
    {[/user],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}
    {[/admin/authority],methods=[PUT],params=[],headers=[],consumes=[application/json],produces=[application/json],custom=[]}
    {[/admin/authority/{uid}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/authority],methods=[POST],params=[],headers=[],consumes=[application/json],produces=[application/json],custom=[]}
    {[/admin/authority/{uid}],methods=[GET],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/authorities],methods=[DELETE],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/authorities],methods=[GET],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/client/{clientId}/secret],methods=[PUT],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/client/{clientId}],methods=[GET],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/client],methods=[PUT],params=[],headers=[],consumes=[application/json],produces=[application/json],custom=[]}
    {[/admin/client],methods=[POST],params=[],headers=[],consumes=[application/json],produces=[application/json],custom=[]}
    {[/admin/clients],methods=[GET],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/client/{clientId}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/group],methods=[POST],params=[],headers=[],consumes=[application/json],produces=[application/json],custom=[]}
    {[/admin/group/{groupName}/authorities],methods=[GET],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/group/{groupName}],methods=[PUT],params=[],headers=[],consumes=[application/json],produces=[application/json],custom=[]}
    {[/admin/group/{groupName}/authority],methods=[PUT],params=[],headers=[],consumes=[application/json],produces=[application/json],custom=[]} {[/admin/groups],methods=[GET],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/group/{groupName}/users],methods=[GET],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/group/{groupName}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/group/{oldName}/{newName}],methods=[PUT],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/group/{groupName}],methods=[GET],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/user/{userName}/group/{groupName}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/user/{userName}],methods=[GET],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/user/{userName}/group/{groupName}],methods=[PUT],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/user],methods=[POST],params=[],headers=[],consumes=[application/json],produces=[application/json],custom=[]}
    {[/admin/user],methods=[PUT],params=[],headers=[],consumes=[application/json],produces=[application/json],custom=[]}
    {[/admin/user/{userName}],methods=[DELETE],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/user/password],methods=[PUT],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/admin/user/{userName}/exists],methods=[GET],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}
    {[/error],methods=[],params=[],headers=[],consumes=[],produces=[],custom=[]}
    {[/error],methods=[],params=[],headers=[],consumes=[],produces=[text/html],custom=[]}
    [/login]
    [/oauth/confirm_access]
    
