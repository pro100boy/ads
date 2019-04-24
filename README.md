### Short description

To launch the application follow the next steps:

  - clone repository to local computer: `git clone https://github.com/pro100boy/ads.git`
  - compile in maven: `mvn clean install -DskipTests=true`
  - run application: `mvn spring-boot:run` 
  - go to URL: `http://localhost:8080 `, Swagger with detailed description and documentation will be opened
  - try it!

### Comments
Application based on Spring boot & H2 DB. 
Database will be created during application start (file *schema-h2.sql*) and populated some test data (file *data-h2.sql*)

### Constants are used in application

* sorting by:
``` 
    NAME,   // by campaig name
    STATUS, // by campaig status
    ADS     // by campaign advertisiments count
```
* search order
```
    ASC,
    DESC
```
* statuses
```
    PLANNED,
    ACTIVE,
    PAUSED,
    FINISHED
```
### Sample search URL

`http://localhost:8080/api/v1/summaries?limit=10&offset=0&order=asc&search=adidas&sort=name&status=active`
