FROM clkao/postgres-plv8:9.6-1.5
MAINTAINER Karthik Muthukumaraswamy <karthik@breezeware.net>

# Set password for 'postgres' system (not DB) user
RUN echo 'postgres:secret' | chpasswd

# Setup Dynamo DB User
ENV POSTGRES_USER dynamo
ENV POSTGRES_PASSWORD dynamo123

# make temporary directory in Docker container for storing DB scripts
RUN mkdir -p /tmp/psql/
RUN mkdir -p /tmp/psql/dynamo

# Adjust PostgreSQL configuration so that remote connections to the
# database are possible.
RUN echo "host all  all    0.0.0.0/0  md5" >> /var/lib/postgresql/data/pg_hba.conf

# And add ``listen_addresses`` to postgresql.conf
RUN echo "listen_addresses='*'" >> /var/lib/postgresql/data/postgresql.conf

# Copy script files to Docker container entry point directory
COPY docker-setup-db.sh /docker-entrypoint-initdb.d/

# Copy SQL files to Docker container temporary directory
COPY dynamo/* /tmp/psql/dynamo/

# Set Port Configurations
EXPOSE 5432

# Set Timezones
RUN echo "Asia/Kolkata" > /etc/timezone
RUN dpkg-reconfigure -f noninteractive tzdata


# Docker build & run commands
# docker build -t dynamo .
# docker run --name dynamo -p 5432:5432 -i -t dynamo