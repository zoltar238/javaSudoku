import cv2
import numpy as np
from utils import *
from tensorflow.keras.models import load_model
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'

def preProcesamiento(img):
    # preprocesamiento de la imagen, se transforma a escala de grises, se le aplica
    # desenfoque gausiano para hacer la detecion de los bordes mas facil y se le aplica
    # el trheshold para binarizar la imagen
    imgGris = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    imgBlur = cv2.GaussianBlur(imgGris, (5, 5), 1)
    imgThreshold = cv2.adaptiveThreshold(imgBlur, 255, 1, 1, 11, 2)
    return imgThreshold

def biggestContour(contours):
    # escanea todos los contornos en busca del que tenga la mayor area
    biggest = np.array([])
    max_area = 0
    for i in contours:
        area = cv2.contourArea(i)
        # se mira si el area es mayor que 50 para descartar todos los contornos
        # que sean demasiado pequeños
        if area > 50:
            # la funcion arclengh calcula la longitud del contorno introducido
            peri = cv2.arcLength(i, True)
            # funcion que aproxima el numero de poligonos donde el primer valor es el contorno
            # el segundo epsilon que controla la precision le la aproximacion y el ultimo mira si esta cerrado el contorno
            approx = cv2.approxPolyDP(i, 0.02 * peri, True)
            # si el area es mayor al area maxima y los poligonos son iguales a 4, es decir es un rectangulo
            if area > max_area and len(approx) == 4:
                biggest = approx
                max_area = area
    # biggest es un array de 4 dimensiones lo que permitira recortal la imagen para acomodarse a dichos puntos
    return biggest, max_area

def reorder(myPoints):
    # sirve para ordenar los puntos en el orden correcto
    myPoints = myPoints.reshape((4,2))
    myPointsNew = np.zeros((4,1,2), dtype = np.int32)
    add = myPoints.sum(1)
    myPointsNew[0] = myPoints[np.argmin(add)]
    myPointsNew[3] = myPoints[np.argmax(add)]
    diff = np.diff(myPoints, axis=1)
    myPointsNew[1] = myPoints[np.argmin(diff)]
    myPointsNew[2] = myPoints[np.argmax(diff)]
    return myPointsNew

def splitBoxes(img):
    # la funcion de vsplit divide verticalmente la matriz imagen en nueve partes
    # puesto que nuestro sudoku cuenta con nueve cuadrados
    rows = np.vsplit(img,9)
    boxes = []
    for r in rows:
        cols = np.hsplit(r, 9)
        for box in cols:
            # crea los cuadrados y los añade a la matriz boxes
            boxes.append(box)
    return boxes

def initPredictionModel():
    model = load_model('myModel.h5')
    return model

def getPrediction(boxes, model):
    # utilizando keras para la prediccion de los numeros
    result = []
    for image in boxes:
        # prepara la imagen
        img = np.array(image)
        img = img[4:img.shape[0] - 4, 4:img.shape[1] - 4]
        img = cv2.resize(img, (28,28))
        img = img/255
        img = img.reshape(1,28,28,1)
        # predice el numero
        predictions = model.predict(img,verbose=None)
        classIndex = np.argmax(predictions, axis=-1)
        probabilityValue = np.amax(predictions)
        # guarda el resultado
        if probabilityValue > 0.8:
            result.append(classIndex[0])
        else:
            result.append(0)
    return result