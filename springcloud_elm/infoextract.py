from paddlenlp import Taskflow
import socket

schema = ['姓名', '电话', '住址']  # Define the schema for entity extraction
ie = Taskflow('information_extraction', schema=schema, position_prob=0.001, home_path=r"E:\SDK\python")
ie('s')

serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
host = socket.gethostname()
port = 9999
serversocket.bind(('localhost', port))
serversocket.listen(5)

while True:
    # 建立客户端连接
    clientsocket, addr = serversocket.accept()
    print("连接地址: %s" % str(addr))
    name = phone = address = ''
    s = clientsocket.recv(1024).decode('utf-8')
    s = s[2:]
    x = ie(s)
    tosend = ''
    for key in schema:
        if key not in x[0]:
            continue
        maxPro = 0
        if key == '住址':
            x[0][key].sort(key=lambda i: i["start"])
            for ele in x[0][key]:
                address = address + ele['text']
        elif key == '姓名':
            for ele in x[0][key]:
                if ele['probability'] > maxPro:
                    name = ele['text']
                    maxPro = ele['probability']
                    if len(name) < 3:
                        if ele['start'] + 2<len(s) and len(s[ele['start'] + 2].encode('utf-8')) > 1:
                            name = s[ele['start']:(ele['start'] + 3)]
                        else:
                            name = s[ele['start']:(ele['start'] + 2)]
        elif key == '电话':
            for ele in x[0][key]:
                if ele['probability'] > maxPro:
                    phone = ele['text']
                    maxPro = ele['probability']
                    if len(phone) < 11:
                        phone = s[ele['start']:(ele['start'] + 11)]
    # 第2轮筛选
    for key in schema:
        if key not in x[0]:
            continue
        for item in x[0][key]:
            if len(item['text']) == 11 and item['text'].isnumeric():
                phone = item['text']
    tosend = name + '\t' + phone + '\t' + address + '\r\n'
    clientsocket.send(tosend.encode('utf-8'))
    clientsocket.close()
    print(f'{name},{phone},{address}')
