# Video-Translation-ClientServer
Video Translation Client Server
The Video Translation Client Library enables interaction with HeyGen’s video translation server and is a useful tool for integrating the video translation functionality into applications.  It simplifies the process of interacting with the video translation server and provides a reliable and efficient way to check the status of translation jobs.

It implements an asynchronous status checking mechanism with a robust retry mechanism to handle the requests, which achieves the resilience whenever there is a temporary server-side errors or delays.


Key Features:
•	Asynchronous Status Checking: The library initiates non-blocking requests to the server to check the status of the video translation process, ensuring optimal performance and responsiveness.
•	Retry Mechanism: In case of temporary server-side errors or delays, the library automatically retries requests with exponential back-off, providing resilience and fault tolerance.
•	Customizable Configuration: The library offers configurable parameters such as initial retry delay, maximum number of retries, and backoff multiplier, allowing users to fine-tune the behavior according to their needs.
•	Graceful Shutdown: The library offers a convenient method to safely shut down the service, ensuring that all ongoing tasks are completed or terminated without any issues.
•	Logging Support: The library integrates with the SLF4J logging framework, enabling users to monitor the library's activities, debug issues, and gain insights into the video translation process.
![image](https://github.com/divyanshikgit/Video-Translation-ClientServer/assets/78239756/8e34637b-9388-4255-8fd7-79d21cc43dc6)

