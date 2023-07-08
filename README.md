# TUM_SW2
1:
Strangler Pattern
In this exercise, you will use the Strangler Pattern to refactor a monolith to microservices.

Jedi Order
The Sith Order is getting stronger and poses a huge threat to the Jedi Order. For this reason, the Jedi Order is in need of new apprentices.

Until now, Master Yoda has been responsible for both finding and training new apprentices. However, due to his old age, he decided to hand over the responsibility of training apprentices to Obi-Wan Kenobi and focus only on finding new apprentices.

You have the following tasks:

 Migrate functionality 2 of 2 tests passing
Move the endpoint that handles training apprentices from MasterYodaController to ObiWanKenobiController.

 Forward call to the right service 1 of 1 tests passing
In the Client, forward the call to train apprentices in the method trainApprentices to ObiWanKenobiService running under localhost:8081.

2:
Microservice Patterns - Async Messages
In the droid manufacturing process, it can take a significant amount of time for a droid to be fully assembled. During this time, the operator is idle and waiting for the droid to be completed. However, there have been instances where the operator becomes impatient and decides to move on before the droid is ready, resulting in the loss of the droid (in the form of an HTTP timeout).

To address this issue, the operator has come up with a new solution. They have created an inbox system where they will receive notifications when a new droid is ready. This way, the operator can continue working on other tasks, and will be alerted when a droid is completed and ready for further processing.

You have the following tasks:

 Implement the inbox system 3 of 3 tests passing
Implement the droidReady3PO and droidReadyR2 methods in the InboxClient class. Once a droid is ready, these two methods should be called to send a POST request to the Operator. For sending the POST request, you can use the postForEntity method of RestTemplate. Make sure to create the POST request url string using the static strings (e.g. BASE_URL) at the top of the class, otherwise the tests will fail. To create the correct headers and body for the request, you can use the helper method createHttpEntity. The expected response type of each request is String. To retrieve the body of your POST request, that should be stored in the attribute messages, you can use the getBody method.
To handle these incoming POST requests, create the InboxResource class in the OperatorService with two new methods, one for R2 droids and one for 3PO droids, which should be called droidReadyR2 and droidReady3PO respectively, just like they are called in the InboxClient class. Next, you have to annotate each method accordingly.
Important note: You can take a look at the DroidFactoryResource and the DroidfactoryClient classes, as the InboxClient and InboxResource classes should follow a similar logic. The only differences are that the parameter of the methods should be of type String and the return value should be a ResponseEntity<String>. The value for the RequestMapping should be /messages/.
Note: The InboxResource class methods are extremely simple, but this is on purpose since the focus of this exercise is on the asynchronous messaging pattern. In a real-world-scenario, they would be more complex.

 Modify the call from the Operator to the DroidFactory to be async 2 of 2 tests passing
Now that the inbox system is ready, we want to enhance the existing droid factory by replacing the old methods for producing droids with new asynchronous ones.
First, you should replace the synchronous produceR2 and produce3PO methods in DroidFactoryResource with their async counterparts, produceR2Async and produce3POAsync. For the implementation of the two methods, you should use a CompletableFuture (please refer to the CompletableFuture documentation). Since these methods should return a CompletableFuture<String>, you need to call getName on the produced droid.
Next, you should also create asynchronous counterparts to the exisiting synchronous produceR2 and produce3PO methods in DroidFactoryClient. These asynchronous methods in DroidFactoryClient should make a POST request that is identical to the synchronous methods except that the expected response type is String instead of Droid. Pass the result of this POST request to the corresponding method (either droidReady3PO or droidReadyR2) from the InboxClient class.

3:
REST Architectural Style
The university has finally decided to use real-world examples for learning purposes as opposed to mystical penguins. However, the previous developers were not happy with that decision and waddled off having deleted parts of the project they didn't like. The Project is a simple REST API based on Spring Boot. Your task is to apply the layered architectural pattern on top of an MVC design pattern and to write an API.

This exercise uses Spring Web on the server side to process and reply to REST requests that the client-side sends using Spring WebFlux. The provided links provide a preview of how the frameworks work. It isn't necessary to know in detail the workings of the framework but it's worth a go.

Application Structure
The following model describes the Layered Architecture that is used for this exercise to separate the implemented functionality into different layers.

Client-Side:
The Presentation Layer contains functionality that the user directly interacts with (such as a GUI).
The Application Layer contains the actual application logic. This layer receives the entities (Person and Note) from the Network Layer, processes it and passes the data over to the Presentation Layer. In addition, this layer receives events from the Presentation Layer (such as invoking updating the name of an entity) and forwards the events in terms of method calls to the Network Layer.
The Network Layer contains the functionality to send REST requests to the server.
Server-Side:
The Network Layer contains the functionality to receive, validate and respond to REST requests from the client.
The Business Layer contains the functionality to process the entities received from the client. In this exercise, this layer is also responsible for persisting (saving, updating and deleting) the entities.
<img width="413" alt="image" src="https://github.com/tstren1223/TUM_SW2/assets/64294878/32219690-0898-4e23-9e24-af85f860503f">

On the client side, the Model View Controller Pattern has been applied. The following component model describes the implementation in detail.

<img width="332" alt="image" src="https://github.com/tstren1223/TUM_SW2/assets/64294878/b412dca3-b0bb-4703-924b-3dbc941b5760">



