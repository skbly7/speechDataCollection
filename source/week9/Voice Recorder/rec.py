import pyaudio
import wave
import time
from PyQt4 import QtGui
from PyQt4.QtCore  import *

class VoiceRec(QObject):

	def __init__(self):
		super(VoiceRec, self).__init__()
		self.rec = False

	def record(self):
		self.rec=True
		CHUNK = 1024
		FORMAT = pyaudio.paInt16
		CHANNELS = 2
		RATE = 48000
		WAVE_OUTPUT_FILENAME = "output.wav"

		p = pyaudio.PyAudio()

		stream = p.open(format=FORMAT, channels=CHANNELS, rate=RATE, input=True, frames_per_buffer=CHUNK)

		print("* recording")
		frames = []
		while 1:
			data = stream.read(CHUNK)
			frames.append(data)
			QCoreApplication.processEvents()
			if self.rec == False:
				break

		print("* done recording")

		stream.stop_stream()
		stream.close()
		p.terminate()

		wf = wave.open(WAVE_OUTPUT_FILENAME, 'wb')
		wf.setnchannels(CHANNELS)
		wf.setsampwidth(p.get_sample_size(FORMAT))
		wf.setframerate(RATE)
		wf.writeframes(b''.join(frames))
		wf.close()
		

	def stop(self):
		self.rec=False