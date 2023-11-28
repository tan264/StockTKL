FROM python:3.12.0-slim-bookworm

WORKDIR /usr/src/app


#COPY requirements.txt ./
COPY . ./

RUN apt-get update && apt-get install -y \
    unixodbc \
    unixodbc-dev

# Install MySql ODBC
ADD https://cdn.mysql.com//Downloads/Connector-ODBC/8.2/mysql-connector-odbc-8.2.0-linux-glibc2.28-x86-64bit.tar.gz .
RUN tar -C . -xzvf mysql-connector-odbc-8.2.0-linux-glibc2.28-x86-64bit.tar.gz
RUN cp -r mysql-connector-odbc-8.2.0-linux-glibc2.28-x86-64bit/bin/* /usr/local/bin
RUN cp -r mysql-connector-odbc-8.2.0-linux-glibc2.28-x86-64bit/lib/* /usr/local/lib
RUN myodbc-installer -a -d -n "MySQL ODBC 8.2 ANSI Driver" -t "Driver=/usr/local/lib/libmyodbc8a.so"
RUN myodbc-installer -d -l

# Install python library
RUN pip install --no-cache-dir -r requirements.txt

RUN chmod +x entrypoint.sh

CMD ["./entrypoint.sh"]