Note: Important! To run the application try it locally, please run H05E01ServerApplication for the server side and Starter for the client side. Do not run H05E01ClientApplication!

Application Functionality
This system has 2 sides.

Client-side
Server-side
The server side has an open endpoint listening on port 8080 for HTTP Requests. For every respective HTTP Request, a specific action is carried out to fulfil the task and return the result. The client-side has a JavaFX GUI, to spare the clients the rather scary-looking naked REST Calls and have it run behind closed doors instead. For every respective action, it generates the required HTTP Request and sends it to the server and gets back an HTTP response which is then used to update the UI.

The endpoints you'll have to implement should allow you to Create, Read, Update and Delete (CRUD) persons. In detail, these are:

POST /persons: Creates a new person. The person is passed via the body of the HTTP request. The response contains the saved person in the body of the HTTP response
GET /persons: Reads all persons. The persons are returned as a list in the body of the HTTP response
PUT /persons/{personId}: Updates the person with the given ID. The updated person is passed via the body of the HTTP request. The response contains the updated person in the body of the HTTP response
DELETE /persons/{personId}: Deletes the person with the given ID. It returns an HTTP 204 No Content response if successful
Note: There are TODOs in the code giving helpful hints

Side note : If you want to try out these new REST Requests directly without the client, run the server side by running H05E01ServerApplication, and use the following curl commands to see REST in action.
curl -X GET -H "Content-Type: application/json" "localhost:8080/notes?secret=SecretKey" should return [] as nothing has been stored.
curl -X POST -H "Content-Type: application/json" -d '{"name":"nice","content":"this is how you save a nice note","creationDate":"2001-06-09T04:20:21.91119Z"}' "localhost:8080/notes" to save a nice note. It'll return the saved data as json to acknowledge what was saved
curl -X GET -H "Content-Type: application/json" "localhost:8080/notes?secret=SecretKey" would then return the same note.
Note: The creationDate gets overwritten as that is generated by the server. You can cross-check if the data got saved by running Starter and then checking out Notes


Part 1
In the first part, we want to get basic functionality up and running on the server side. Specify the endpoints in the server in PersonResource. You have to forward each action to the PersonService and add any additional checks before that.

 Endpoint to create a person 2 of 2 tests passing
Implement the method createPerson, which saves a valid Person. You need to ensure that it is indeed a new Person that does not have an ID yet.
 Endpoint to update a person 2 of 2 tests passing
Implement the method updatePerson, which updates a valid Person based on the UUID. You need to ensure that the modified Person has personId as its ID.
 Endpoint to delete a person 1 of 1 tests passing
Implement the method deletePerson, which deletes a Person based on the UUID.
 Endpoint to get all persons 1 of 1 tests passing
Last but not the least, implement the method getAllPersons, which returns all persons.
Note: Feel free to get inspired by NoteResource for additional help.

Part 2
In the second part, we will now be writing the RESTFUL Requests to the server on the client side. But first, we need to set up PersonController properly according to this UML Diagram. The Controller should work like this:

There is a single WebClient instance that is used for all requests
Each method should only send a single HTTP request
The HTTP requests need to happen asynchronously
It has an internal list of all persons
If a person is created, updated or deleted this has to be updated in the list
If all persons are retrieved from the server the list has to be updated accordingly
Each method takes a Consumer<List<Note>> parameter, which has to be called after a response is received
This consumer takes the internal list of persons as a parameter
UML Diagram
<img width="254" alt="image" src="https://github.com/tstren1223/TUM_SW2/assets/64294878/56836bb9-4cf9-4b0e-bedb-4307f8855bf7">



 Request to create a person 3 of 3 tests passing
Implement the method addPerson which sends a POST Request to our server to add a Person.
 Request to update a person 3 of 3 tests passing
Implement the method updatePerson which sends a PUT Request to our server to change the attributes of the old Person according to the UUID.
 Request to delete a person 2 of 2 tests passing
Implement the method deletePerson which sends a DELETE Request to our server to delete a Person based on the UUID.
 Request to get all persons 2 of 2 tests passing
Last but not the least, implement the method getAllPersons which sends a GET Request to our server to get a list of Person objects.
Note: Don't forget the constructor of PersonController

Part 3
It has been determined that the server should now also support the sorting of persons. The functionality for this has already been implemented in the client UI. The user can sort the persons by their ID, first name, last name, and birthday in either ascending or descending order. These options are stored in PersonSortingOptions for convenience.

It is now your job to implement this feature. The sorting itself should happen in the PersonService. The GET /persons endpoint has to be adapted to accept the sorting options and pass them to the PersonService. The client request also has to be adapted to send the sorting options to the server. Some additional notes:

The sorting options have to be passed as the query parameters sortField and sortingOrder. You can look at the GET /notes endpoint to see an example using query parameters.
The server endpoint should still work without specified sorting options. In this case, it should default to ID and ASCENDING as the sorting options. Hint: Look at the JavaDoc of RequestParam.
 Add the sorting functionality 2 of 2 tests passing
Implement the method getAllPersons in PersonService which sorts the list of Person according to the PersonSortingOptions parameter. Then update the getAllPersons methods in PersonResource and PersonController to pass the PersonSortingOptions in the HTTP request.

Note: Do not change the attributes of PersonService, this will lead to Artemis tests failing
