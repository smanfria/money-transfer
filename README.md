# REVOLUT Money Transfer challenge

## How to run
You should be able to start the example application by executing one of the following options:

* com.revolut.transfer.money.App, which starts a webserver on port 4567 (http://localhost:4567/api/account/)
* Running the command line mvn package && java -jar money-transfer-1.0-shaded.jar which starts a webserver on port 4567 (http://localhost:4567/api/account/)


The project is based on a restful services which uses the following technologies:

* Java 11
* Spark framework
* Database H2 (In-Memory)
* Maven


## Services

* POST http://localhost:4567/api/account/ . Create a new account.
* GET http://localhost:4567/api/account/:id . Search an account by id.
* DELETE http://localhost:4567/api/account/:id . Close an account by id.
* POST http://localhost:4567/api/account/:idFrom/transfer/:idTo . Transfer money from account idFrom to account idTo.

The post method should receive a payload like the following:

{
	"amount": 3000,
	"currency": "USD"
}