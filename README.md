# BayBank Test

provide JAVA Restful API for user to register and get user information after registration. Try your best to deliver 2 API services with security,
Submit source code on GIT control with document

Use Technology.
- Java Spring Boot
- Mockito
- JWT

![image](https://imgur.com/H5wVi6c.jpg)


#After install git, MongoDb, Postman on your computer.
- Create Folder BayTest
- Type git init
- git clone https://github.com/UPThawatchai/BayBankSpringBoot.git
- cd BayTest/BayBankSpringBoot And Open command line
- Type mvn spring-boot:run

# Usage example Postman call Restful API
- Open Postman

Config Postman tab 1
 1) Method post
 2) Url : http://localhost:8080/authenticate
 3) Add body (Username baybank only)
       ```json
       {
          "username" : "baybank",
          "password" : "password"
       }
       ```
 4) Send and method return token and coppy
       
Config Postman tab 2
 1) Method post
 2) Url : http://localhost:8080/register
 3) Add body (Username baybank only)
       ```json
       {
            "username" : "baybank",
            "password" : "register",
            "address" : "109/207 Bloc20 Resident",
            "phone" : "0988358950",
            "email" : "siseof@gmail.com",
            "firstName" : "Thawatchai",
            "lastName" : "Thamkantee",
            "salary" : 16000
        }
       ```
 4) Tab Header Add key "Authorization" and put value from token (Tab 1.4)
 - Ex. Bearar : eyJhbGciOiJIUzUxMiJ9
 5) Send
        
Config Postman tab 3     
 1) Method Get
 2) Url : http://localhost:8080/findAll
 3) Tab Header Add key "Authorization" and put value from token (Tab 1.4)
 - Ex. Bearar : eyJhbGciOiJIUzUxMiJ9
 5) Send
