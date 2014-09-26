import sys
from PyQt4 import QtGui
from gui import Gui
from about import About

def main():
	app = QtGui.QApplication(sys.argv)
	gui = Gui()
	sys.exit(app.exec_())

if __name__ == '__main__':
	main()