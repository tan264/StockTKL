from datetime import datetime, timedelta
import random, time
from database import Database
import sys

db = Database()
stock_id_from = 1
stock_id_to = 26

arguments = "17"
if len(sys.argv) > 1:
    arguments = sys.argv[1]

def insert_first_quotes(n=26):
    start_date = datetime(2020, 4, 26)
    end_date = datetime(2023, 5, 14)
    delta = end_date - start_date
    for i in range(stock_id_from, stock_id_to + 1):
        stock_id = i
        price = round(random.uniform(1, 100), 2)
        change_value = round(random.uniform(-10, 10), 2)
        percent_change = round(change_value / price * 100, 2)
        volume = random.randint(1000, 1000000)
        # Tạo ngày giờ ngẫu nhiên
        day = random.randint(1, delta.days)
        seconds = random.randint(0, 24*60*60)
        microseconds = random.randint(0, 1000000)
        time_stamp = start_date + timedelta(days=day, seconds=seconds, microseconds=microseconds)

        sql = "INSERT INTO quotes (stock_id, price, change_value, percent_change, volume, time_stamp) VALUES (?, ?, ?, ?, ?, ?)"
        values = (stock_id, price, change_value, percent_change, volume, time_stamp)
        print("Inserting: ", values)
        db.execute_query(sql, values)    

def insert_10k_records(n=100):        
    # Kiểm tra số lượng bản ghi hiện có trong bảng        
    db.cursor.execute('SELECT COUNT(*) FROM quotes')
    row = db.cursor.fetchone()
    count = row[0]
    if count >= 100000:
        print('Đã có đủ 100000 bản ghi.')
        return

    # Thêm 100000 bản ghi fake
    start_date = datetime(2020, 4, 26)
    end_date = datetime(2023, 5, 14)
    delta = end_date - start_date
    for i in range(n):
        stock_id = random.randint(stock_id_from, stock_id_to)
        price = round(random.uniform(1, 100), 2)
        change_value = round(random.uniform(-10, 10), 2)
        percent_change = round(change_value / price * 100, 2)
        volume = random.randint(1000, 1000000)
        # Tạo ngày giờ ngẫu nhiên
        day = random.randint(1, delta.days)
        seconds = random.randint(0, 24*60*60)
        microseconds = random.randint(0, 1000000)
        time_stamp = start_date + timedelta(days=day, seconds=seconds, microseconds=microseconds)

        sql = "INSERT INTO quotes (stock_id, price, change_value, percent_change, volume, time_stamp) VALUES (?, ?, ?, ?, ?, ?)"
        values = (stock_id, price, change_value, percent_change, volume, time_stamp)
        print("Inserting: ", values)
        db.execute_query(sql, values)        
    # print('Thêm dữ liệu thành công.')

def insert_random_quotes(delay=2):    
    while True:
        stock_id = random.randint(stock_id_from, stock_id_to)
        price = round(random.uniform(100, 1000), 2)
        change_value = round(random.uniform(-50, 50), 2)
        percent_change = round(random.uniform(-5, 5), 2)
        volume = random.randint(1000, 10000)
        time_stamp = datetime.now()
        values = (stock_id, price, change_value, percent_change, volume, time_stamp)
        print("Inserting: ", values)        
        sql = "INSERT INTO quotes(stock_id, price, change_value, percent_change, volume, time_stamp) VALUES (?, ?, ?, ?, ?, ?)"
        db.execute_query(sql, values)        
        time.sleep(delay)

if(arguments == "new"):
    insert_first_quotes()
elif(arguments == "dangerous"):
    insert_10k_records()
else:
    insert_random_quotes()