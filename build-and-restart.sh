docker build -t psa .
docker stop psa || true
docker rm psa || true
docker run -d -p 8082:8080 --name=psa --network dbr-network --restart unless-stopped psa