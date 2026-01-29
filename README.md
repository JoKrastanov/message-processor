# Message Processor Application

This is a simple backend application that takes a rule set and applies it to all incoming events.

### Tech stack used: 
* Java 21
* SpringBoot + Maven
* Apache Kafka
* Some database (using Postgres in the dev example)

### External containers

* Kafka - used for consuming and producing messages
* KafkaUI - easy overview of all the data within Kafka as well as creating messages through a user-friendly interface
* Zookeeper - 

### How to run the application

For development, start the `docker-compose-dev.yaml` file.
This can be done by running 

    docker-compose -f .\docker-compose.yaml up -d

This will start the application alongside all the external containers.

For running the entire application simply run the `docker-compose.yaml` file. This will build all the external containers as well as the application.

### Messages
By default, messages are consumed from a topic `incoming-message` and the processed messages are sent to a topic `output`.

Furthermore, the provided database creates a table called `messages` where the initial state and the processed state of each message is stored.

For more in-depth version history of each message, the table `message_history` stores each each modification of the initial message as the actions are being applied one by one and also shows the delta(difference) between the state of the message before and after the specific action is applied.

### DSL Information

In order to properly use the application you need to know about the domain specific language implemented to handle the incoming messages. It can be split up into 4 separtate categories

The rule configuration file can be found in

    src\main\resources\rules\config.json

#### 1. Rules
Rules are the fundamentals of the application. Whenever a new message is picked up by the `MessageConsumer.java` each rule will be attempted to be applied to the message

In order to create a rule you need to provide the following fields in the JSON file:

        id: string,
        name: string,
        description: string,
        conditions: Condition[] // more on them in 2.
        actions: Action[] // more on them in 4.

#### 2. Conditions
Conditions are like the filters which decide if a rule should be applied to the message.

You can find all the supported condition types in: 

    src\main\java\com\example\message_processor\rules\Condition\ConditionEvaluationService.java

Each condition can contain a number of comparators which are what actually evaluate the value of the provided object.

In order to create a condition you need to provide the following fields in the JSON file:

        type: ConditionType,
        comparators: Comparator[] // more on them in 3.
        subconditions: Condition[]

#### 3. Comparators
Like mentioned above, comparators are what actually check the value of the message and compare it with a pre-defined value to decide if a rule should be applied.

You can find all the supported comparator types in:

    src\main\java\com\example\message_processor\rules\Comparator\ComparatorEvaluationService.java

The way the field selection works is the following. If you object is something like 

    {
        "user": {
            "age": 23
        }
    }

If you want to specify the age field you should pass `user.age`.

In order to create a comparator you need to provide the following fields in the JSON file:

        field: string,
        comparatorType: ComparatorType
        value: Object

#### 4. Actions

Acitons are the actual changes that get applied to the incomming message.

You can find all the supported action types in:

    src\main\java\com\example\message_processor\rules\action\ActionExecutionService.java

The field selection for actions works the same way as with Comparators.

In order to create an action you need to provide the following fields in the JSON file:

        field: string,
        actionType: ActionType
        expression: string
    
##### 4.1 Difference between Create and Update action types
If an action is defined as:
    
    {
        field: "user.location.city",
        actionType: "CREATE",
        "expression": "Sofia"
    }

And is applied to a message:

    {
        "user": {
            "name": "Joan"
        }
    }

In the case of CREATE, the new object `location` will be created and the value `city` will be defined. In the case of update, the app will throw an exception as that path is invalid in the current message


### Basic example message to start with

    {
        "user": {
            "firstName": "Joan",
            "lastName": "Krastanov",
            "age": 25
        }
    }