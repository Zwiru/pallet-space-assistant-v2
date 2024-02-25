docker build -t psa .
docker stop psa || true
docker rm psa || true
docker run -d -p 8080:8080 --name psa psa