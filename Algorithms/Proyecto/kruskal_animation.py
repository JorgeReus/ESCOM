from matplotlib import pyplot as plt
from matplotlib import animation
import networkx as nx
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

# Make the MST, the History and the cycles(For animation steps)
history, mst, cycles = kruskal(G)

print("MST: ",mst)
# Layout of the Graph
pos = nx.spring_layout(G)
# Lists for keeping track of the nodes and edges
visited_edges_cycles = []
visited_edges = []
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
    ax.set_title("Kruskal's Algorithm")
    # Plot nodes for ax
    nodes = nx.draw_networkx_nodes(G, pos, node_size=500, node_color='y')
    # Plot edges for ax
    edges = nx.draw_networkx_edges(G, pos, width=1)
    # Plot labels for ax
    labels = nx.get_edge_attributes(G,'weight')
    nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
    return fig, ax, edges, nodes


# Frame rendering function
def renderFrame(i, edges, nodes):
    if i > 0:
        # Erase the previous figure, for memory optimization
        ax.cla()
        # Remove al ticks and tick labels
        ax.set_xticks([])
        ax.set_yticks([])
        # Title of the plot
        ax.set_title("Kruskal's Algorithm")
        # Update frame with original nodes
        nodes = nx.draw_networkx_nodes(G, pos, node_size=500, node_color='y')
        # Update frame with original edges
        edges = nx.draw_networkx_edges(G, pos, width=2, edge_color='r')
        nx.draw_networkx_edges(G, pos,edgelist=visited_edges_cycles, width=2, edge_color='y')
        # Update frame with labels (Labels do not change)
        labels = nx.get_edge_attributes(G,'weight')
        nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
        nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
        if (len(history) > 0):
            # Current Edge
            current = history[0]
            if (current not in cycles):
                # Draw the visited edges
                edges = nx.draw_networkx_edges(G, pos,edgelist=visited_edges, width=2, edge_color='b')
                # Add the current edge to visited edges for MST
                visited_edges.append(current)
            else:
                # Draw the visited edges
                edges = nx.draw_networkx_edges(G, pos,edgelist=visited_edges, width=2, edge_color='b')
                # Add the current edge to visited edges for MST
                visited_edges_cycles.append(current)
                # Draw the edges which cause cycles
                nx.draw_networkx_edges(G, pos,edgelist=visited_edges_cycles, width=2, edge_color='y')
            # Remove the step of mst from history
            del history[0]
        else:
            # Draw Final edges (MST)
            nx.draw_networkx_edges(G, pos, edgelist=mst, width=4, edge_color='b')
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



