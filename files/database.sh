docker run --name some-postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres

docker exec -it some-postgres bash