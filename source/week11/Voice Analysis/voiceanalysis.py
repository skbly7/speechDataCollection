""" This module checks for the quality of recorded wav file"""
import wave
import struct
import contextlib
import numpy as np

def getlastslice(inputtaken):
    """ This function takes the last slice from a input wav file"""
    output = inputtaken.split('.')[0] + 'last.wav'
    with contextlib.closing(wave.open(inputtaken, 'r')) as framesget:
        frames = framesget.getnframes()
        rate = framesget.getframerate()
        duration = frames / float(rate)#duration of the input file
    duration1 = duration-0.1
    win = wave.open(inputtaken, 'rb')
    wout = wave.open(output, 'wb')
    time0, time1 = duration, duration1 # get audio for the last 100 miliseconds
    start0 = int(time1 * win.getframerate())
    start1 = int(time0 * win.getframerate())
    win.readframes(start0)
    frames = win.readframes(start1-start0)
    wout.setparams(win.getparams())
    wout.writeframes(frames)
    win.close()
    wout.close()
    return output

def checkendsilence(inputgiven):
    """ This function checks for the endsilence of an input wav file"""
    output = getlastslice(inputgiven)
    wave_file = wave.open(output, "r")
    for i in range(wave_file.getnframes()):
        current_frame = wave_file.readframes(1)
        unpacked_signed_value = struct.unpack("<h", current_frame)
        if abs(unpacked_signed_value[0]) > 500:
            return False
    return True

def checkfrequency(inputgiven):
    """ This function returns the maximum frequency of a wav file"""
    data_size = 40000
    wav_file = wave.open(inputgiven, 'r')
    data = wav_file.readframes(data_size)
    wav_file.close()
    data = struct.unpack('{n}h'.format(n=data_size), data)
    print max(data)
