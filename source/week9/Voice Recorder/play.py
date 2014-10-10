import pyaudio
import wave
import time
from PyQt4 import QtGui
from PyQt4.QtCore  import *

class Play(QObject):

	def __init__(self):
		super(Play, self).__init__()

	def play(self):

		CHUNK = 15

		wf = wave.open("output.wav", 'rb')

		p = pyaudio.PyAudio()

		stream = p.open(format=p.get_format_from_width(wf.getsampwidth()), channels=wf.getnchannels(), rate=wf.getframerate(), output=True)

		data = wf.readframes(CHUNK)

		while data != '':
			stream.write(data)
			data = wf.readframes(CHUNK)

		stream.stop_stream()
		stream.close()

		p.terminate()
