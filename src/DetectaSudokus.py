import cv2
import numpy as np
from utils import *
from tensorflow.keras.models import load_model
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'

def resultado(imgPath):
    height = 450
    width = 450
    model = initPredictionModel()
    # preprocesamiento de la imagen
    original = cv2.imread(imgPath, 1);
    original = cv2.resize(original, (height, width))
    imgThreshold = preProcesamiento(original)

    # detecion de bordes sobre la imagen original
    imgContour = original.copy()
    imgBIGContour = original.copy()
    countours, hierarchy = cv2.findContours(imgThreshold, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    cv2.drawContours(imgContour,countours, -1, (0,255,0), 3)

    # detectar el mayor contorno y establecerlo como imagen principal
    biggest, maxArea = biggestContour(countours)
    biggest = reorder(biggest)
    if biggest.size != 0:
        cv2.drawContours(imgBIGContour,biggest, -1, (0,255,0), 3)
        pts1 = np.float32(biggest)
        pts2 = np.float32([[0,0],[width,0], [0,height], [width,height]])
        matrix = cv2.getPerspectiveTransform(pts1, pts2)
        imgWarpColored = cv2.warpPerspective(original, matrix, (width,height))
        imgWarpGray = cv2.cvtColor(imgWarpColored, cv2.COLOR_BGR2GRAY)

    boxes = splitBoxes(imgWarpGray)
    result = getPrediction(boxes, model)
    return result
