fh = open("imageToSave.png", "wb")
fh.write(str.decode('base64'))
fh.close()
