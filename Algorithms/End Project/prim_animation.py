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

# Make the MST and the History(For animation steps)
mst, history = prim(G,'A')

print("MST: ",mst)
print("HIstory", history)
# Layout of the Graph
pos = nx.spring_layout(G)
# Lists for keeping track of the nodes and edges
visited_edges = []
visited_nodes = []

# Function for making figure
def makeFigure():
    # Figure
    fig = plt.figure()
    fig.canvas.set_window_title("MST Algorithm Comparison")
    # Add ax
    ax = fig.add_subplot(2,1,1)
    # Remove the ticks and tick labels
    ax.set_xticks([])
    ax.set_yticks([])
    ax.set_title("Prim's Algorithm")
    # Plot nodes for ax
    nodes = nx.draw_networkx_nodes(G, pos, node_size=500)
    # Plot edges for ax
    edges = nx.draw_networkx_edges(G, pos, width=1)
    # Plot labels for ax
    labels = nx.get_edge_attributes(G,'weight') # The labels are shared between axes
    nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
    return fig, ax, edges, nodes

# Add the starting node
visited_nodes.append(history[0][0][0])
# Frame rendering function
def renderFrame(i, edges, nodes):
    if i > 0:
        # Erase the previous figure, for memory optimization
        ax.cla()
        # Remove al ticks and tick labels
        ax.set_xticks([])
        ax.set_yticks([])
        # Title of the plot
        ax.set_title("Prim's Algorithm")
        # Update frame with original nodes
        nodes = nx.draw_networkx_nodes(G, pos, node_size=500)
        # Update frame with original edges
        edges = nx.draw_networkx_edges(G, pos, width=1)
        # Update frame with labels (Labels do not change)
        labels = nx.get_edge_attributes(G,'weight')
        nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
        nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
        if (len(history) > 0):
            # Current Edge
            current = history[0][0]
            # Draw all the neighbors for the current node
            nx.draw_networkx_edges(G, pos,edgelist=history[0][1], width=6, edge_color='b', style='dashed')
            # Draw the lowest value edge
            nx.draw_networkx_edges(G, pos,edgelist=[current,], width=6, edge_color='r', style='dashed')
            # Draw the visited nodes
            nodes = nx.draw_networkx_nodes(G, pos, nodelist=visited_nodes, node_size=500, node_color='yellow')
            # Draw the visited edges (MST)
            edges = nx.draw_networkx_edges(G, pos,edgelist=visited_edges, width=6, edge_color='r', style=':')
            # Add the current edge to visited edges for MST
            visited_edges.append(current)
            # Add the current node to visited nodes for MST
            visited_nodes.append(current[1])
            # Remove the step of mst from history
            del history[0]
        else:
            # Final edges (MST)
            nx.draw_networkx_edges(G, pos, edgelist=mst, width=6, alpha=0.5, edge_color='b', style='dashed')
    return edges, nodes

# # List of Animation objects for tracking
anim = []

# Make the figures
figcomps1=makeFigure()

# Animate the figures
fig, ax, edges, nodes = figcomps1
# Start animation
anim.append(animation.FuncAnimation(fig,renderFrame,fargs=[edges, nodes], frames=len(history)+2, interval=1400))
# Mananges for maximize the window
figManager = plt.get_current_fig_manager()
figManager.window.showMaximized()
# Show figure
plt.show()



