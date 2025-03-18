import sys, os, json
import time, uuid

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
LIB_PATH = os.path.join(BASE_DIR, "python3", "Lib", "site-packages")

if LIB_PATH not in sys.path:
    sys.path.append(LIB_PATH)

def save_json(input_data):
    print(f"{input_data}")

    data = {
        "name": "Test Building",
        "northAxis": 0,
        "weather": "seoul",
        "address": "서울특별시 관악구 관악로 1",
        "terrain": "city"
    }

    file_path = os.path.join(BASE_DIR, str(uuid.uuid4()) + "data.json")

    with open(file_path, "w", encoding="utf-8") as json_file:
        json.dump(data, json_file, ensure_ascii=False, indent=4)

    time.sleep(5)

    print(f"{os.path.abspath(file_path)}")

if __name__ == "__main__":
    save_json(sys.argv[1])
