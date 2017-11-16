import os
import time

command = open('run picture.sh', 'r').read()

while True:
    os.system(command)
    time.sleep(3)
