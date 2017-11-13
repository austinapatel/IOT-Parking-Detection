content = open('img_data.txt','r').read()

# fh = open("imageToSave.png", "wb")
# fh.write(content.decode('base64'))
# fh.close()
import base64
with open("imageToSave.png", "wb") as fh:
    fh.write(base64.standard_b64decode(content))
    fh.close()
