# URL_Lookup


Description
-------------

1. The application provides APIs which process lookup URLs in bi-directionally.
   
   For eg : Data is mapped bi directionally as follows
   
    From:                                         To:
/products                                       /Fashion/
/products?gender=female                         /Women/
/products?tag=5678                              /Boat-Shoes/
/products?gender=female&tag=123&tag=1234         /Women/Shoes/
/products?brand=123                              /Adidas/


3. API with resource path as "/url/lookup/params" is used to consume the list of parameterized URLs and returns a map of provided parameterized URLs as keys
   with corresponding pretty URLs as values.
   
4. API with resource path as "/url/lookup/pretty" is used to consume the list of pretty URLs and returns a map of provided pretty URLs as keys and corresponding 
   parameterized URLs as values.

3. The end points can perform well for an input list of up to 100 URLs, each with up to 100 params, looking up in ~1 million key-value pairs.
     
4. RabbitMQ concurrency is leveraged to enhance the perfomance.
     
     Current implementation makes use of 4 concurrent consumers which can be modified according to the load expected.
   
6. BidiMap is used as in memory storage and bi directional lookup.
   
7. Concurrent Hashmap used insert the corresponding response for multi treaded environment.

Assumptions
--------------

1. Input URLs are in Json List format and Output from both end points returns a Map of Key-value pairs.

2. Input List can not be empty or null.

3. URLs cannot be empty or null.

4. Expected message count is assumeed to be 50,000. This is set as the initial value for Concurrent hashmap.


Technologies Used
-----------------

1. Java SE 17

2. Spring boot 3.1.1

3. RabbitMQ

4. Spring boot test

Improvements
------------
1. Scope for Docker file or similar to run the application in a container

2. To imporve the latency and throughput,any other kind of asynchronus event processing architectures could be introduced. eg  LAMAX Disruptor
 
3. Scope for better test coverage

4. Extensive documentation
 

 
 Thank you!
