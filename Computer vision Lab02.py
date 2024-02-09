import cv2

img = cv2.imread('images/Objects.jpg')

cv2.imshow('Start image', img)

cv2.waitKey(2000)

gray = cv2.Color(img, cv2.COLOR_BGR2GRAY)




choose = input("")



if choose == "f":
    faces = cv2.CascadeClassfier('faces.xml')
    results = faces.detectMultiScale(gray, scaleFactor=3.7, minNeighbors=3)






     for (x, y, w, h) in results:
	cv2.rectangle(img, (x, y), (x + w, y + h), (0, 0, 255), thickness=3)




     cv2.imshow("Find a person's face", img)
     cv2.waitKey(5000)

elif choose == "c":
    cats = cv2.CascadeClassfier('frontalcatface.xml')
    results = cats.detectMultiScale(gray, scaleFactor=3.1, minNeighbors=3)
    for(x, y, w, h) in results:
	cv2.rectangle(img, (x, y), (x + w, y + h), (0, 0, 255), thickness=3)
    cv2.imshow('Find the cat's face', img)
    cv2.waitKey(0)
