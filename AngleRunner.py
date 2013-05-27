#!usr/bin/python

import os
import sys
import math

values = []

#take in values from terminal
for h in range(len(sys.argv)):
    if h>0:
        num = sys.argv[h]
        values.append(float(num))

sun = "sun 0.0 0.0 1.9891E+30 0.0\n"
mercury = "mercury -2.10526389E+10 -6.6406696E+10 36653.0187 -12289.8482 3.302E+23 0.0\n"
venus = "venus -1.07505643E+11 -3.36652386E+9 889.160643 -35159.2381 48.685E+23 0.0\n"
earth = "earth -2.52109509E+10 1.44928045E+11 -29839.859 -5207.63851 5.9736E+24 0.0\n"
mars = "mars 2.07995235E+11 -3.14301182E+9 1295.00459 26294.4434 6.4185E+23 0.0\n"

planets = [sun, mercury, venus, earth, mars]

theta_0 = (values[0]*math.pi)/(180.0) #get initial values
theta_1 = (values[1]*math.pi)/(180.0)
v_0 = values[2]

file_name = "./MarsDataIn/dataIn" + str(int(values[3])) + ".data"

dv = 20; #number of chunks

end = dv*dv;
theta_int = (theta_1 - theta_0)/end #intervals

#print out range of values to numbered file for planets
f = open(file_name, 'w')

for p in range(len(planets)):
    f.write(planets[p]) #first print planet data to file

for i in range(1, end): #print satellites to file
    x_value = v_0*math.cos(theta_0 + i*theta_int)
    y_value = v_0*math.sin(theta_0 + i*theta_int)
    f.write("st" + str(i) + " 0.0 0.0 " + str(x_value) + " " + str(y_value) + " 1000.0 0.0\n")

f.close()

#run numbered java simulation
os.system("java MarsTest " + str(values[3])) #five planets
