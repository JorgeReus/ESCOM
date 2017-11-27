from pqdict import PQDict

def prim(G,start):
    """Function recives a graph and a starting node, and returns a MST"""
    MST_len = G.number_of_nodes() - 1
    current = start
    visited = set()
    # Priority Queue (keeps the item with the lowest value on the top - in this case the weight of an edge)
    pq = PQDict()
    # Minimal spamming tree
    mst = []
    steps = []
    history = []
    # While the MST has N - 1 edges (N is the total nodes)
    while len(mst) < MST_len:
        # print (" ")
        # print ("Current node :", current)
        # Get the neighbors
        # history.append((current, G.neighbors(current)))
        for node in G.neighbors(current):
            if node not in visited and current not in visited:
                # print ("    neigbors: ", node)
                # Append the history
                steps.append((current, node))
                if (current,node) not in pq and (node,current) not in pq:
                    w = G.edge[current][node]['weight']
                    pq.additem((current,node), w)
        # We have visited the node
        visited.add(current)
        # Tup is the edge "(X, Y)", wght is the cost
        tup, wght = pq.popitem()
        # Get the lowest edge if we haven't visited the node
        while(tup[1] in visited):
            tup, wght = pq.popitem()
        # Append the edge to the minimum spanning tree
        mst.append(tup)
        history.append([tup, steps])
        steps = list()
        # Update the current node to the one we visit based on the minimal weight
        current = tup[1]

    return mst, history
    # pass