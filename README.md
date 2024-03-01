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
