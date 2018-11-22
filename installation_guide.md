# AGHydra
### Backend application project installation guide
<br>

* Java 10
* Maven 3

#### PostgreSQL 
 * version: 10.5
 * recommended source: [enterprisedb.com](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)
 * go trough installer with default settings
 * type **'_postgres_'** if prompted for postgres user password
 
#### Intellij
 * clone project
 
 
 * install intellij **@lombok** plugin<br>
 ![](https://user-images.githubusercontent.com/23015353/48921646-6f584000-eea1-11e8-8645-6b695aa4f300.png)
 
 
 * create schema _**'hydra'**_ inside postgres database<br>
 ![](https://user-images.githubusercontent.com/23015353/48921762-3cfb1280-eea2-11e8-8ad8-4596adfef8ae.png)
 
 
 * run **_`mvn clean install`_**<br>
 ![](https://user-images.githubusercontent.com/23015353/48921818-aed35c00-eea2-11e8-98bc-382936fae91b.png)
 
 
 * migrate SQL scripts with flyway<br>
 ![](https://user-images.githubusercontent.com/23015353/48921945-8435d300-eea3-11e8-962e-c145082fd7eb.png)
 
 
 * run application<br>
 ![](https://user-images.githubusercontent.com/23015353/48922087-7b91cc80-eea4-11e8-97bb-f215984309c4.png)
 
 
<br>

 #### Postman collection
 ![](https://user-images.githubusercontent.com/23015353/48922407-cb719300-eea6-11e8-98b9-4c9da10a3afa.png)
 
 <br>
 
 #### REST API Swagger Docs
 * copy response of [http://localhost:5000/hydra/v2/api-docs](http://localhost:5000/hydra/v2/api-docs)
 * into [https://editor.swagger.io/#](https://editor.swagger.io/#)
 
 
 
