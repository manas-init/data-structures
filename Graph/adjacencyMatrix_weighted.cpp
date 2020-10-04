#include<bits/stdc++.h>
using namespace std;

typedef pair<int, int> eV;

void addEdge(vector<vector<eV> > &adj, int u, int v, int w)
{
	adj[u].push_back(make_pair(v, w));
	adj[v].push_back(make_pair(u, w));
}

void printGraph(vector<vector<eV> > adj)
{
	for(int i=0;i<adj.size();i++)
	{
		cout<<"Root is "<<i<<endl<<"\tHead";
		for(int j=0;j<adj[i].size();j++)
		{
			cout<<" -> ("<<adj[i][j].first<<", "<<adj[i][j].second<<")";
		}
		cout<<endl;
	}
}

int main()
{
	int V=5;
	vector<vector<eV> > adj(V);
    addEdge(adj, 0, 1, 1); 
    addEdge(adj, 0, 4, 2); 
    addEdge(adj, 1, 2, 3); 
    addEdge(adj, 1, 3, 4); 
    addEdge(adj, 1, 4, 5); 
    addEdge(adj, 2, 3, 6); 
    addEdge(adj, 3, 4, 7);
    printGraph(adj);
	return 0;
}
