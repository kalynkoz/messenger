# MESSENGER

This is a simple messaging API with two endpoints for interacting with the database:
1. GET `/messages/{recipientId}`
2. POST `/message` 
```json
{
  "sender": "Sender's id string", 
  "recipient": "Recipient's id string", 
  "content": "Message that was sent", 
  "sentAt": 1600030385 
}        
```
`sentAt` is an optional, long and if not included, sentAt = Time.now.epochSecond

## Running Locally :sparkles:
This service runs in docker. If you do not have docker downloaded yet, please follow directions at:
[Docker](https://docs.docker.com/get-docker/).

Run the following:
`docker-compose build && docker-compose up`

Once the application has started:
`curl localhost:8080/hello`

### Notes
* Step 4 in the build process takes a little-bit of time because it is building the jar.
* `/hello` only exists for testing purposes

## Implementation Design :tada:

Hello! Thank you so much for taking the time to look over this project. 

I chose a Kotlin, Spring REST microservice because...

For the database I chose to use a light-weight library `Kotlin Exposed` on top of an H2 database because...
