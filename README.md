# Multi Threaded Web Server

## Process :

When client wants to establish a connection with the server, both of them will use Socket API to do so.
The operating system in the client machine will open a PORT. Similarly, a PORT will be opened on the server side too.

Here, server can server multiple clients at any given time.

I tested for 600,000 requests per minute in JMeter to test my server application.

Also, I implemented the concept of thread pool to reduce the number of resouces occupied by the threads, thereby resolving the problem of memory spike.
