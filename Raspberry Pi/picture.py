import base64
from picamera import PiCamera
from time import sleep
camera = PiCamera()
camera.capture('/home/pi/Desktop/image.jpg')
with open("image.jpg", "rb") as imageFile:
        str = base64.b64encode(imageFile.read())
        print str
#convert string back to image
#fh = open("imageToSave.png", "wb")
#fh.write(str.decode('base64'))
#fh.close()
#https://www.programcreek.com/2013/09/convert-image-to-string-in-python/
        
