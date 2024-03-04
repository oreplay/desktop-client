# desktop-client

Client to upload results from external timekeeping software

# For the future

Timekeeping software directly connected to oreplay api for logging in, communicating, sending and receiving data

# For the beginning

A client is going to be needed in order to transfer data from timekeeping software to oreplay. Login into an oreplay event or getting an authorization. Then, sending data to oreplay and getting the response

# Data transfer

Timekeeping software (OE, OS, OEScore, SiTiming, etc) is capable of exporting data in several formats, mainly IOF XML. Important data is starting times, intermediate results and provisional/official results. The desktop-client should get the files, convert the data into an intermediate format, for example JSON, and then upload to the server through the Backend API

# Client's development

By now, Java is being explored as the development language. A desktop application is going to be developed in order to perform all of the tasks, mainly:

- **Login into an event**, or authorising through the use of tokens
- **Importing a file** from timekeeping software
- **Conversion to JSON**
- **Uploading to the server**, and letting the backend make all of the big checkings, calculations and operations with the database

# For starting collaboration

Cloning GitHub Netbeans project

Although sometimes is better to spend long time with stable versions of products, in this case a migration to the last versions has been done, thinking that it could help the new collaborators. Initially, OReplay desktop-client was created using Netbeans 11.3 and Java JDK 1.8. Now, the project has been migrated to **Netbeans 21**, **Java OpenJDK 21** and **Maven**

- Ensure you've got a proper Java JDK installed or Install it (Netbeans will use it)
  - [OpenJDK](https://openjdk.org)
- Install Netbeans
  - [NetBeans](https://netbeans.apache.org/front/main/index.html)
- Clone the GitHub project in Netbeans
  - [Netbeans Git tutorial](https://netbeans.apache.org/tutorial/main/kb/docs/ide/git/)
  - Team -> Git -> Clone
    - Repository URL: https://github.com/oreplay/desktop-client.git
    - User/password: your Git user/password (or leave it blank for anonymous access)
    - Destination folder: the path where your other Netbeans projects are or specify a new one
  - Select Remote Branches: main
  - Destination Directory. Set a name for your Netbeans project in field Clone Name, for example OReplay
- Add-ons, managed by Maven through POM file
  - EclipseLink (JPA 2.2). Java Persistence, database management
  - JAXB Bindings. XML to Java Classes converter
  - Apache Commons IO
  - Apache Commons Codec
  - Jackson (Java Classes to JSON converter)
- Regenerate JAXB code
  - Some errors will arise because after cloning there will be no code for the JAXB binding. These errors disappear when cleaning and building the project again

# Pushing changes to the GitHub repository (remote)

After making changes, Netbeans lets you push changes to the remote directory after commiting them locally. You have to refer to the GitHub's project URL, your GitHub user and use a developer token as password. From your GitHub's user settings, go to developer settings, personal access tokens and create a token or use an existing one
