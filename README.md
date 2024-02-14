### Full project build and run
- For running th full project please run the docker-compose.yml file
- It will create server A, server B and DB.
- `docker-compose build`
- `docker-compose up`


### Deployment to kubernetes
- install minikube
- login to docker `docker login`
- run `minikube start`
- run `kubectl apply -f postgres-deployment.yaml -f server-a-deployment.yaml -f server-b-deployment.yaml`
- run `kubectl apply -f postgres-service.yaml -f server-a-service.yaml -f server-b-service.yaml`
- confirm 
`kubectl get deployments`
`kubectl get pods`
`kubectl get services`
