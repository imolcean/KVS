# KVS

Simple distributed key-value storage that supports both synchronous and asynchronous replication strategies.
The storage consists of two replicas, whereby one is the master and the other one is the slave. Clients are only
allowed to write to the master but can read from all the nodes.

The system was developed as a university project at TU Berlin.

This repository also provides a Client API for the storage. It can be seen in action in the example client that is also shipped with the project.


## How to build

Use gradle to build both server and client or only one of them.

- `./gradlew build`
- `./gradlew :server:build`
- `./gradlew :client:build`

## How to run the server

For both master and slave nodes, there is one executable but different set of parameters.

#### Slave

Run `server/build/libs/server-<version>.jar <port>`, where

- `<port>` is the port that this instance will be listening to.

#### Master

Run `server/build/libs/server-<version>.jar <port> <hs> <ps> [<sync>]`, where

- `<port>` is the port that this instance will be listening to.
- `<hs>` is the host of the slave instance.
- `<ps>` is the port of the slave instance.
- `<sync>` denotes that synchronous replication is used (leave it empty, if you need asynchronous replication).


## How to run the example client

Run `client/build/libs/client-<version>.jar <id> <host> <port> <i>`, where

- `<id>` is the ID of this client.
- `<host>` is the host of the server instance.
- `<port>` is the port of the server instance.
- `<i>` is the number of requests that the client should issue to the server.


## Client API

Client provides three CRUD methods:

- `Serializable get(String)` to retrieve the value for the given key.
- `void put(String, Serializable)` to place a new key-value pair into the storage.
- `void remove(String)` to remove a key-value pair with the given key.

> Note:
>
> Method `get` throws a `ValueRetrievalException` if no value for the given key could be found.
>
> Methods `put` and `remove` can only be executed while connected to the master node, a `PrivilegedOperationException` will
> be thrown otherwise.


