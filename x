curl --request POST \
  --url http://35.225.145.174/default/oauth/token \
  --header 'authorization: Basic c3VwZXJzZWNyZXRjbGllbnQ6c3VwZXJzZWNyZXRjbGllbnQxMjM=' \
  --header 'cache-control: no-cache' \
  --header 'content-type: application/x-www-form-urlencoded' \
  --header 'postman-token: 26951e8d-bbe2-f617-896d-9a31658fe142' \
  --header 'x-tenant: default' \
  --data 'username=testguardian&password=testguardian1123&audience=self&grant_type=password'
