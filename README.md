# tictactoe
Test project to create a game of tic tac toe, while also learning how to use kubernetes and docker.

* [Tools Required](#tools-required)
* [Set up docker environment on windows](#set-up-docker-environment-on-windows)
* [Start Minikube](start-minikube)
* [Create a docker image for the tictactoe application](#create-a-docker-image-for-the-tictactoe-application)
* [Deploy postgres](#deploy-postgres)
* [Deploy application](#deploy-application)


## Tools Required:

1. Java with Spring boot

2. Docker toolbox

3. Minikube

4. Postgresql DB

5. Postman

6. HTML


## Set up docker environment on windows
1. Download Docker ToolBox and install.
```
    https://docs.docker.com/toolbox/toolbox_install_windows/
```

2. Download and Install kubernetes and minikube.
```
    https://kubernetes.io/docs/tasks/tools/install-minikube/
```

## Start Minikube

1. Start minikube using command
```
    start minikube
```

2. Configure shell to use docker commands
```
    @FOR /f "tokens=*" %i IN ('minikube docker-env') DO @%i
```

## Create a docker image for the tictactoe application

1. Create a dockerfile.

2. From the root directory of the project (The one with pom.xml), build the code.
```
    mvn clean install
```
	
3. Build the docker image specifying the tag and version.
```
    docker build -t tictactoe:latest
```

## Deploy postgres

1. Pull 	postgres image.
```
    docker pull postgres --image-pull-policy=IfNotPresent
```

2. Deploy postgres docker image.
```
    kubectl run postgres --image=postgres:latest --env="POSTGRES_PASSWORD=postgres"
```

3. Expose postgres deployment on desired port.
```
    kubectl expose --port=5432 deployment postgres --type=NodePort 
```

4. (Optional) Check if service is running and get postgres service url.
```
    minikube service postgres --url
```

## Deploy application

1.  Deploy the application, set the port to match tomcat port as configured in the application.
```
    kubectl run neotictactoe --image=tictactoe:latest --port=8087 --image-pull-policy=IfNotPresent 
```

2.  Expose the application deployment.
```
    kubectl expose deployment neotictactoe --type=NodePort
```

3. Get the application's service URL.
```
    minikube service neotictactoe --url
```

Now the containers can be accessed by using docker ip in browser

