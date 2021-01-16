import cv2
import youtube_dl
import requests
import pafy


def car_detection(car_vid_url):
    cars_xml_url = \
        "https://github.com/Brokoth/TrafficAppData/blob/main/Vehicle%20and%20pedestrain%20detection/cars.xml?raw=true"
    cars_xml_file = requests.get(cars_xml_url).content
    with open('cars.xml', 'wb') as file:
        file.write(cars_xml_file)
    cars_classifier = cv2.CascadeClassifier('cars.xml')
    video = pafy.new(car_vid_url)
    best = video.getbest()
    play_url = best.url
    camera = cv2.VideoCapture(play_url)
    fps = camera.get(cv2.CAP_PROP_FPS)
    frame_count = int(camera.get(cv2.CAP_PROP_FRAME_COUNT))
    duration = frame_count / fps
    count = 0
    print(count, ',', frame_count, ',', fps, ',', duration)
    while True:
        ret, img = camera.read()
        if img is not None:
            height, width = img.shape[0:2]
            img[0:70, 0:width] = [0, 0, 255]
            cv2.putText(img, 'CAR COUNT:', (10, 50), cv2.FONT_HERSHEY_SIMPLEX, 1.5, (255, 255, 255), 2)
            cv2.line(img, (0, height - 200), (width, height - 200), (0, 255, 255), 2)
            blur = cv2.blur(img, (3, 3))
            gray = cv2.cvtColor(blur, cv2.COLOR_BGR2GRAY)
            cars = cars_classifier.detectMultiScale(gray)
            for (x, y, w, h) in cars:
                car_cy = int(y + h / 2)
                line_cy = height - 200
                if line_cy + 6 > car_cy > line_cy - 6:
                    count = count + 1
                cv2.rectangle(img, (x, y), (x + w, y + h), (0, 255, 0), 2)
                cv2.putText(img, 'CAR', (x, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 2)
                cv2.putText(img, str(count), (500, 50), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 2)
            cv2.imshow('live', img)
            key = cv2.waitKey(1)
            if key == 27:
                break
        else:
            break
    cv2.destroyAllWindows()
    camera.release()
    print(count, ',', frame_count, ',', fps, ',', duration)
    return count, ',', frame_count, ',', fps, ',', duration


vid_url = "https://www.youtube.com/watch?v=Y1jTEyb3wiI&list=PLxgQHbsjYVOhmhdGps5Fiw16Av3Ni1Kz0&index=22"
car_detection(vid_url)
