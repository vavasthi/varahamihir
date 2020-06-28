microk8s kubectl apply -f mysql-pv.yaml
microk8s kubectl apply -f mysql-deployment.yaml
microk8s kubectl describe deployment mysql
microk8s kubectl get pods -l app=mysql
microk8s kubectl describe pvc mysql-pv-claim
microk8s kubectl run -it --rm --image=mysql:5.6 --restart=Never mysql-client -- mysql -h mysql -ppassword
microk8s kubectl delete deployment,svc mysql
microk8s kubectl delete pvc mysql-pv-claim
microk8s kubectl delete pv mysql-pv-volume

microk8s kubectl run -it --rm --restart=Never --image=infoblox/dnstools:latest dnstools