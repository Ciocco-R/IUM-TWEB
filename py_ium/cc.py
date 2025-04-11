import os
from tkinter import *
import networkx as nx
import Levenshtein


def new_possible_nodes(G,start, end):
    if not G.has_node(start):
        G.add_node(start)
        for node in G.nodes():
            word1 = node
            distance = Levenshtein.distance(word1, start)
            if distance == 1:
                G.add_edge(word1, start, weight=distance)

    if not G.has_node(end):
        G.add_node(end)
        for node in G.nodes():
            word1 = node
            distance = Levenshtein.distance(word1, end)
            if distance == 1:
                G.add_edge(word1, end, weight=distance)
    return G

def graph_building () :
    with open('words.italian.txt', 'r') as file:
        contenuto = file.read()
    words = contenuto.split()

    G = nx.Graph()

    for word in words:
        G.add_node(word)
        for node in G.nodes():
            word1 =node
            distance = Levenshtein.distance(word1, word)
            if distance == 1:
                G.add_edge(word1, word, weight=distance)
    if os.path.exists("graph.gml"):
        os.remove("graph.gml")
    nx.write_gml(G, "graph.gml")

def main (start ,end):
    if  (not os.path.exists("graph.gml")) or os.path.getsize("graph.gml") == 0:
        graph_building()

    G = nx.read_gml("graph.gml")
    G = new_possible_nodes(G,start, end)
    try :
        path = nx.dijkstra_path(G, start, end)
    except:
        path = "non trovato"
        print(path)
        return path,0
    i=0
    str=path.pop(0)

    for parent in path:
        str = str + ' -> ' + parent
        i = i + 1
    print(str,i)
    return  str,i


class MyWindow:
    def __init__(self, win):
        self.lblTitolo = Label(win, text='Percorso minimo tra due parole', width=50, height=1)
        self.lbl1 = Label(win, text='Start : ', width=7,height=1)
        self.lbl2 = Label(win, text='End : ',width=7,height=1)
        self.lbl3 = Label(win, text='N° passi :')
        self.lbl4 = Label(win, text='Percorso')
        self.t1 = Entry()
        self.t2 = Entry()
        self.t3 = Label(width=10,background="white")
        self.t4 = Label(width=63,background="white")
        self.lblTitolo.place(x=50, y=20)
        self.lblTitolo.config(font=("Courier", 15))
        self.lbl1.place(x=110, y=60)
        self.t1.place(x=160, y=60)
        self.lbl2.place(x=350, y=60)
        self.t2.place(x=408, y=60)
        self.b1 = Button(win, text='Trova', command=self.add,width=60)
        self.b1.place(x=100, y=100)
    def add(self):
        str1 = str(self.t1.get())
        str2 = str(self.t2.get())

        path,passi=main(str1,str2)

        self.lbl3.place(x=100, y=200)
        self.t3.place(x=200, y=200)
        self.t3.config(text=str(passi))
        self.lbl4.place(x=320, y=250)
        self.lbl4.config(font=12)
        self.t4.place(x=100, y=270)
        self.t4.config(text=str(path))


window = Tk()
mywin = MyWindow(window)
window.title('Progetto_IUM_2023')
window.geometry("700x400")
window.mainloop()
