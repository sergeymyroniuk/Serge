import cv2

hog = cv2.HOGDescriptor
hog.setSVMDetector(cv2.HOGDescriptor_getDefaultPeopleDetector())

image = cv2.imread('Desktop/img06.jpg')
assert not isinstance(image,type(None)), 'image not found'

image = imutils.resize(image, width=min(400, image.shape[1]))



regions, _) = hog.detectMultiScale(image, winStride=(4,4), padding=(4, 4), scale=1.05)

for(x, y, w, h) in regions:
    cv2.rectangle(img, (x, y), (x + w, y + h), (0, 0, 255), 2)
cv2.imshow('Find the cat's face', img)
cv2.waitKey(0)
