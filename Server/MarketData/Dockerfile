FROM python:3.11

WORKDIR /src

COPY . /src

RUN apt-get update && \
    apt-get install -y gcc libpq5 unixodbc default-libmysqlclient-dev odbcinst wget && \
    apt-get autoremove -y && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* && \
    pip install --upgrade pip && \
    pip install pyodbc faker


RUN wget https://dev.mysql.com/get/Downloads/Connector-ODBC/8.2/mysql-connector-odbc-8.2.0-linux-glibc2.28-x86-64bit.tar.gz && \
    tar -xvf mysql-connector-odbc-8.2.0-linux-glibc2.28-x86-64bit.tar.gz && \
    rm mysql-connector-odbc-8.2.0-linux-glibc2.28-x86-64bit.tar.gz

# Configure ODBC
COPY odbcinst.ini /etc/odbc.ini

CMD ["python", "insert_quotes.py"]