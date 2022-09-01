FROM openjdk:11
ADD build/libs/candlestick-aggregator.jar /opt/candlestick-aggregator.jar 
EXPOSE 8080
CMD ["java", "-jar", "/opt/candlestick-aggregator.jar"]
