# MESSENGER

This is a simple messaging API with two endpoints for interacting with the database:
1. GET `/messages/{recipientId}`
    * Maximum messages returned: 100
2. POST `/message` 
    * `sentAt` is an optional, long and if not included, sentAt = Time.now.epochSecond
```json
{
  "sender": "Sender's id string", 
  "recipient": "Recipient's id string", 
  "content": "Message that was sent", 
  "sentAt": 1600030385 
}        
```

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

### Example Commands


## Implementation Design :tada:

Hello! Thank you so much for taking the time to look over this project. 

### Technology Choices
I chose a Kotlin, Spring REST microservice because it is the industry standard for APIs, there is lots of documentation, 
and I am fairly familiar with it.

For the database I chose to keep store it as an in-memory hashmap due to the time constraint for the project. 
The map is set-up such that the key is `recipientId` and the value is a list of all messages sent to that user.  I did 
this to get fast lookup times on `recipientId` 
with the understanding I would have to iterate over the list of messages if it was greater than 100 
to get all newer messages.

### Productionalize 
The biggest change that would need to occur with the implementation would be to store this in a proper database 
rather than in-memory hash. I would have likely chosen a SQL database in order to query by `recipientId` and `sentAt` 
to match the current query needs. This would have then expanded nicely to additional functionality or other query patterns.

Additionally, I would add the remaining CRUD functionality and add other `read` query patterns such as 
`getMessagesBySender` or `getMessagesFromConversation`.

### Housekeeping Changes
The following are chores I would likely have done if I were going to continue working this:
1. Extracted interfaces to keep the connections between controller and service clean and free from implementation details
2. End-to-End tests with DB once set-up
3. Data validation, specifically in tests
4. Separate reads and writes in controller and in service