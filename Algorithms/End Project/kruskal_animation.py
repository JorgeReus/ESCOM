from matplotlib import pyplot as plt
from matplotlib import animation
import networkx as nx
from kruskal import *

G=nx.read_edgelist("graph.edgelist")

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
    # Add ax1
    ax1 = fig.add_subplot(2,2,1)
    # Add ax1
    ax2 = fig.add_subplot(2,2,2)
    plt.sca(ax1)
    # Remove the ticks and tick labels
    ax1.set_xticks([])
    ax1.set_yticks([])
    ax1.set_title("Kruskal's Algorithm")
    # Plot nodes for ax1
    nodes = nx.draw_networkx_nodes(G, pos, node_size=500, node_color='y')
    # Plot edges for ax1
    edges = nx.draw_networkx_edges(G, pos, width=1)
    # Plot labels for ax1
    labels = nx.get_edge_attributes(G,'weight')
    nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
    # Draw text for the algorithm
    plt.sca(ax2)
    ax2.text(.1, .6, 'Sort all the edges in ascendant order', fontsize=15, color="green")
    ax2.text(.1, .5, 'For edge in the sorted edges', fontsize=15)
    ax2.text(.1, .4, '      If the edge does not make a cycle append it to the mst', fontsize=15)
    ax2.text(.1, .3, '      else: discard it', fontsize=15)
    plt.sca(ax1)
    return fig, ax1, ax2, edges, nodes


# Frame rendering function
def renderFrame(i, edges, nodes):
    if i > 0:
        # Erase the previous figure, for memory optimization
        ax1.cla()
        # Remove al ticks and tick labels
        ax1.set_xticks([])
        ax1.set_yticks([])
        # Title of the plot
        ax1.set_title("Kruskal's Algorithm")
        # Update frame with original nodes
        nodes = nx.draw_networkx_nodes(G, pos, node_size=500, node_color='y')
        # Update frame with original edges
        edges = nx.draw_networkx_edges(G, pos, width=2, edge_color='r')
        nx.draw_networkx_edges(G, pos,edgelist=visited_edges_cycles, width=2, edge_color='y')
        # Update frame with labels (Labels do not change)
        labels = nx.get_edge_attributes(G,'weight')
        nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
        nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
        plt.sca(ax2)
        ax2.text(.1, .6, 'Sort all the edges in ascendant order', fontsize=15)
        ax2.text(.1, .5, 'For edge in the sorted edges', fontsize=15, color="green")
        ax2.text(.1, .4, '      If the edge does not make a cycle append it to the mst', fontsize=15, color="green")
        ax2.text(.1, .3, '      else: discard it', fontsize=15, color="green")
        plt.sca(ax1)
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
            plt.sca(ax2)
            ax2.text(.1, .6, 'Sort all the edges in ascendant order', fontsize=15, color="blue")
            ax2.text(.1, .5, 'For edge in the sorted edges', fontsize=15, color="blue")
            ax2.text(.1, .4, '      If the edge does not make a cycle append it to the mst', fontsize=15, color="blue")
            ax2.text(.1, .3, '      else: discard it', fontsize=15, color="blue")
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



