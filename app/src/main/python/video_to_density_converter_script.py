import os
import cv2
import os.path
import requests
from pytube import YouTube
import numpy as np
from com.arthenica.mobileffmpeg import FFmpeg
from com.arthenica.mobileffmpeg import FFprobe


def vid_saving(url1, url2, url3, url4, frame_gap, initial_path):
    cars_xml_url = \
        "https://github.com/Brokoth/TrafficAppData/blob/main/Vehicle%20and%20pedestrain%20detection/cars.xml?raw=true"
    cars_xml_file = requests.get(cars_xml_url).content
    with open(initial_path + '/cars.xml', 'wb') as file:
        file.write(cars_xml_file)
    cars_classifier = cv2.CascadeClassifier(initial_path + '/cars.xml')
    count = 1
    lane1_traffic_density_values = []
    lane2_traffic_density_values = []
    lane3_traffic_density_values = []
    lane4_traffic_density_values = []
    while count < 5:
        if count == 1:
            yt = YouTube(url1)
            vid_length_in_seconds = yt.length
            stream = yt.streams.filter(file_extension='mp4').first()
            stream.download(output_path=initial_path, filename='vid1')
            path = initial_path
            path += '/'
            path += 'vid1'
            path += '.mp4'
            FFmpeg.execute("-i " + path + " -r " + frame_gap + " -f image2 " + initial_path + "/image-%2d.png")
            os.remove(path)
            image_read_counter = 1
            while image_read_counter < int(vid_length_in_seconds * frame_gap):
                str_image_read_counter = '%02d' % image_read_counter
                image_path = initial_path + '/image-' + str_image_read_counter + '.png'
                img = cv2.imread(image_path)
                if img is not None:
                    blur = cv2.blur(img, (3, 3))
                    gray = cv2.cvtColor(blur, cv2.COLOR_BGR2GRAY)
                    cars = cars_classifier.detectMultiScale(gray)
                    cars_count = 0
                    for (x, y, w, h) in cars:
                        cars_count = cars_count + 1
                    lane1_traffic_density_values.append(cars_count)
                else:
                    break
                os.remove(image_path)
                image_read_counter = image_read_counter + 1
            count = count + 1
        elif count == 2:
            yt = YouTube(url2)
            vid_length_in_seconds = yt.length
            stream = yt.streams.filter(file_extension='mp4').first()
            stream.download(output_path=initial_path, filename='vid2')
            path = initial_path
            path += '/'
            path += 'vid2'
            path += '.mp4'
            FFmpeg.execute("-i " + path + " -r " + frame_gap + " -f image2 " + initial_path + "/image-%2d.png")
            os.remove(path)
            image_read_counter = 1
            while image_read_counter < int(vid_length_in_seconds * frame_gap):
                str_image_read_counter = '%02d' % image_read_counter
                image_path = initial_path + '/image-' + str_image_read_counter + '.png'
                img = cv2.imread(image_path)
                if img is not None:
                    blur = cv2.blur(img, (3, 3))
                    gray = cv2.cvtColor(blur, cv2.COLOR_BGR2GRAY)
                    cars = cars_classifier.detectMultiScale(gray)
                    cars_count = 0
                    for (x, y, w, h) in cars:
                        cars_count = cars_count + 1
                    lane2_traffic_density_values.append(cars_count)
                else:
                    break
                os.remove(image_path)
                image_read_counter = image_read_counter + 1
            count = count + 1
        elif count == 3:
            yt = YouTube(url3)
            vid_length_in_seconds = yt.length
            stream = yt.streams.filter(file_extension='mp4').first()
            stream.download(output_path=initial_path, filename='vid3')
            path = initial_path
            path += '/'
            path += 'vid3'
            path += '.mp4'
            FFmpeg.execute("-i " + path + " -r " + frame_gap + " -f image2 " + initial_path + "/image-%2d.png")
            os.remove(path)
            image_read_counter = 1
            while image_read_counter < int(vid_length_in_seconds * frame_gap):
                str_image_read_counter = '%02d' % image_read_counter
                image_path = initial_path + '/image-' + str_image_read_counter + '.png'
                img = cv2.imread(image_path)
                if img is not None:
                    blur = cv2.blur(img, (3, 3))
                    gray = cv2.cvtColor(blur, cv2.COLOR_BGR2GRAY)
                    cars = cars_classifier.detectMultiScale(gray)
                    cars_count = 0
                    for (x, y, w, h) in cars:
                        cars_count = cars_count + 1
                    lane3_traffic_density_values.append(cars_count)
                else:
                    break
                os.remove(image_path)
                image_read_counter = image_read_counter + 1
            count = count + 1
        else:
            yt = YouTube(url4)
            vid_length_in_seconds = yt.length
            stream = yt.streams.filter(file_extension='mp4').first()
            stream.download(output_path=initial_path, filename='vid4')
            path = initial_path
            path += '/'
            path += 'vid4'
            path += '.mp4'
            FFmpeg.execute("-i " + path + " -r " + frame_gap + " -f image2 " + initial_path + "/image-%2d.png")
            os.remove(path)
            image_read_counter = 1
            while image_read_counter < int(vid_length_in_seconds * frame_gap):
                str_image_read_counter = '%02d' % image_read_counter
                image_path = initial_path + '/image-' + str_image_read_counter + '.png'
                img = cv2.imread(image_path)
                if img is not None:
                    blur = cv2.blur(img, (3, 3))
                    gray = cv2.cvtColor(blur, cv2.COLOR_BGR2GRAY)
                    cars = cars_classifier.detectMultiScale(gray)
                    cars_count = 0
                    for (x, y, w, h) in cars:
                        cars_count = cars_count + 1
                    lane4_traffic_density_values.append(cars_count)
                else:
                    break
                os.remove(image_path)
                image_read_counter = image_read_counter + 1
            count = count + 1
    # print(os.listdir(initial_path))
    return str(lane1_traffic_density_values) + '/' + str(lane2_traffic_density_values) + '/' + str(lane3_traffic_density_values) + '/' + str(lane4_traffic_density_values)
