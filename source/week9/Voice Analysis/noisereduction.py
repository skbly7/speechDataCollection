import os
def noisereduction(inputgiven):
    output = inputgiven.split('.')[0] + 'filtered.wav'
    os.system("sox " + inputgiven + " -n trim 0 0.5 noiseprof myprofile")
    os.system("sox " + inputgiven + " " + output + " noisered myprofile 0.2")
    os.system("sox " + output + " -c 1 " + inputgiven)
    os.remove(output)
