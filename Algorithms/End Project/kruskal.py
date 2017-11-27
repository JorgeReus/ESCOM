from networkx.utils import UnionFind

def kruskal(G):
    result = []
    history = []
    cycles = []
    subtrees = UnionFind()
    edges = sorted(G.edges(data=True), key=lambda t: t[2].get('weight', 1))
    for u, v, d in edges:
        if subtrees[u] != subtrees[v]:
            result.append((u, v))
            subtrees.union(u, v)
        else:
            cycles.append((u, v))
        history.append((u, v))
    return history, result, cycles