#!/bin/bash

# Đường dẫn tới file passwd mà Mosquitto sẽ sử dụng
PASSWD_FILE=/mosquitto/config/passwd

# Xóa file passwd cũ nếu tồn tại
if [ -f "$PASSWD_FILE" ]; then
    rm $PASSWD_FILE
fi

# Đọc danh sách user và password từ file userlist.txt
while IFS=: read -r username password; do
    mosquitto_passwd -b $PASSWD_FILE "$username" "$password"
done < /mosquitto/config/userlist.txt

# Khởi động Mosquitto
mosquitto -c /mosquitto/config/mosquitto.conf
