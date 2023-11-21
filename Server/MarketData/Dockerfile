FROM python:3.11-slim-buster

WORKDIR /src

COPY . /src

RUN apt-get update && \
    apt-get install -y gcc libpq5 unixodbc default-libmysqlclient-dev odbcinst  && \
    apt-get autoremove -y && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* && \
    pip install --upgrade pip && \
    pip install pyodbc faker
#
## Download and install MySQL ODBC driver
#RUN apt-get install -y wget && \
#    wget https://dev.mysql.com/get/Downloads/Connector-ODBC/8.0/mysql-connector-odbc_8.0.28-1debian10_amd64.deb && \
#    dpkg -i mysql-connector-odbc_8.0.28-1debian10_amd64.deb && \
#    rm mysql-connector-odbc_8.0.28-1debian10_amd64.deb

# Configure ODBC
COPY odbcinst.ini /etc/odbcinst.ini

CMD ["python", "insert_quotes.py"]
