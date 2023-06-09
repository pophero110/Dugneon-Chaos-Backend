# Twitter REST APIs Spring Boot Application
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

## [Brainstorm](https://github.com/users/pophero110/projects/5/views/2?pane=issue&itemId=26910994)
1. **Determine what kind of application I want to build**  
   I decide to build a Twitter-like RESTful API web application for two main reasons. Firstly, I do not build or design a social media platform application before, and I would like to challenge myself in this area. Secondly, I want to learn how to implement a many-to-many relationship with Spring Data JPA and PostgreSQL, which is a common feature in Twitter. By building this application, I hope to gain practical experience and enhance my skills in these areas.
2. **Identify the entities that are relevant to the domain**  
   User: A user who creates an account and can post tweets.  
   UserProfile: A profile associated with a user account, which can include personal information.  
   Tweet: A message posted by a user.  
   Thread: A conversation consisting of multiple tweets that are linked together.  
   Hashtag: A keyword or phrase preceded by the # symbol used to group together tweets on a specific topic.
3. **Use ERD to visualize the relationship between the entities and include the attributes associated with each entity**  
   use correct cardinality symbols  
   ![cardinality-symbols](./resources/Cardinality-Symbols-Database.webp)
4. **Design RESTful APIs**  
   [What is A RESTful API article](https://aws.amazon.com/what-is/restful-api/#:~:text=RESTful%20API%20is%20an%20interface,applications%20to%20perform%20various%20tasks.)
5. **Create user stories**  
   User story structure  
   ![user-story-structure](./resources/user-story-structure.png)  
   Acceptance Criteria  
   can also be written as bullets to describe the actual behaviors and functions a user would see to get to the desired outcome. While slightly more technical, the functions they describe are the mechanics a user should see and experience, not what a development team should build.
6. **Development Process**  
   create a branch for each ticket  
   write descriptive commit messages  
   commit only relevant changes to the current task.  
   commit often and early  
   always test the code of the current task before moving on to the next task


# Improvement Ideas



# Credit

- Thanks to my instructor, [Suresh Sigera](https://github.com/sureshmelvinsigera/), who gave me strong encouragement and trust for building this project
- Thanks to my roommates from Room 9 in Zoom for being with me and kind feedback on my project