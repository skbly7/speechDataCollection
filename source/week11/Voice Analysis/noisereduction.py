""" This Module Removes Noise from a Wave File """
import os
def noisereduction(inputgiven):
    """ This part removes the noise from a wav file """
    if os.path.exists(inputgiven):
        output = inputgiven.split('.')[0] + 'filtered.wav'
        os.system("sox " + inputgiven + " -n trim 0 0.5 noiseprof myprofile")
        os.system("sox " + inputgiven + " " + output + \
			" noisered myprofile 0.2")
        os.system("sox " + output + " -c 1 " + inputgiven)
        os.remove(output)
        return True
    return False
