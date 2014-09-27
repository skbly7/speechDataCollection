import sys
from PyQt4 import QtGui

class Gui(QtGui.QMainWindow):

	def __init__(self):
		super(Gui, self).__init__()
		self.initUI()

	def initUI(self):

		openFile = QtGui.QAction(QtGui.QIcon('open.png'), '&Open', self)
		openFile.setShortcut('Ctrl+O')
		openFile.setStatusTip('Open new File')
		openFile.triggered.connect(self.showDialogOpen)

		exitAction = QtGui.QAction(QtGui.QIcon('exit.png'), '&Exit', self)
		exitAction.setShortcut('Ctrl+Q')
		exitAction.setStatusTip('Exit application')
		exitAction.triggered.connect(QtGui.qApp.quit)

		self.textEdit = QtGui.QTextEdit(self)
		self.setCentralWidget(self.textEdit)

		menubar = self.menuBar()
		fileMenu = menubar.addMenu('&File')
		fileMenu.addAction(openFile)
		fileMenu.addAction(exitAction)

		self.setGeometry(200,200, 500, 200)
		self.setFixedSize(500 , 200)
		self.setWindowTitle('Voice Recorder')
		self.show()

	def showDialogOpen(self):
		fname = QtGui.QFileDialog.getOpenFileName(self, 'Open file','/home')
		if(fname!=""):
			f = open(fname, 'r')
			with f:
				text=f.read()
			f.close
			self.textEdit.setText(text.decode('utf-8'))

def main():
	app = QtGui.QApplication(sys.argv)
	gui = Gui()
	sys.exit(app.exec_())

if __name__ == '__main__':
	main()
