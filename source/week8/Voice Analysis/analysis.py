import wave
import struct
import contextlib
fname = raw_input()
with contextlib.closing(wave.open(fname,'r')) as f:
    frames = f.getnframes()
    rate = f.getframerate()
    duration = frames / float(rate)
duration1 = duration - 0.01
win= wave.open(fname, 'rb')
wout= wave.open('test2.wav', 'wb')

t0, t1= duration1, duration # get audio for the last 100 seconds
s0, s1= int(t0*win.getframerate()), int(t1*win.getframerate())
win.readframes(s0) # discard
frames= win.readframes(s1-s0)

wout.setparams(win.getparams())
wout.writeframes(frames)

win.close()
wout.close()
wave_file = wave.open("test2.wav", "r")

temp=0
for i in range(wave_file.getnframes()):
    current_frame = wave_file.readframes(1)

    silent = True
    # wave frame samples are stored in little endian**
    # this example works for a single channel 16-bit per sample encoding
    unpacked_signed_value = struct.unpack("<h", current_frame) 
    if abs(unpacked_signed_value[0]) > 500:
        silent = False

    if silent:
        temp=0
    else:
        temp=1
	break
if temp==1:
    print "No silence in the end"
else:
    print "Passed Successfully"
