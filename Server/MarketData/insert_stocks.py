from faker import Faker
import random
import string
import sys

from database import Database

db = Database()
# Tạo đối tượng Faker SKT T1
fake = Faker()

# Tạo danh sách các mã cổ phiếu đã được sử dụng
used_symbols = set("TKL")

arguments = "17"
if len(sys.argv) > 1:
    arguments = sys.argv[1]

def generate_word(min_length=2, max_length=10):
    letters = string.ascii_uppercase
    word_length = random.randint(min_length, max_length)
    word = ''.join(random.choice(letters) for i in range(word_length))
    return word

# Tạo 26 bản ghi giả cho bảng stocks
for i in range(26):
    if i == 0 and arguments == "15_16":
        symbol = "TKL"
        company_name = "Ban Mai Group"
        sector = "Education"
        industry = "Educational system"
    else :
        symbol = generate_word(2,6)
        while symbol in used_symbols:
            symbol = generate_word(2,6)
        used_symbols.add(symbol)
        company_name = fake.company()
        # Các giá trị cho trường sector
        SECTORS = {"Thực phẩm": "Food", "Bất động sản": "Real estate", 
                "Ngân hàng": "Banking", "Bán lẻ": "Retail", "Công nghệ": "Technology", 
                "Cơ khí": "Mechanical", "Chứng khoán": "Securities", "Quỹ đầu tư": "Investment fund", "Giáo dục": "Education"}
        # Các giá trị cho trường industry
        INDUSTRIES = {"Sữa và sản phẩm sữa": "Dairy and dairy products", 
                    "Phát triển bất động sản": "Real estate development", 
                    "Bán lẻ thực phẩm": "Food retailing", "Ngân hàng thương mại": "Commercial banking", 
                    "Công nghệ thông tin": "Information technology", 
                    "Cơ khí chế tạo": "Mechanical manufacturing", 
                    "Chứng khoán đầu tư": "Investment securities", 
                    "Quỹ đầu tư chứng khoán": "Securities investment fund",
                    "Hệ thống trường học": "Educational system"}

        # Tạo giá trị cho trường sector
        sector = random.choice(list(SECTORS.values()))
        # sector_en = SECTORS[sector]

        # Tạo giá trị cho trường industry
        industry = random.choice(list(INDUSTRIES.values()))
        # industry_en = INDUSTRIES[industry]
    
    # Thêm dữ liệu giả vào bảng stocks
    query = "INSERT INTO stocks (symbol, company_name, sector, industry) VALUES (?, ?, ?, ?)"
    params = (symbol, company_name, sector, industry)
    db.execute_query(query, params)

    # print(f"Đã insert bản ghi: {i}")
    # print(f"Mã cổ phiếu: {symbol}, Tên công ty: {company_name}, Ngành: {sector}, Lĩnh vực: {industry}")
print('''\nTết Trung Thu rước đèn đi chơi
Em rước đèn đi khắp phố phường
Lòng vui sướng với đèn trong tay
Em múa ca trong ánh trăng rằm.''')  