from PIL import image

img = Image.open("Destop\in.jpg")


img1 = img.convert("L")
img1.show()
