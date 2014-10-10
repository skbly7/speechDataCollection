import wave
import struct
import contextlib

def getlastslice(inputtaken):
    output = inputtaken.split('.')[0] + 'last.wav'
    with contextlib.closing(wave.open(inputtaken, 'r')) as framesget:
        frames = framesget.getnframes()
        rate = framesget.getframerate()
        duration = frames / float(rate)#duration of the input file
    duration1 = duration-0.01
    win = wave.open(inputtaken, 'rb')
    wout = wave.open(output, 'wb')
    time0, time1 = duration1, duration # get audio for the last 100 miliseconds
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
    output = getlastslice(inputgiven)
    wave_file = wave.open(output, "r")
    temp = 0
    for i in range(wave_file.getnframes()):
        current_frame = wave_file.readframes(1)
        silent = True
        unpacked_signed_value = struct.unpack("<h", current_frame)
        if abs(unpacked_signed_value[0]) > 500:
            silent = False
        if silent:
            temp = 0
        else:
            temp = 1
            break
    if temp == 1:
        return False
    else:
        return True
