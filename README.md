# Binary-Logger
Logs serialized events into files.

Prerequisites:
- It requires JAVA 8 for running.

How to run:
1. Use `java -jar` command after project was built from `/out` directory.

## CLI API:
Arguments for READ operation:

| Argument Number  | Parameter  | Type of parameter | Description | Required | 
|---|---|--------|-----|---|
|1|operation|string|operation name|yes|
|2|path to file|string|path to file with events|yes|

Command Example: 
`java -jar BinaryLogger.jar read "/Users/.../BinaryLogger/events.bin"`

Arguments for WRITE operation:

| Argument Number  | Parameter  | Type of parameter | Description | Required | 
|---|---|--------|-----|---|
|1|Operation|string|Operation name|yes|
|2|Path to file|string|Path to file with events|yes|
|3|Multiply event|integer|How many events would you like to generate. It's used for testing. Default 10(not required)|no|
|4|New event name|string|Event name value(not required)|no|
|5|New event details|string|Event details value(not required)|no|
|6|File size limit|long|File size limit. Once it's higher than limit then new file will be created for other events.(not required)|no|

Command Example: 
`java -jar BinaryLogger.jar write "/Users/.../BinaryLogger/events.bin" 1000 "Some Event" "Bla bla bla" 100000`

NOTE: before running java command please navigate to build file directory or specify full path to the jar file.