import pyodbc

class Database:
    def __init__(self):
        self.server_name = 'db'
        self.database_name = 'stock_tkl'
        self.port = '3306'
        self.username = 'tan264'
        self.password = '123456'
        self.driver= 'MySQL ODBC 8.2 ANSI Driver'                
        self.connection_string = ('DRIVER={driver};'
                                  'SERVER={server_name},{port};'
                                  'DATABASE={database_name};'
                                  'UID={username};'
                                  'PWD={password};TrustServerCertificate=Yes;MULTI_HOST=1'
                                  'charset=utf8mb4').format(
                                        driver=self.driver,
                                        server_name=self.server_name,
                                        port=self.port,
                                        database_name=self.database_name,
                                        username=self.username,
                                        password=self.password
                                    )    
        print("Connection String:", self.connection_string)
        self.conn = pyodbc.connect(self.connection_string)
        self.conn.setdecoding(pyodbc.SQL_CHAR, encoding='utf-8')
        self.conn.setdecoding(pyodbc.SQL_WCHAR, encoding='utf-8')
        self.conn.setencoding(encoding='utf-8')
        self.cursor = self.conn.cursor()    


    def execute_query(self, query, params):
        self.cursor.execute(query, params)
        self.conn.commit()