import numpy as np
from matplotlib import pyplot as plt
from matplotlib import animation
import networkx as nx
from prim import *

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
first = ('A', 'B') 

mst, result = prim(G,'A')
othr = [n for n in G.edges() if n[::-1] not in mst and n not in mst]
print("MST: ",mst)
print("Others: ", othr)
pos=nx.spring_layout(G)

x=np.linspace(-np.pi,np.pi,100)
# Function for making figures
def makeFigure():
    # Make The figure
    fig = plt.figure()
    fig.canvas.set_window_title("MST Algorithm Comparison")
    # Add the upper ax
    ax1 = fig.add_subplot(2,1,1)
    ax1.set_xticks([])
    ax1.set_yticks([])
    ax1.set_title("Prim's Algorithm")
    # Plot nodes for ax1
    nx.draw_networkx_nodes(G, pos, node_size=500)
    # Plot edges for ax1
    line1 = nx.draw_networkx_edges(G, pos, width=1)
    # Plot labels for ax1
    labels = nx.get_edge_attributes(G,'weight') # The labels are shared between axes
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
    nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')

    # Add the middle ax
    ax2=fig.add_subplot(2,1,2)
    ax2.set_xticks([])
    ax2.set_yticks([])
    ax2.set_title("Random")
    # Plot nodes for ax2
    nx.draw_networkx_nodes(G, pos, node_size=500)
    # Plot edges for ax2
    line2 = nx.draw_networkx_edges(G, pos, width=1)
    # Plot labels for ax2
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
    nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')

    return fig,ax1, ax2,line1,line2

# Frame rendering function
def renderFrame(i, line1, line2, current_node):
    plt.sca(ax1)
    ax1.cla()
    ax1.set_xticks([])
    ax1.set_yticks([])
    ax1.set_title("Prim's Algorithm")
    # Plot nodes for ax1
    nx.draw_networkx_nodes(G, pos, node_size=500)
    # Plot edges for ax1
    global first
    if (result.index(first) != len(result) - 1):
        current_node = nx.draw_networkx_edges(G, pos, edgelist=[first,], width=6, alpha=0.5, edge_color='g', style='-.')
        line1 = nx.draw_networkx_edges(G, pos, width=1)
        first = result[result.index(first) + 1]
        # Plot labels for ax1
        labels = nx.get_edge_attributes(G,'weight') # The labels are shared between axes
        nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
        nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
    else:
        # ax1.cla()
        nx.draw_networkx_edges(G, pos, edgelist=othr, width=6)
        nx.draw_networkx_edges(G, pos, edgelist=mst, width=6, alpha=0.5, edge_color='b', style='dashed')
        # Plot labels for ax1
        labels = nx.get_edge_attributes(G,'weight') # The labels are shared between axes
        nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
        nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')




    # Apply a phase shift to the line
    plt.sca(ax2)
    ax2.cla()
    ax2.set_xticks([])
    ax2.set_yticks([])
    ax2.set_title("Random")
    # Plot nodes for ax1
    nx.draw_networkx_nodes(G, pos, node_size=500)
    # Plot edges for ax1
    line2 = nx.draw_networkx_edges(G, pos, width=i)
    # Plot labels for ax1
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
    nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
    return line1, line2, current_node

# # List of Animation objects for tracking
anim = []

# Make the figures
figcomps1=makeFigure()

# Animate the figures
fig, ax1, ax2, line1, line2 = figcomps1
anim.append(animation.FuncAnimation(fig,renderFrame,fargs=[line1, line2, first], frames=len(result), interval=700))
figManager = plt.get_current_fig_manager()
figManager.window.showMaximized()
plt.show()