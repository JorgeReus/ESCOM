from networkx.utils import UnionFind

def kruskal(G: "networkx Graph object") -> (list, list, list):
    """
    Function that recieves a graph an returns the history of the algorithm, 
    the Mininum Spanning Tree and the edges that causes cycles
    """
    # List for the minimum spanning tree
    mst = []
    # List for the history of the algorithm
    history = []
    # List for the edges tat causes cycles
    cycles = []
    # Disjoint structure for calculating the edges that causes cycles
    subtrees = UnionFind()
    # Sort the edges in ascendant order
    edges = sorted(G.edges(data=True), key=lambda t: t[2].get('weight', 1))
    # For node A, Node B, Weight in the sorted edges
    for u, v, d in edges:
        # If the edge doesn't make a cycle append it to the mst
        if subtrees[u] != subtrees[v]:
            mst.append((u, v))
            subtrees.union(u, v)
        # Else append it to a cycle
        else:
            cycles.append((u, v))
        history.append((u, v))
    return history, mst, cycles

if __name__ == '__main__':
    print(getattr(kruskal, '__annotations__'))