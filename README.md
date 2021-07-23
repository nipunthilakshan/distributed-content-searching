# Distributed Computing - Distributed Content Searching

#### Set of Instructions

1. Clean and Compile the `distributed-content-searching` project with Maven. (mvn clean install)
2. Copy the jar and external.app.properties to the desired location.
3. Run the bootstrap server(ip: 127.0.0.1, port: 55555).
4. Run the client with `java -jar distributed-file-system.jar`.
5. Enter Client Port.
6. Enter User Interface Port.
7. Enter Username.
8. Repeat above steps as for the required nodes.

#### Usage
- The list of files will be created and added to a node specific folder at the startup.
- Access the user interface by navigating to **[client-ip]:[port]** using the browser.
- Search by typing in files names to the search field.
- Leave the system using the Leave option at the bottom.
- Download the files from the search results.


#### Notes
- Bootstrap.ip, Bootstrap.port, Client.ip and Hop count can be configured in external.app.properties file.
- Wait.time can be configured in external.app.properties file.This will be used to show the search results in User Interface.