# JAVA Unit Test with Mutation Coverage

Leaking code and coverage to classes used may result in poor quality tests and/or errors.
A good UT should isolate the code tested.

### const

## Project Frameworks

* Gradle

## Test Frameworks

* JUnit
* Mockito
* PowerMock

## Build Instructions

Windows : `gradlew.bat clean build`,
Others: `./gradlew clean build`
Inside the project root dir, where is build.gradle file is located.

## Import instructions
To load the project,
1. Open IntelliJ IDEA
2. Click on "Open or Import" menu in the welcome screen. (or File->Import in main screen)
3. Navigate to the project folder and select the build.gradle file.
4. Click OK, and in the next prompt, choose "Open as project"
5. To download the dependencies, expand the Gradle panel in the top right section of the IDEA main window,
and click on "Reimport all gradle projects" icon.
6. This should download all dependencies and build the project.

## Required Tools
Please note that, all tools are already pre installed on the host.

* Gradle
* Java
* IntelliJ IDEA

## Build Instructions

Windows : `gradlew.bat clean build`

Linux  &  OSX : `./gradlew clean build`
inside the project root dir, where is build.gradle file is located.

## Coverage Reports
To see line coverage report,
 1. You can execute the test with coverage option in IntelliJ
 2. Or you can execute JacocoTestReport gradle task
     Windows:  "gradlew.bat clean jacocoTestReport"
     Others:   "./gradlew clean jacocoTestReport"
     this should generate jacoco line coverage report under build/reports directory.

To see mutation coverage report,
 1. Execute,
      Windows:  "gradlew.bat clean pitest"
      Others:   "./gradlew clean pitest"
      this should generate pitest mutation coverage report under build/reports directory.
