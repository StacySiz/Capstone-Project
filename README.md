# Capstone-Project
My project for passing middle exam in Akvelon

## How To Run

```
docker-compose up
```

## Remarks on the Code

- Capstone-Eureka-Server - is the Eureka server for service discovery.
- Capstone-Zuul - is the Zuul server. It distributes the requests from other microservices 
- Capstone-Web - backend for website for stand up comics, where they can sign in/sign up,create events and places,invite friends for events 
- Capstone-Email - service for email notification. Sends an email when user signed up and email with invitation for an event 
