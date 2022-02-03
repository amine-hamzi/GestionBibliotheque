FROM openjdk:16
EXPOSE 8088
ADD target/AMBiblio_API_REST.jar ambiblio_api_rest
ENTRYPOINT ["java", "-jar", "ambiblio_api_rest"]