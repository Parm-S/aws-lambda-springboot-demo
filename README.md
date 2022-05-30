# aws-lambda-springboot-demo

Create a maven jar file and upload to the S3 bucket.
Copy the Object Url which u upload on S3 bucket. URL like this  s3://spring-boot-demo/yourjarfileName.jar

open lambda function 
In lambda function code source and in click on upload ther is two option upload directly if you have less than 10 mb jar or use s3 bucket. so click on sc bucket and paste the object url which we copied form S3 bucket and save it.

after that add your database enviroment variable in lambda configuration which u r hosting on rds.

DATABASE_NAME , DATABASE_HOST , DATABASE_PORT , DATABASE_USER , DATABASE_PASSWORD

in edit also set MemorySize: 1024 and  Timeout: 300

in runtime setting edit handler packageName.className::lambdaMethod for eg :- com.spring_boot_assignment.StreamLambdaHandler::handleRequest

After configuration u can test your lambda function in test event json

like this for get request 

{
  "resource": "/employee",
  "path": "/employee",
  "httpMethod": "GET",
  "queryStringParameters": {
    "page": 1,
    "size": 5
  },
  "requestContext": {
    "identity": {}
  }
}

for post request

{
  "body": "{\"firstName\":\"john\",\"lastName\":\"doe\",\"emailId\":\"john.doe@email.com\",\"phoneNo\":\"98989898\"}",
  "resource": "/employee",
  "path": "/employee",
  "httpMethod": "POST",
  "multiValueHeaders": {
    "content-type": [
      "application/json"
    ]
  },
  "requestContext": {
    "identity": {}
  }
}

for postman we need to configure Api gateway

open the console of apigateway 
 create rest api  
 after create rest api click on resources and In resources click on action, in action menu click on create a resource.
 
 In resource path we must the path name in which the controller Post or get mapping url in our case we give employee our /employee and after create resource we select resource and then click on action and click on create method and select method for this case i use any
 
now select your mehod any then click on method request, in method request add  URL Query String Parameters like page and size and in  Request Body  we need add model content-type application/json.

for create model click on model and model name and also create model schema like this

{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "title": "GetStartedLambdaIntegrationInputModel",
  "type": "object",
  "properties": {
    "firstName": { "type": "string" },
    "lastName": { "type": "string" },
    "emailId": { "type": "string" },
    "phoneNo": { "type": "string" }
  }
}

/id

we need to create proxy resource select a checkbox Configure as proxy resource then save it and create method similar as we create above.

**********************************************************************************************************************************************************

you can also create template.yml in your code

AWSTemplateFormatVersion: '2010-09-09'
Transform: AWS::Serverless-2016-10-31
Description: AWS Serverless Spring Boot API - com.springboot-demo::spring-lambda-serverless-api
Globals:
  Api:
    EndpointConfiguration: REGIONAL

Resources:
  SpringLambdaServerlessApiFunction:
    Type: AWS::Serverless::Function
    Properties:
      Handler: com.spring_boot_assignment.StreamLambdaHandler::handleRequest
      Runtime: java8
      CodeUri: target/spring_boot_assigment-1.0.0-SNAPSHOT-lambda-package.zip
      MemorySize: 1024
      Policies: AWSLambdaBasicExecutionRole
      Timeout: 300
      Events:
        ProxyResource:
          Type: Api
          Properties:
            Path: /{proxy+}
            Method: any

Outputs:
  SpringLambdaServerlessApiApi:
    Description: URL for application
    Value: !Sub 'https://${ServerlessRestApi}.execute-api.${AWS::Region}.amazonaws.com/Prod/ping'
    Export:
      Name: SpringLambdaServerlessApiApi

 





