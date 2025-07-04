@echo off
echo ==============================
echo  Starting to deploy all microservices to K8s ...
echo ==============================

REM Deploy Eureka registration center
echo  Deploying eureka-service ...
kubectl apply -f eureka-service\eureka-service-deployment.yaml
kubectl apply -f eureka-service\eureka-service-service.yaml

REM Deploy house-client-service
echo  Deploying house-client-service ...
kubectl apply -f house-client-service\house-client-deployment.yaml
kubectl apply -f house-client-service\house-client-service.yaml

REM Deploy house-admin-service
echo Deploying house-admin-service ...
kubectl apply -f house-admin-service\house-admin-deployment.yaml
kubectl apply -f house-admin-service\house-admin-service.yaml

REM Deploy user-service
echo Deploying user-service ...
kubectl apply -f user-service\user-deployment.yaml
kubectl apply -f user-service\user-service.yaml

REM Deploy api-gateway registration center
echo  Deploying api-gateway ...
kubectl apply -f api-gateway\api-gateway-deployment.yaml
kubectl apply -f api-gateway\api-gateway-service.yaml


echo ==============================
echo All services deployed successfully!
echo ==============================
pause
