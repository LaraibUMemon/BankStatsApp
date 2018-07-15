# BankStatsApp

## Comments
SpringBoot application created using maven
- Compile with command "mvn clean install"
- Run Tests with "mvn clean test"
- Run with command "mvn spring-boot:run"
- Transactions storred in TransactionCache in a ConcurrentSkipListMap where key = epochSecond and value = list of transactions occured during that second
- Spring Scehduling used to clear the transactions from the cache that are older than 60 seconds with interval of every 5 seconds. Interval may be decreased.
- Cache Map will keep updating concurrently every 5 seconds by removing older entries coressponding with epochSeconds that are before current time epoch second minus the fixed duration of 60 seconds.
- Statistics service created to save transactions and get statistics for fixed duration of 60 seconds
- Simple beans created for Transaction and Statistics class
- Statistics calculated on the collection using DoubleSummaryStatics on the list of Transactions obtained merging the transactions for all the previous seconds in the duration.
- Unit Tests added for services, class methods and controllers.


## Statistics API
GET /statistics
This is the main endpoint of this task, this endpoint have to execute in constant time and
memory (O(1)). It returns the statistic based on the transactions which happened in the last 60
seconds.
![statistics](https://user-images.githubusercontent.com/40967987/42738620-12d4c306-88a0-11e8-8d83-d4dca3faa0f0.PNG)


## Transactions API
Every Time a new transaction happened, this endpoint will be called.
Returns: Empty body with either 201 or 204.
- 201 - in case of success
![transactioncreated](https://user-images.githubusercontent.com/40967987/42738640-692b3de8-88a0-11e8-9c34-ffd0ded65496.PNG)

- 204 - if transaction is older than 60 seconds
![transactionnocontent](https://user-images.githubusercontent.com/40967987/42738661-a85ba43a-88a0-11e8-85c7-79061f211b75.PNG)
