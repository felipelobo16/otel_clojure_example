from websocket import create_connection
ws = create_connection("ws://localhost:8080/ws")
print(ws.recv())
print("Sending 'Hello, World'")
ws.send("Hello, World")
print("Close")
ws.close()