# MESSENGER

This is a simple messaging API with the following endpoints for interacting with the database:
1. GET `/messages/{recipientId}`
    * Returns all messages for a recipient
    * Maximum messages returned: 100
1.  GET `/messages`
    * Returns all messages from database
    * Maximum messages returned: 100  
1. GET `/messages/recent/{recipientId}`
    * Returns all messages from the past 30 days for a recipient
1.  GET `/messages/recent`
    * Returns all messages from the past 30 days    
1. POST `/messages` 
    * `sentAt` is an optional, long and if not included, sentAt = Time.now.epochSecond
    * `id` is an optional, string and if not included, id will be generated
    ```json
    {
      "convoId": "Conversation's id string",
      "sender": "Sender's id string", 
      "recipient": "Recipient's id string", 
      "content": "Message that was sent", 
      "sentAt": 1600030385,
      "id": "Database ID"
    }        
    ```

## Running Locally :sparkles:
This service runs in docker. If you do not have docker downloaded yet, please follow directions at:
[Docker](https://docs.docker.com/get-docker/).

Run the following:
`docker-compose up --build`

Once the application has started:
`curl localhost:8080/health`

### Notes
* Step 4 in the build process takes a little-bit of time because it is building the jar.
* `/health` only exists for testing purposes

### Example Commands
POST
```bash
curl -X POST \
  http://localhost:8080/messages \
  -H 'Content-Type: application/json' \
  -d '{
  "convoId": "atlhjkgaFda241",
  "sender": "slgfsg425s3gGDSg", 
  "recipient": "R5467ggjkZF", 
  "content": "Message that was sent", 
  "sentAt": 1568415467
}'
```

```bash
curl -X POST \
  http://localhost:8080/messages \
  -H 'Content-Type: application/json' \
  -d '{
  "convoId": "atlhjkgaFda241",
  "sender": "slgfsg425s3gGDSg", 
  "recipient": "R5467ggjkZF", 
  "content": "Message that was sent"
}'
```

GET
```bash
curl localhost:8080/messages
```

```bash
curl localhost:8080/messages/recent/R5467ggjkZF
```

### Running Test Suite
In order to run the test suite run:
```
./gradlew test
```


## Implementation Design :tada:

Hello! Thank you so much for taking the time to look over this project. 

### Technology Choices
I chose a Kotlin, Spring REST microservice because it is a great stack for CRUD APIs.

For the database, I chose to use an in-memory hashmap due to the time constraint for the project. 
The map is set-up such that the key is `recipientId` and the value is a list of all messages sent to that user.  I did 
this to get fast lookup times on `recipientId` 
with the understanding I would have to iterate over the list of messages if it was greater than 100 
to get all newer messages.

### Productionalize 
The biggest change that would need to occur with the implementation would be to store this in a proper database 
rather than an in-memory hash. I would have likely chosen a SQL database in order to query by `recipientId` and `sentAt` 
to match the current query needs.

Additionally, I would add the remaining CRUD functionality and add other `read` query patterns such as 
`getMessagesBySender` or `getMessagesFromConversation`. It might be beneficial to have a batch endpoint for posts. 

### Housekeeping Changes
The following are chores I would likely have done if I were going to continue working this:
1. Extracted interfaces to keep the connections between controller and service clean and free from implementation details
1. End to end tests with DB once set-up (only `/health` is setup)
1. Data validation, specifically in tests
