## Product Comparison Service

Service that helps the end customer to decide which website or retail shop
they can use to buy their product by comparing data from different providers.

##

Project made with Spring boot and H2 Database

##

Docker run command

```
docker build --build-arg JAR_FILE=build/libs/\*.jar -t product-comparison .
docker run -p 8080:8080 product-comparison
```

##

You can import all request from `postman_collection.json`