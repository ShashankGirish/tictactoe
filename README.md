# tictactoe
Test project to create a game of tic tac toe, while also learning how to use kubernetes and docker.

Tools required:
1. Java with Spring boot
2. Docker toolbox
3. Minikube
4. Postgresql DB
5. Postman
6. HTML


Set up docker environment on windows
1. Download Docker ToolBox and install.
https://docs.docker.com/toolbox/toolbox_install_windows/

2. Download and Install kubernetes and minikube.
https://kubernetes.io/docs/tasks/tools/install-minikube/

Create a docker image for the tictactoe application
1. Create a docker file.
2. From the root directory of the project (The one with pom.xml), build the code.
	mvn clean install
3. Execute 
	docker build -t <tag>:<version> .
	Example: docker build -t <tictactoe>:<latest>


Deploy postgres
1. Pull 	postgres image.
	docker pull postgres --image-pull-policy=IfNotPresent

2. Deploy postgres docker image.
kubectl run postgres --image=postgres:latest --env="POSTGRES_PASSWORD=postgres"

3. Expose postgres deployment on desired port.
kubectl expose --port=5432 deployment postgres --type=NodePort 

4. (Optional) Check if service is running and get postgres service url.
minikube service postgres --url

Deploy application
1.  Deploy the application, set the port to match tomcat port as configured in the application.
kubectl run neotictactoe --image=tictactoe:latest --port=8087 --image-pull-policy=IfNotPresent 

2.  Expose the application deployment.
kubectl expose deployment neotictactoe --type=NodePort

3. Get the application's service URL.
minikube service neotictactoe --url

Now the containers can be accessed by using docker ip in browser

