# Dungeon Chaos Backend
**Developer**: Jeff Ou  
**Description**: This is a backend-only application that provides REST APIs with functionalities similar to Twitter's backend, allowing users to perform actions such as *creating, updating, and deleting tweets, creating profile, replying to a tweet,  searching for tweets, and adding hashtags*.
# Table of Contents

- [Technologies](#technologies)
- [Project Dependencies](#project-dependencies)
- [Project Management](#project-management)
- [Entity Relationship Diagram](#entity-relationship-diagram)
- [API Reference](#api-reference)
    - [Postman Workspace](#postman-workspace)
- [Wins](#wins)
- [Improvement Ideas](#improvement-ideas)
- [Credits](#credits)

# Technologies
**[Trello](https://github.com/users/pophero110/projects/5)**: resource management  
**IntelliJ**: development IDEA  
**[Draw.io](https://drive.google.com/file/d/1KQNFfVow5GVSpBJ-qKArLaxjQkaPVNiU/view?usp=sharing)**: Create Entity Relationship Diagram  
**[Postman](https://www.postman.com/jeffou-1/workspace/twitter-like-restful-api-web-application)**: APIs Testing and Documentation   
**Sourcetree**: Git GUI
**Cucumber**: Behavior-driven testing framework
**JUnit**: 

# Project Dependencies
<details>
    <summary>Click to expand</summary>


<!--	API Documentation	-->
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-ui</artifactId>
			<version>1.7.0</version>
		</dependency>

	<!-- Testing -->
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>io.cucumber</groupId>
			<artifactId>cucumber-java</artifactId>
			<version>${cucumber.version}</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>io.cucumber</groupId>
			<artifactId>cucumber-junit</artifactId>
			<version>${cucumber.version}</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>io.cucumber</groupId>
			<artifactId>cucumber-spring</artifactId>
			<version>${cucumber.version}</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>io.rest-assured</groupId>
			<artifactId>rest-assured</artifactId>
			<version>4.3.0</version>
			<scope>test</scope>
		</dependency>
	<!-- Testing End -->

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<version>2.1.214</version>
		</dependency>
	</dependencies>
</details>

# Project Management



# Credit

- Thanks to my instructor, [Suresh Sigera](https://github.com/sureshmelvinsigera/), who gave me strong encouragement and trust for building this project
- Thanks to my roommates from Room 9 in Zoom for being with me and kind feedback on my project