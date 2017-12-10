from matplotlib import pyplot as plt
from matplotlib import animation, rcParams
import networkx as nx
from prim import *
from kruskal import *

rcParams['toolbar'] = 'None'
# Create the graph from file
G=nx.read_edgelist("graph.edgelist")

# Make the prim_mst and the prim_history(For prim's animation steps)
prim_mst, prim_history = prim(G,'A')
# Make the kruskal_mst and the kruskal_history(For krukal's animation steps)
kruskal_history, kruskal_mst, kruskal_cycles = kruskal(G)

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
    fig.canvas.set_window_title("MST algorithm comparison")
    # Add ax1 (Prim)
    ax1 = fig.add_subplot(2,2,1)
    ax1_2 = fig.add_subplot(2,2,2)
    plt.sca(ax1)
    # Remove the ticks and tick labels for ax1
    ax1_2.set_xticks([])
    ax1_2.set_yticks([])
    ax1_2.set_title("Prim's Algorithm")
    # Remove the ticks and tick labels for ax1-2
    ax1.set_xticks([])
    ax1.set_yticks([])
    ax1.set_title("Prim's Animation")
    # Plot nodes for ax1
    nodes = nx.draw_networkx_nodes(G, pos, node_size=300)
    # Plot edges for ax1
    edges = nx.draw_networkx_edges(G, pos, width=1)
    # Plot labels for ax1
    labels = nx.get_edge_attributes(G,'weight')
     # The labels are shared between axes
    nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
    # Text for prim algorithm
    plt.sca(ax1_2)
    ax1_2.text(.1, .6, 
        'While the MST has N - 1 edges (N is the total nodes):', 
        fontsize=15)
    ax1_2.text(.1, .5, 
        '      Get the neighbor edge with the lowest weight', 
        fontsize=15)
    ax1_2.text(.1, .4, 
        '      if the neighbour is not in the MST: add it', 
        fontsize=15)
    ax1_2.text(.1, .3, 
        '      else: discard it', 
        fontsize=15)
    ax1_2.text(.1, .2, 
        '      update the current node with the added one', 
        fontsize=15)
    plt.sca(ax1)


    # Add ax2 (Kruskal)
    ax2 = fig.add_subplot(2,2,3)
    ax2.set_title("Kruskal's Animation")
    ax2_2 = fig.add_subplot(2,2,4)
    plt.sca(ax2)
    # Remove the ticks and tick labels
    ax2_2.set_xticks([])
    ax2_2.set_yticks([])
    ax2_2.set_title("Kruskal's Algorithm")
    ax2.set_xticks([])
    ax2.set_yticks([])
    ax2.set_title("Kruskal's Algorithm")
    # Plot nodes for ax2
    nodes = nx.draw_networkx_nodes(G, pos, node_size=300, node_color='y')
    # Plot edges for ax2
    edges = nx.draw_networkx_edges(G, pos, width=1)
    # Plot labels for ax2
    labels = nx.get_edge_attributes(G,'weight')
    nx.draw_networkx_labels(G,pos,font_size=10,font_family='sans-serif')
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
    plt.sca(ax2_2)
    ax2_2.text(.1, .6, 'Sort all the edges in ascendant order', 
        fontsize=15, color="green")
    ax2_2.text(.1, .5, 'For edge in the sorted edges', 
        fontsize=15)
    ax2_2.text(.1, .4, 
        '      If the edge does not make a cycle append it to the mst', 
        fontsize=15)
    ax2_2.text(.1, .3, '      else: discard it', fontsize=15)
    plt.sca(ax2)
    return fig, ax1, ax1_2, ax2, ax2_2, edges, nodes

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
        ax1.set_title("Prim's Animation")
        ax1_2.set_title("Prim's Algorithm")
        # Update frame with original nodes
        nodes = nx.draw_networkx_nodes(G, pos, node_size=300)
        # Update frame with original edges
        edges = nx.draw_networkx_edges(G, pos, width=1)
        # Update frame with labels (Labels do not change)
        labels = nx.get_edge_attributes(G,'weight')
        nx.draw_networkx_labels(G,pos,font_size=15,font_family='sans-serif')
        nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
        if (len(prim_history) > 0):
            # Text for prim algorithm
            plt.sca(ax1_2)
            ax1_2.text(.1, .6, 
                'While the MST has N - 1 edges (N is the total nodes):', 
                fontsize=15)
            ax1_2.text(.1, .5, 
                '      Get the neighbor edge with the lowest weight', 
                fontsize=15, color="green")
            ax1_2.text(.1, .4, 
                '      if the neighbour is not in the MST: add it', 
                fontsize=15, color="green")
            ax1_2.text(.1, .3, 
                '      else: discard it', 
                fontsize=15, color="green")
            ax1_2.text(.1, .2, 
                '      update the current node with the added one', 
                fontsize=15, color="green")
            plt.sca(ax1)
            # Current Edge
            current = prim_history[0][0]
            # Draw all the neighbors for the current node
            nx.draw_networkx_edges(G, pos, 
                edgelist=prim_history[0][1], width=6, 
                edge_color='b', style='dashed')
            # Draw the lowest value edge
            nx.draw_networkx_edges(G, 
                pos,edgelist=[current,], width=6, 
                edge_color='r', style='dashed')
            # Draw the visited nodes
            nodes = nx.draw_networkx_nodes(G, pos, 
                nodelist=visited_nodes_prim, node_size=300, 
                node_color='yellow')
            # Draw the visited edges (prim_mst)
            edges = nx.draw_networkx_edges(G, pos, 
                edgelist=visited_edges_prim, width=6, 
                edge_color='r', style=':')
            # Add the current edge to visited edges for prim_mst
            visited_edges_prim.append(current)
            # Add the current node to visited nodes for prim_mst
            visited_nodes_prim.append(current[1])
            # Remove the step of prim_mst from prim_history
            del prim_history[0]
        else:
            # Final edges (prim_mst)
            nx.draw_networkx_edges(G, pos, edgelist=prim_mst, 
                width=6, alpha=0.5, edge_color='b', style='dashed')
            plt.sca(ax1_2)
            ax1_2.text(.1, .6, 
                'While the MST has N - 1 edges (N is the total nodes):', 
                fontsize=15, color="blue")
            ax1_2.text(.1, .5, 
                '      Get the neighbor edge with the lowest weight', 
                fontsize=15, color="blue")
            ax1_2.text(.1, .4, 
                '      if the neighbour is not in the MST: add it', 
                fontsize=15, color="blue")
            ax1_2.text(.1, .3, 
                '      else: discard it', fontsize=15, 
                color="blue")
            ax1_2.text(.1, .2, 
                '      update the current node with the added one', 
                fontsize=15, color="blue")
            plt.sca(ax1)

        plt.sca(ax2)
        # Erase the previous figure, for memory optimization
        ax2.cla()
        # Remove al ticks and tick labels
        ax2.set_xticks([])
        ax2.set_yticks([])
        # Title of the plot
        ax2.set_title("Kruskal's Animation")
        ax2_2.set_title("Kruskal's Algorithm")
        # Update frame with original nodes
        nodes = nx.draw_networkx_nodes(G, pos, node_size=300, 
            node_color='y')
        # Update frame with original edges
        edges = nx.draw_networkx_edges(G, pos, width=2, 
            edge_color='r', style='dashed')
        nx.draw_networkx_edges(G, pos,edgelist=edges_cycles_kruskal, 
            width=2, edge_color='y')
        # Update frame with labels (Labels do not change)
        labels = nx.get_edge_attributes(G,'weight')
        nx.draw_networkx_labels(G,pos,font_size=10,
            font_family='sans-serif')
        nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
        plt.sca(ax2_2)
        ax2_2.text(.1, .6, 
            'Sort all the edges in ascendant order', fontsize=15)
        ax2_2.text(.1, .5, 
            'For edge in the sorted edges', 
            fontsize=15, color="green")
        ax2_2.text(.1, .4, 
            '      If the edge does not make a cycle append it to the mst',
             fontsize=15, color="green")
        ax2_2.text(.1, .3, '      else: discard it', 
            fontsize=15, color="green")
        plt.sca(ax2)
        if (len(kruskal_history) > 0):
            # Current Edge
            current = kruskal_history[0]
            if (current not in kruskal_cycles):
                # Draw the visited edges
                edges = nx.draw_networkx_edges(G, 
                    pos,edgelist=visited_edges_kruskal,
                     width=3, edge_color='b', style='dashed')
                edges = nx.draw_networkx_edges(G, pos, 
                    edgelist=edges_cycles_kruskal, width=2, 
                    edge_color='y')
                # Add the current edge to visited edges for MST
                visited_edges_kruskal.append(current)
            else:
                # Draw the visited edges
                edges = nx.draw_networkx_edges(G, pos, 
                    edgelist=visited_edges_kruskal, 
                    width=3, edge_color='b', style='dashed')
                # Draw the edges which cause cycles
                nx.draw_networkx_edges(G, pos, 
                    edgelist=edges_cycles_kruskal, 
                    width=2, edge_color='y')
                # Add the current edge to visited edges for MST
                edges_cycles_kruskal.append(current)
            # Remove the step of mst from kruskal_history
            del kruskal_history[0]
        else:
            # Draw Final edges (MST)
            nx.draw_networkx_edges(G, pos, 
                edgelist=kruskal_mst, width=4, edge_color='r')
            plt.sca(ax2_2)
            ax2_2.text(.1, .6, 'Sort all the edges in ascendant order', 
                fontsize=15, color="blue")
            ax2_2.text(.1, .5, 'For edge in the sorted edges', 
                fontsize=15, color="blue")
            ax2_2.text(.1, .4, 
                '      If the edge does not make a cycle append it to the mst', 
                fontsize=15, color="blue")
            ax2_2.text(.1, .3, '      else: discard it', 
                fontsize=15, color="blue")
            plt.sca(ax2)
    return edges, nodes

# List of Animation objects for tracking
anim = []

# Make the figures
figcomps1=makeFigure()

# Animate the figures
fig, ax1, ax1_2, ax2, ax2_2, edges, nodes = figcomps1
# Start animation
anim.append(animation.FuncAnimation(fig,renderFrame,
    fargs=[edges, nodes], frames=len(prim_history)+2, 
    interval=1400))
# Mananges for max1imize the window
figManager = plt.get_current_fig_manager()
figManager.window.showMaximized()
# Show figure
plt.show()



