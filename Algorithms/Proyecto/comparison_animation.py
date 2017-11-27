from matplotlib import pyplot as plt
from matplotlib import animation
import networkx as nx
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

# Make the prim_mst and the prim_history(For prim's animation steps)
prim_mst, prim_history = prim2(G,'A')
# Make the kruskal_mst and the kruskal_history(For krukal's animation steps)
kruskal_history, kruskal_mst, kruskal_cycles = kruskal(G)

print("prim_mst: ",prim_mst)
print("prim_history", prim_history)
# Layout of the Graph
pos = nx.spring_layout(G)
# Lists for keeping track of the nodes and edges
visited_edges_prim = []
visited_nodes_prim = []
edges_cycles_kruskal = []
visited_edges_kruskal = []
# Function for making figure
def makeFigure():
    # Figure
    fig = plt.figure()
    fig.canvas.set_window_title("prim_mst Algorithm Comparison")
    # Add ax1 (Prim)
    ax1 = fig.add_subplot(2,1,1)
    # Remove the ticks and tick labels for ax1
    ax1.set_xticks([])
    ax1.set_yticks([])
    ax1.set_title("Prim's Algorithm")
    # Plot nodes for ax1
    nodes = nx.draw_networkx_nodes(G, pos, node_size=500)
    # Plot edges for ax1
    edges = nx.draw_networkx_edges(G, pos, width=1)
    # Plot labels for ax1
    labels = nx.get_edge_attributes(G,'weight') # The labels are shared between axes
    nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)


    # Add ax2
    ax2 = fig.add_subplot(2,1,2)
    # Remove the ticks and tick labels
    ax2.set_xticks([])
    ax2.set_yticks([])
    ax2.set_title("Kruskal's Algorithm")
    # Plot nodes for ax2
    nodes = nx.draw_networkx_nodes(G, pos, node_size=500, node_color='y')
    # Plot edges for ax2
    edges = nx.draw_networkx_edges(G, pos, width=1)
    # Plot labels for ax2
    labels = nx.get_edge_attributes(G,'weight')
    nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
    return fig, ax1, ax2, edges, nodes

# Add the starting node
visited_nodes_prim.append(prim_history[0][0][0])
# Frame rendering function
def renderFrame(i, edges, nodes):
    if i > 0:
        # Erase the previous figure, for memory optimization
        plt.sca(ax1)
        ax1.cla()
        # Remove al ticks and tick labels
        ax1.set_xticks([])
        ax1.set_yticks([])
        # Title of the plot
        ax1.set_title("Prim's Algorithm")
        # Update frame with original nodes
        nodes = nx.draw_networkx_nodes(G, pos, node_size=500)
        # Update frame with original edges
        edges = nx.draw_networkx_edges(G, pos, width=1)
        # Update frame with labels (Labels do not change)
        labels = nx.get_edge_attributes(G,'weight')
        nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
        nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
        if (len(prim_history) > 0):
            # Current Edge
            current = prim_history[0][0]
            # Draw all the neighbors for the current node
            nx.draw_networkx_edges(G, pos,edgelist=prim_history[0][1], width=6, edge_color='b', style='dashed')
            # Draw the lowest value edge
            nx.draw_networkx_edges(G, pos,edgelist=[current,], width=6, edge_color='r', style='dashed')
            # Draw the visited nodes
            nodes = nx.draw_networkx_nodes(G, pos, nodelist=visited_nodes_prim, node_size=500, node_color='yellow')
            # Draw the visited edges (prim_mst)
            edges = nx.draw_networkx_edges(G, pos,edgelist=visited_edges_prim, width=6, edge_color='r', style=':')
            # Add the current edge to visited edges for prim_mst
            visited_edges_prim.append(current)
            # Add the current node to visited nodes for prim_mst
            visited_nodes_prim.append(current[1])
            # Remove the step of prim_mst from prim_history
            del prim_history[0]
        else:
            # Final edges (prim_mst)
            nx.draw_networkx_edges(G, pos, edgelist=prim_mst, width=6, alpha=0.5, edge_color='b', style='dashed')
        plt.sca(ax2)
        # Erase the previous figure, for memory optimization
        ax2.cla()
        # Remove al ticks and tick labels
        ax2.set_xticks([])
        ax2.set_yticks([])
        # Title of the plot
        ax2.set_title("Kruskal's Algorithm")
        # Update frame with original nodes
        nodes = nx.draw_networkx_nodes(G, pos, node_size=500, node_color='y')
        # Update frame with original edges
        edges = nx.draw_networkx_edges(G, pos, width=2, edge_color='r')
        nx.draw_networkx_edges(G, pos,edgelist=edges_cycles_kruskal, width=2, edge_color='y')
        # Update frame with labels (Labels do not change)
        labels = nx.get_edge_attributes(G,'weight')
        nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
        nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
        if (len(kruskal_history) > 0):
            # Current Edge
            current = kruskal_history[0]
            if (current not in edges_cycles_kruskal):
                # Draw the visited edges
                edges = nx.draw_networkx_edges(G, pos,edgelist=visited_edges_kruskal, width=2, edge_color='b')
                # Add the current edge to visited edges for MST
                visited_edges_kruskal.append(current)
            else:
                # Draw the visited edges
                edges = nx.draw_networkx_edges(G, pos,edgelist=visited_edges_kruskal, width=2, edge_color='b')
                # Add the current edge to visited edges for MST
                edges_cycles_kruskal.append(current)
                # Draw the edges which cause cycles
                nx.draw_networkx_edges(G, pos,edgelist=edges_cycles_kruskal, width=2, edge_color='y')
            # Remove the step of mst from kruskal_history
            del kruskal_history[0]
        else:
            # Draw Final edges (MST)
            nx.draw_networkx_edges(G, pos, edgelist=kruskal_mst, width=4, edge_color='b')
    return edges, nodes

# # List of Animation objects for tracking
anim = []

# Make the figures
figcomps1=makeFigure()

# Animate the figures
fig, ax1, ax2, edges, nodes = figcomps1
# Start animation
anim.append(animation.FuncAnimation(fig,renderFrame,fargs=[edges, nodes], frames=len(prim_history)+2, interval=1400))
# Mananges for max1imize the window
figManager = plt.get_current_fig_manager()
figManager.window.showMaximized()
# Show figure
plt.show()



