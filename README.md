# URL_Lookup


Description
-------------

1. The application provides APIs which process lookup URLs in bi-directionally.
   
   For eg : Data is mapped bi directionally as follows
   
 <img width="536" alt="image" src="https://github.com/jeenaAdhikariVeetil/URL_Lookup/assets/108806756/94f4539c-7236-4aa2-9a2f-3562b850c4ee">


2. API with resource path as "/url/lookup/params" is used to consume the list of parameterized URLs and returns a map of provided parameterized URLs as keys
   with corresponding pretty URLs as values.
   
3. API with resource path as "/url/lookup/pretty" is used to consume the list of pretty URLs and returns a map of provided pretty URLs as keys and corresponding 
   parameterized URLs as values.
   
4. Common business framework is used to search for corresponding param and pretty url from the storage map.
   if there is no exact match found in the lookup, a best match will be retured as per the program logic.
   If no match was found,it returns the provided URL itself.

5. The end points can perform well for an input list of up to 100 URLs, each with up to 100 params, looking up in ~1 million key-value pairs.
     
6. RabbitMQ concurrency is leveraged to enhance the perfomance. ParamConsumer and PrettyConsumer to handle the logic seperately with the common utility framework.
     
     Current implementation makes use of 4 concurrent consumers which can be modified according to the load expected in a single request.
   
7. BidiMap is used as in memory storage and bi directional lookup.
   
8. Concurrent Hashmap used to insert the corresponding response for multi threaded environment.
    
9. Junit test cases are added for controller and service implementation classes. 

Assumptions
--------------

1. Input URLs are in Json List format and Output from both end points returns a Map of key-value pairs.

2. Input List can not be empty or null.

3. URLs cannot be empty or null.

4. Expected urls count is assumeed to be 50,000. This is set as the initial value for Concurrent hashmap.


Technologies Used
-----------------

1. Java SE 17

2. Spring boot 3.1.1

3. RabbitMQ

4. Spring boot test

Improvements
------------
1. Scope for Docker file or similar to run the application in a container

2. To improve the storage capacity and enhance the look up capabilities an in memory cache like Redis can be used.

3. To imporve the latency and throughput,any other kind of mutithreaded framework like executor service or asynchronus event processing architectures could be introduced. 
   eg  LAMAX Disruptor.
   
4. A better algorithm for look up operations like  trie or a prefix tree datastructure could be used.
 
5. Scope for better test coverage.

6. More validations could be added for input list urls.

7. Extensive documentation like swagger could be implemented.
 

 
 Thank you!
