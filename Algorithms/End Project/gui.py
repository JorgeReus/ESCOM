import sys
import os
from PyQt5.QtWidgets import QApplication, QWidget, QPushButton, QHBoxLayout, QGroupBox, QDialog, QVBoxLayout, QGridLayout, QLabel
from PyQt5.QtGui import QIcon, QPixmap
from PyQt5.QtCore import pyqtSlot
 
class App(QWidget):
 
    def __init__(self):
        super().__init__()
        self.title = 'Minimun Spanning tree algorithm animations'
        self.left = 600
        self.top = 300
        self.width = 640
        self.height = 480
        self.initUI()
 
    def initUI(self):
        self.setWindowTitle(self.title)
        self.setGeometry(self.left, self.top, self.width, self.height)
        self.setWindowIcon(QIcon('icon.jpg'))
        # Prim
        self.prim_button = QPushButton("Prim's Algorithm Animation", self)
        self.prim_button.setToolTip('Runs the animation for prim')
        self.prim_button.clicked.connect(lambda: self.on_click("prim_animation.py"))
        # Kruskal
        self.kruskal_button = QPushButton("Kruskal's Algorithm Animation", self)
        self.kruskal_button.setToolTip('Runs the animation for kruskal')
        self.kruskal_button.clicked.connect(lambda: self.on_click("kruskal_animation.py"))
        # Comparison
        self.comparison_button = QPushButton("Comparison Animation", self)
        self.comparison_button.setToolTip('Runs both animations for comparison')
        self.comparison_button.clicked.connect(lambda: self.on_click("comparison_animation.py"))
        self.escom_pixmap = QPixmap("escom_image")
        self.subject_pixmap = QPixmap("subject")
        self.createGridLayout()
        self.show()

    def createGridLayout(self):
        layout = QGridLayout()
        escom_label = QLabel(self)
        escom_label.setPixmap(self.escom_pixmap)
        subject_label = QLabel(self)
        subject_label.setPixmap(self.subject_pixmap)
        self.setLayout(layout)

        layout.addWidget(escom_label, 0, 0, 1, 3)
        layout.addWidget(subject_label, 1, 1, 1, 3)
        layout.addWidget(self.prim_button, 2, 0) 
        layout.addWidget(self.kruskal_button, 2, 1) 
        layout.addWidget(self.comparison_button, 2, 2) 


    @pyqtSlot()
    def on_click(self, file):
        os.system("python {}".format(file))
        
if __name__ == '__main__':
    app = QApplication(sys.argv)
    ex = App()
    sys.exit(app.exec_())