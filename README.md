# Rental book code challenge - code instructions

1. My Dev enviroment 👍
   - Windows 10
   - Eclipse IDE for Enterprise Java and Web Developers 2021-09 (4.21.0)
   		- Eclipse Plugins
   			- Spring Tools 4 for Eclipse (Spring Tool Suite 4 - 4.12.1 ) 
   			- Java 21 support for Eclipse
   			- Project Lombok
   - JDK 21
   - Maven  3.9.5
   - git 2.43.0.windows.1
   - gh version 2.44.1
   - curl 8.4.0 (Windows) libcurl/8.4.0 Schannel WinIDN
   - Postman for Windows Version
   - Docker
		- Docker for Windows (Docker version 25.0.3) 
		- Docker Desktop for Windows 4.27.2

2. Install if necessary git, follow the instruction on the link below.
	- ```  
	   https://git-scm.com/downloads 
	  ```
	- After install run the command below in the terminal
		- ``` 
		   git config --global core.autocrlf true
		  ```

3. Install if necessary gh, follow the instruction on the link below.
	- ``` https://cli.github.com/manual/installation ```

4. try to access the link below
	- ``` https://github.com/pontalti/rental_books ```

5. Clone the repository
	- ``` git clone git@github.com:pontalti/rental_books.git ```

6. If necessary install the JDK 21, download it on the link below
	- ``` https://www.oracle.com/java/technologies/downloads/ ```
	- Choose your distribution and install the JDK
	- Create the Java Home
		- Windows -> ``` JAVA_HOME = [YOUR_PATCH]\jdk-21 ```
		- Linux -> ``` JAVA_HOME = [YOUR_PATCH]/jdk-21 ```
	- Put the JAVA_HOME on the System Patch
		- For Windows -> ``` %JAVA_HOME%\bin ```
		- For Linux -> ``` export PATH=$JAVA_HOME/bin:$PATH ```
	- Test JDK on command line
		- ``` java -version ```		

7. If necessary install Maven, download it on the link below
	- ``` https://maven.apache.org/download.cgi ```
	- Extract compressed file in your prefered tool folder.
	- Create the M2_HOME
		- Windows -> ``` M2_HOME = [YOUR_PATCH]\apache-maven-3.9.5 ```
		- Linux -> ``` M2_HOME = [YOUR_PATCH]/apache-maven-3.9.5 ```
	- Put the Maven on the System Patch
		- For Windows -> ``` %M2_HOME%\bin ```
		- For Linux -> ``` export PATH=$M2_HOME/bin:$PATH ```
	- Test Maven on command line
		- ``` mvn --version ```

8. If necessary install your favorite IDE with support to JDK 21.

9. if necessary Install the project Lombok on your IDE, follow the instruction on the link below.
	- ``` https://projectlombok.org/setup/overview ```

10. Open the project in favotite IDE

11. To build please.
	- Go to the project root folder.
	- Run the command below.
		- ``` mvn -U clean install package spring-boot:repackage ```

12. To run the SpringBoot application in localhost.
	- Go to the project root folder.
	- Run the command below.
		- ``` mvn spring-boot:run ```
	- OR
		- ``` java -jar target\rental_books.jar ```

13. To run the SpringBoot application with Docker.
	- Please install Docker.
	- Go to the project root folder.
	- Run the commands below.
		- ``` docker-compose build ```			
		- ``` docker-compose up -d ```
	- To check the log, please run the command below.
		- ``` docker logs -f rental-book ```
		
14. if necessary install curl on Windows or Linux.
	- for Windows -> ``` choco install curl ```
	- for Linux(Ubuntu/Debian) -> ``` apt-get install curl ```
	- for Linux(RHEL/CentOS/Fedora) -> ``` yum install curl ```
	
15. To call the services using curl please use the commands below on the Windows(CMD) orLinux(Terminal).
	- ``` curl -i -X GET "http://localhost:8080/customer" ```
	- ``` curl -i -X GET "http://localhost:8080/book" ```
    - ``` curl -i -X GET "http://localhost:8080/bookType" ```
    - ``` curl -i -X GET "http://localhost:8080/rental" ```

16. if necessary install POSTMAN, follow the instructions in the link below.
	- ``` https://learning.postman.com/docs/getting-started/installation-and-updates/ ```

17. To access the OpenAPI definition, please use the link below
	- ``` http://localhost:8080/swagger-ui/index.html ```

18. Find in the project root folder, the Postman folder with all examples to call all rest services available.
	- Import the file ``` Rental books.postman_collection.json  ``` to use it on Postman.
		
19. Rest services requested on code challenge. - ``` check the postman folder to use the examples. ```
	- Create a customer -> ``` http://localhost:8080/customer/ ```
	- Create a book type (category) -> ``` http://localhost:8080/bookType ```
	- Create a book -> ``` http://localhost:8080/book/ ```
		- Book type ID needed, to register a book.
	- Create a rental order -> ``` http://localhost:8080/rental/order/create ```
		- Customer ID and the list with book ID and the quantity to be rent, are needed to register a rental order.
	- Create a rental order return -> ``` http://localhost:8080/rental/order/return ```
		- Rental order ID and the list with 'rental book ID' and the quantity to be returned, are needed to register a rental order return.
	- Consult list of available books -> ``` http://localhost:8080/book/all/available ```
	- Consult list of unavailable books -> ``` http://localhost:8080/book/all/unavailable ```
	- Consult list of books owned -> ``` http://localhost:8080/book/all/owned ```
