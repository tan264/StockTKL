
FROM gradle:jdk17

RUN mkdir /home/gradle/buildWorkspace
COPY . /home/gradle/buildWorkspace

WORKDIR /home/gradle/buildWorkspace
RUN ls /home/gradle/buildWorkspace
RUN gradle build --no-daemon -x test
RUN ls build/libs

# Run the Java application
CMD ["java", "-jar", "/home/gradle/buildWorkspace/build/libs/StockTKL-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080

