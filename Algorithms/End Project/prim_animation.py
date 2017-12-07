from matplotlib import pyplot as plt
from matplotlib import animation
from matplotlib import rcParams
import networkx as nx
from prim import *
rcParams['toolbar'] = 'None'

G=nx.read_edgelist("graph.edgelist")

# Make the MST and the History(For animation steps)
mst, history = prim(G,'A')

# print("MST: ",mst)
# print("HIstory", history)
# Layout of the Graph
pos = nx.spring_layout(G)
# Lists for keeping track of the nodes and edges
visited_edges = []
visited_nodes = []

# Function for making figure
def makeFigure():
    # Figure
    fig = plt.figure()
    fig.canvas.set_window_title("Prim Algorithm Animation")
    # Add ax1
    ax1 = fig.add_subplot(2,2,1)
    ax2 = fig.add_subplot(2,2,2)
    plt.sca(ax1)
    ax1.set_xticks([])
    ax1.set_yticks([])
    ax1.set_title("Prim's Animation")
    ax2.set_title("Prim's Algorithm")
    ax2.set_xticks([])
    ax2.set_yticks([])
    plt.sca(ax2)
    ax2.text(.1, .6, 'While the MST has N - 1 edges (N is the total nodes):', fontsize=15)
    ax2.text(.1, .5, '      Get the neighbor edge with the lowest weight', fontsize=15)
    ax2.text(.1, .4, '      if the neighbour is not in the MST: add it', fontsize=15)
    ax2.text(.1, .3, '      else: discard it', fontsize=15)
    ax2.text(.1, .2, '      update the current node with the added one', fontsize=15)
    plt.sca(ax1)
    # Remove the ticks and tick labels
    # Plot nodes for ax1
    nodes = nx.draw_networkx_nodes(G, pos, node_size=500)
    # Plot edges for ax1
    edges = nx.draw_networkx_edges(G, pos, width=1)
    # Plot labels for ax1
    labels = nx.get_edge_attributes(G,'weight') # The labels are shared between axes
    nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
    return fig, ax1, ax2, edges, nodes

# Add the starting node
visited_nodes.append(history[0][0][0])
# Frame rendering function
def renderFrame(i, edges, nodes):
    if i > 0:
        # Erase the previous figure, for memory optimization
        ax1.cla()
        # Remove al ticks and tick labels
        ax1.set_xticks([])
        ax1.set_yticks([])
        # Title of the plot
        ax1.set_title("Prim's Animation")
        ax2.set_title("Prim's Algorithm")
        # Update frame with original nodes
        nodes = nx.draw_networkx_nodes(G, pos, node_size=500)
        # Update frame with original edges
        edges = nx.draw_networkx_edges(G, pos, width=1)
        # Update frame with labels (Labels do not change)
        labels = nx.get_edge_attributes(G,'weight')
        nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
        nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
        if (len(history) > 0):
            # Draw the text
            plt.sca(ax2)
            ax2.text(.1, .6, 'While the MST has N - 1 edges (N is the total nodes):', fontsize=15)
            ax2.text(.1, .5, '      Get the neighbor edge with the lowest weight', fontsize=15, color="green")
            ax2.text(.1, .4, '      if the neighbour is not in the MST: add it', fontsize=15, color="green")
            ax2.text(.1, .3, '      else: discard it', fontsize=15, color="green")
            ax2.text(.1, .2, '      update the current node with the added one', fontsize=15, color="green")
            plt.sca(ax1)
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
            plt.sca(ax2)
            ax2.text(.1, .6, 'While the MST has N - 1 edges (N is the total nodes):', fontsize=15, color="blue")
            ax2.text(.1, .5, '      Get the neighbor edge with the lowest weight', fontsize=15, color="blue")
            ax2.text(.1, .4, '      if the neighbour is not in the MST: add it', fontsize=15, color="blue")
            ax2.text(.1, .3, '      else: discard it', fontsize=15, color="blue")
            ax2.text(.1, .2, '      update the current node with the added one', fontsize=15, color="blue")
            plt.sca(ax1)
    return edges, nodes

# # List of Animation objects for tracking
anim = []

# Make the figures
figcomps1=makeFigure()

# Animate the figures
fig, ax1, ax2, edges, nodes = figcomps1
# Start animation
anim.append(animation.FuncAnimation(fig,renderFrame,fargs=[edges, nodes], frames=len(history)+2, interval=1400))
# Mananges for maximize the window
figManager = plt.get_current_fig_manager()
figManager.window.showMaximized()
# Show figure
plt.show()