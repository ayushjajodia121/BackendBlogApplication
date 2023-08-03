#Start with a base image containing Java Runtime
FROM openjdk:8-jdk-alpine as build

#Information around who maintains the image
MAINTAINER jajodia.com

#Add the application's jat to container
COPY target/appjar aapjar

#execute the application
ENTRYPOINT ["java","-jar","/appjar"]