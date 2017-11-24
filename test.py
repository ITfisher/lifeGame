from tkinter import *
import random





class getPosition:

    def __init__(self, root):
        self.root = root
        root.bind('<Button-1>', self.action)
    def action(self, event):


        print( u"窗口坐标x:%d 窗口坐标y:%d\n" % (event.x, event.y))






    #画格子
def checkered(canvas, line_distance):

    for xy in range(10,canvas_width-9,line_distance):
        canvas.create_line(xy, 10, xy, canvas_height-9, fill="#476042")
    for yy in range(10,canvas_height-9,line_distance):
        canvas.create_line(10, yy, canvas_width-9, yy, fill="#476042")


master = Tk()#创建主窗体
master.title("生命游戏")#命名
# master.resizable(width=False, height=False)
canvas_width = 1000#画布宽度
canvas_height = 800#画布高度
#画随机点
def drawRects():
    for q in range(5):
        x = random.randint(0, 75)
        y = random.randint(0, 55)
        w.create_rectangle(10 * x, 10 * y, 10 * x + 10, 10 * y + 10, fill="#476042")



w = Canvas(master,width=canvas_width,height=canvas_height,bg="white")#创建画布
w.pack()#显示元件
def removeRects():
    for x in range(100):
        for y in range(80):
            w.create_rectangle(10 * x, 10 * y, 10 * x + 10, 10 * y + 10, fill="white")

def click_action():
    print('you hit me')
button=Button(master,text="hit me",width=15,height=2,command=drawRects)
button1=Button(master,text="dfsdf",width=15,height=2,command=removeRects)
button.pack()
button1.pack()



checkered(w,10)#画格子
# drawRects(w,5)#画点

window = getPosition(master)
master.minsize(1000,900)
master.maxsize(1000,900)


mainloop()#进入窗体的主循环