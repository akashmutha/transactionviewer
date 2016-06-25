# transactionviewer
It's simple app to see the transaction and the amount of the transactions and coversion in GBP.

# Flow
First Activity(Screen) shows all the products traded by the company. And it also displays the number
of transaction.
Clicking on one of the product shows all the transaction related detailed and the conversion of
amount into GBP.

# Technical Details

Main Algorithm: Basic problem was the rate conversion algorithm. I have to find the minimum number
of conversion for converting any currency to GBP. The solution can be found by implementing Graph's
shortest path algorithm(Djikshtra's Algorithm). But here the weight of the edges(Converting one currency
to the directly adjacent currency will take only one multiplication, we can say single unit weight)
, So shortest path can be found using BFS only. I implemented a BFS to find the shorted root from
GBP to every currency and stored it in some hashmap.

Note: All the reading of files and graph algorithm are execution on the background thread. I used
Rxjava for reactive programming.(So I read the transactions on the background thread and once I got
the transaction on the main thread update the recycler view)

I implemented the Graph Data Structure for handling the above problem. There is a graph module in
model directory.

For passing the data from one Activity to another Activity I used Parcelable.

#Design Pattern Used
Abstraction(Implemented Interfaces for getting the data)
Observer Subscriber (used RxJava)
Inheritance (Implements interface and extends some classes)

