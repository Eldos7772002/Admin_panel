## **Spring-Security-Authentication**
___________________
#### _The Spring Security Authentication project is a RESTful API service designed to provide user authentication and authorization using JWT (JSON Web Token). This service offers endpoints for registering new users, logging in existing users, and refreshing JWT tokens._
________
## Functionality
#### 1. Register New User
+ **POST /auth/signup:** Register a new user in the system.
  + Request parameters: `name`, `email`, `password`, `role`.
  + Returns a JWT token upon successful registration.

#### 2. User Sign-In
+ **POST /auth/signin:** Sign in a user to the system using email and password.
  + Request parameters: `email`, `password`.
  + Returns a JWT token upon successful authentication.

#### 3. Token Refresh
+ **POST /auth/refresh:** Refresh the JWT token when it expires.
  + Request parameters: `refreshToken`.
  + Returns a new JWT token with an updated expiration time.
________
## **Entities and Fields**
+ #### ReqRes (Response Request)
  + `statusCode` : Status code of the response.
  + `error`: Error message string (if any).
  + `message`: Additional message (e.g., success message).
  + `token`: JWT token for the authenticated user.
  * `refreshToken`: JWT token for token refresh.
  * `expirationTime`: Expiration time of the token.
  * `name`: User's name.
  * `email`: User's email.
  * `role`: User's role.
  * `password`: User's password.
  * `products`: List of user's products.
  * `ourUsers`: User information.

+ #### SignUpRequest
  * `name`: User's name for registration.
  * `email`: User's email for registration.
  * `password`: User's password for registration.
  * `role`: User's role for registration.

+ #### SignInRequest
  * `email`: User's email for sign-in.
  * `password`: User's password for sign-in.

+ #### RefreshTokenRequest
  * `refreshToken`: JWT token for token refresh.
_________
## **Key Concepts**
+ **_JWT (JSON Web Token):_** Used for user authentication. Upon successful user authentication, a JWT token is generated and passed in the request header to access protected endpoints.

+ #### **User Roles:**
  *  _ROLE_USER:_ Default role granted to users upon registration.
  * _ROLE_ADMIN:_ Administrator role granting extended privileges.
_______________
## **Example Usage**
1. **Register New User:**

**Request**

````json
{
  "name": "John Doe",
  "email": "johndoe@example.com",
  "password": "password123",
  "role": "ADMIN"
}
````
**Response**
````json
{
  "statusCode": 200,
  "message": "User Saved Successfully",
  "ourUsers": {
    "id": 19,
    "email": "johndoe@example.com",
    "password": "$2a$10$a6OTOn6cegd0CCRcVlunLuIdyAUKEH6bTlLSsmothP9mbjahE5w4S",
    "role": "ADMIN",
    "enabled": true,
    "authorities": [
      {
        "authority": "ADMIN"
      }
    ],
    "username": "johndoe@example.com",
    "credentialsNonExpired": true,
    "accountNonExpired": true,
    "accountNonLocked": true
  },
  "error": null
}
````
2. **User Sign-In:**

**Request**

````json
{
  "email": "johndoe@example.com",
  "password": "password123"
}
````

**Response**

````json
{
"statusCode": 200,
"message": "Successfully Signed In",
"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lQGV4YW1wbGUuY29tIiwiaWF0IjoxNzEyMTQ3NDg1LCJleHAiOjE3MTIyMzM4ODV9._MnuZ32aC1hpeU7EeU7mbaiCA0LTH5Q9KyVyNl4ESNc",
"refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lQGV4YW1wbGUuY29tIiwiaWF0IjoxNzEyMTQ3NDg1LCJleHAiOjE3MTIyMzM4ODV9._MnuZ32aC1hpeU7EeU7mbaiCA0LTH5Q9KyVyNl4ESNc",
"expirationTime": "24Hr",
"error": null
}
````

3. **Token Refresh:**

**Request**

````json
{
  "refreshToken": "your_refresh_token_here"
}
````
**Response**

````json
{
    "statusCode": 200,
    "message": "Successfully Refreshed Token",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lQGV4YW1wbGUuY29tIiwiaWF0IjoxNzEyMTQ3NjU2LCJleHAiOjE3MTIyMzQwNTZ9.bR6QyvBFDJakTeF1599jWb9xODQrzW5iFHA7KwlcSxY",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lQGV4YW1wbGUuY29tIiwiaWF0IjoxNzEyMTQ3NDg1LCJleHAiOjE3MTIyMzM4ODV9._MnuZ32aC1hpeU7EeU7mbaiCA0LTH5Q9KyVyNl4ESNc",
    "expirationTime": "24Hr"
}
````
____________
#### _This project provides secure and reliable authentication for your applications using advanced Spring Security and JWT security methods._









