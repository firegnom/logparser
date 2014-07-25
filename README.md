logparser
=========
#TASK:

A logfile contains newline-terminated, space-separated text formatted like:
 <unix_timestamp> <hostname> <hostname>

 Example:
 1366815793 quark garak
 1366815795 brunt quark
 1366815811 lilac garak

 Each line represents connection from a host (left) to another host (right) at a given time.
 The lines are *roughly* sorted by timestamp. They might be out of order by maximum 5 minutes.
 Implement a tool that parse logfiles like this. The tool should both parse previously written logfiles and terminate or collect input from a new logfile while it's being written and run indefinitely.

 The script will output, once every hour:
 - a list of hostnames connected to a given (configurable) host during the last hour
 - a list of hostnames received connections from a given (configurable) host during the last hour
 - the hostname that generated most connections in the last hour

 The number of loglines and hostnames can be very high. Consider implementing a CPU and memory-efficient solution.
 
 
#Build
Application is configured to be build by maven build system it is enough to go to main directory where pom.xml is located and execute 
	mvn install
This will compile all the sources and run existing tests and create executable jar file , this jar file require some dependencies to be executed and are located in local maven repository.

#Dependencies
Tu run application i am using just two additional dependencies which are slf4j and logback , I am using them for logging purposes

 
#Design
Application is designed to have one file reader which is running in one thread and additional a thread pool of executors which are parsing data and processing it . Each executor has its own Buffers which are reused every time data is processed. 
Data is converted to lines and lines are passed to correct implementation of LineDecoder in this case I am using a patternLineDecoder which can be configured by passing a string to it like "ut s s" which describes that line consists of three elements ut - Unix Timestamp and two types of s - which is just a simple string . It very easy to change order of this items and to implement additional Parsers.
After Data is converted to object it is then processed by Processor I have added a simple processor which is executing 3 other processors wchich are doing tasks:

Processor 1 and 2 are a logging processors with a bit different strategy  but the main function is to log every data that is acceptable by passed filter in to the output logger after this logger is configured in logback.xml to be a rolling appender and data is send to the file 
this processor is not storing any additional data.

Processor 3 is a CountingProcessor and is configured to Count String words passed from data at the moment it counts connections from and to certain host. Processor is using hashmap to store CountElements maximum count is Long.MAX_VALUE  the size of the hashmap can be limited and as default it is limited to 100 000 elements if it is limited if size of the map is greater than specified limit it is truncated to the specified size containing largest counts . This solution  can generate counting errors so if it is necessary to have perfect count it is recommended not to use limit. Processor is flushed every hour (Assumption : but only if there is data coming in)

#Memory Usage & Performance
I have tried to use as much as possible multi threaded environment for this application but I had to sychronize on the Processors which are a bit of the bottleneck in this application , especially a counting one , I thought about implementing some distributed counter , maybe using Loglog Algorithm but unfortunately there was not enough time to do it but it can be easily extended in the future. 

Application is scalable up to one Machine it uses multi core environment and can be configured to use more or less memory  again I was thinking about creating distributed processors which could be placed on different machines and conected via sockets but time was a limitation . 


#Run
To execute application :

java -jar LogParser.jar filename follow hostFrom hostTo
 
filename - name of the file to process 
follow - boolean if follow is true LogParser will wait for data to be appended to file 
hostFrom - source host from which data will be logged 
hostTo - destination host from which data will be logged 
