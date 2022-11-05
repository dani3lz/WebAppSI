# Getting Started

In order to be able to start the application, first of all we have to install docker.
After we installed docker, we have to download the image for PostgreSQL.
Then we need to start the image with our configurations.

## Docker install

We can install Docker for free from the official website:
* https://www.docker.com/

## PostgreSQL image

After successfully installing docker, open the command line:

   ```sh
  docker pull postgres
   ```

## Create own container

After everything has downloaded, we can write the following command:

   ```sh
  docker run --name postgres-1 -p 5432:5432 -e POSTGRES_USERNAME=postgres -e POSTGRES_PASSWORD=postgres -d postgres
   ```

* --name - the name of the container
* -p - port range
* -e - environment variable
* -d - database name

If everything went successfully, the container will automatically start.

---

### JDBC

```sh
  jdbc:postgresql://localhost:5432/postgres
   ```