import meArm
import socket
import re
import time


def moveArm(con,arm1,coord):
#get the arm object
    arm=arm1
#initialize the arm
    type(coord[0])
 #   m=re.search(r'\d+',coord[0])
 #   numeric=m.group()
 #   coord[0]=int(numeric)
    print(coord[0])
    m=re.search(r'\d+',coord[1])
    numeric=m.group()
    coord[1]=int(numeric)
    print(coord[1])
    m=re.search(r'\d+',coord[2])
    numeric=m.group()
    coord[2]=int(numeric)
    print(coord[2])
#control the limit of the motors
    #X axis
    if coord[0]<=-190:
        coord[0]=coord[0]+10
    elif coord[0]>=190:
        coord[0]=coord[0]-10
    #Y axis
    if coord[1]<=5:
        coord[1]=10
    elif coord[1]>=210:
        coord[1]=coord[1]-10
    #Z axis
    if coord[2]==-150:
        coord[2]=coord[2]+10
    elif coord[2]==150:
        coord[2]=coord[2]-10
    preciseMove(arm,coord[0],1,1)
    preciseMove(arm,coord[1],1,2)
    preciseMove(arm,coord[2],1,3)
    arm.gotoPoint(coord[0],100,50)
    print("end movement")
 #   con.send("0go".encode())
 #   print("go sended")

def preciseMove(arm1,number,precision,ax):
    arm=arm1
    aum=0
    minus=0
    coor=arm.getPos()
    if ax==1:
        minus=0
        aum=0
        print("asse x")
        minus=number-coor[0]
        aum=coor[0]
        while minus!=0:
            if coor[0]<number:
                aum=aum+precision
                minus=minus-precision
            else:
                aum=aum-precision
                minus=minus+precision
            arm.gotoPoint(aum,coor[1],coor[2])
            print(minus)
           # time.sleep(1)
    if ax==2:
        minus=0
        aum=0
        print("asse y")
        minus=number-coor[1]
        aum=coor[1]
        while minus!=0:
            if coor[1]<number:
                aum=aum+precision
                minus=minus-precision
            else:
                aum=aum-precision
                minus=minus+precision
            arm.gotoPoint(coor[0],aum,coor[2])
            print(minus)
           # time.sleep(1)
    if ax==3:
        minus=0
        aum=0
        print("asse z")
        minus=number-coor[2]
        aum=coor[2]
        while minus!=0:
            if coor[2]<number:
                aum=aum+precision
                minus=minus-precision
            else:
                aum=aum-precision
                minus=minus+precision
            arm.gotoPoint(coor[0],coor[1],aum)
            print(minus)
            #time.sleep(1)
def openHand(arm):
    arm.openGripper()

def closeHand(arm):
    arm.closeGripper()

def createSocket():
    #get the arm
    arm=meArm.meArm()
    arm.begin()
    #Socket data
    HOST=''
    PORT=49676
    
    listOfCoordinates=[]

    #Creation of the server
    mysocket=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
    print("Socket created")
    try:
        mysocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        mysocket.bind((HOST,PORT))
    except socket.error as msg:
        print("bind failed. Error code : "+str(msg[0])+"Message: "+msg[1])
    mysocket.listen(10)
    print("Socket Listening")
    while 1:
        conn, addr=mysocket.accept()
        print("Connected with: "+addr[0] +":"+str(addr[1]))
        i=0
        myCoordinates=[]
        while 1:
                try:
                    #get the coordinates from the socket
                    data=conn.recv(512)
                    stringdata=data.decode('utf-8')
                    if stringdata=="":
                        print("Client disconnesso")
                        print("Socket Listening...")
                        #changeColor("default")
                        conn, addr=mysocket.accept()
                    print (stringdata)
                    myCoordinates=stringdata.split(":")
                    m=re.search(r'\d+',myCoordinates[0])
                    numeric=m.group()
                    myCoordinates[0]=int(numeric)
                    if myCoordinates[0]>1000:
                        myCoordinates[0]=myCoordinates[0]-1000
                        myCoordinates[0]=-myCoordinates[0]
                    m=re.search(r'\d+',myCoordinates[3])
                    numeric=m.group()
                    myCoordinates[3]=int(numeric)
                 #   print(myCoordinates[3])
                    if myCoordinates[3]==0:
                        arm.openGripper()
                    else:
                        arm.closeGripper()
                    for item in myCoordinates:
                        print(item)
                    moveArm(conn,arm,myCoordinates)
                    
                except socket.error as msg:
                    print("Client disconnesso")
                    print("Socket Listening...")
                    conn, addr=mysocket.accept()
                    print("Connected with: "+addr[0] +":"+str(addr[1]))

    mysocket.close()

createSocket()





    
