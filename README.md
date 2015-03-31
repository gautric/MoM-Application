# MoM Application

An MOM application with several JEE 6 Technologies.

* EJB
* MDB
* Websocket
* CDI
* JMS

This application runs inside JBoss EAP 6.3 and Java 7

## Software Architecture

MoM App is an EAR Application and has got four sub-modules.
These sub-module provide specifics features.


## Compilation

Maven 3 build MoM Application

```
$> mvn package
```

## Configuration

### Websocket transport

MoM Application uses Websocket stuff, JBoss EAP 6 has to setup this feature.
Connect to JBoss instance via the jboss-cli.sh shell

```
 $> jboss-cli.sh --connect
 $# /subsystem=web/connector=http/:write-attribute(name=protocol,value=org.apache.coyote.http11.Http11NioProtocol)
 $# :reload
 $# exit
 $> 
```

#### Miscellaneous

This work is recompilation from different data sources list below :

* https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/6.3/html/Development_Guide/Create_a_Websocket_Application.html
* http://denistek.blogspot.fr/2008/08/list-jndi-names.html
* http://blog.arungupta.me/websocket-jboss-eap-6-3/
* https://weblogs.java.net/blog/jjviana/archive/2010/04/14/decoupling-event-producers-and-event-consumers-java-ee-6-using-cdi-a
* http://piotrnowicki.com/2013/05/asynchronous-cdi-events/
* http://azure.microsoft.com/en-us/documentation/articles/service-bus-java-how-to-use-jms-api-amqp/
* RH colleague stuff 

Feel free to send me a feedback.
