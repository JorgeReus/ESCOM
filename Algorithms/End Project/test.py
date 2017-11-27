#!/usr/bin/env python
"""
An example for Prim's Algorithm
"""
__author__ = """Jorge Reus (http://github.com/JorgeReus/)"""

try:
    import matplotlib.pyplot as plt
except:
    raise
try:
    import networkx as nx
except:
    raise

from prim import *
from kruskal import *

G=nx.Graph()

G.add_edge('A','B',weight=3)
G.add_edge('A','D',weight=5)
G.add_edge('A','E',weight=9)
G.add_edge('B','C',weight=5)
G.add_edge('B','D',weight=4)
G.add_edge('B','E',weight=8)
G.add_edge('C','D',weight=7)
G.add_edge('C','G',weight=3)
G.add_edge('D','F',weight=8)
G.add_edge('D','G',weight=5)
G.add_edge('D','H',weight=6)
G.add_edge('E','F',weight=2)
G.add_edge('F','H',weight=10)
G.add_edge('G','I',weight=1)
G.add_edge('H','I',weight=3)

# mst, result = prim(G,'A')
history, mst, cycles = kruskal(G)
othr = [n for n in G.edges() if n not in mst and n[::-1] not in mst]

# print ("Minimal Spanning Tree: PrimA", mst)
# print ("Result: ", result)
# mst2, result = prim2(G,'A')
# print ("Minimal Spanning Tree: PrimB", mst2)
print ("kruskal:", mst)
print ("Cycles:", cycles)
print ("history:", history)
# print ("History PrimB", result)
# print


pos=nx.spring_layout(G)

# nodes
nx.draw_networkx_nodes(G, pos, node_size=500)

# edges
nx.draw_networkx_edges(G, pos, edgelist=othr, width=6)
nx.draw_networkx_edges(G, pos, edgelist=mst, width=6, alpha=0.5, edge_color='b', style='dashed')

# labels
labels = nx.get_edge_attributes(G,'weight')
nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
nx.draw_networkx_labels(G,pos,font_size=20,font_family='sans-serif')

plt.axis('off')
# plt.savefig("weighted_graph.png") # save as png
plt.show() # display
