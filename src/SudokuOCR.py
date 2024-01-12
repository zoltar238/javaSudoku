# -*- coding: utf-8 -*-

from DetectaSudokus import resultado
import sys

img = sys.argv[1]
resultado = resultado(img)
print(str(resultado))
