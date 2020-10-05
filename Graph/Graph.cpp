#include<bits/stdc++.h>
using namespace std;

class Graph{
	int V;
	list<int> *adj;
	public:
		Graph(int V);
		void addEdge(int u, int v);
		void BFS(int root);
		void BFSUtil(int s, vector<bool> &v);
		void BFS_stronglyConnected(int s);
		void DFS(int s);
		void DFSUtil(int s, vector<bool> &v);
		void DFS_stronglyConnected(int root);
		void DFS_iterative(int root);
		Graph transpose();
		void printGraph();
};

Graph::Graph(int V){
	this->V=V;
	this->adj=new list<int>[V];
}

void Graph::addEdge(int u, int v)
{
	adj[u].push_back(v);
}

//this one works only when all nodes are reachable from first node
void Graph::BFS_stronglyConnected(int s)
{
	vector<bool> visited(V, false);
	list<int> q;
	
	visited[s]=true;
	q.push_back(s);
	while(!q.empty())
	{
		s=q.front();
		cout<<s<<" ";
		q.pop_front();
		list<int>::iterator i;
		for(i=adj[s].begin();i!=adj[s].end();i++)
		{
			if(!visited[*i])
			{
				visited[*i]=true;
				q.push_back(*i);
			}
		}
		
	}
	cout<<endl;
	return;
}

void Graph::BFS(int s)
{
	vector<bool> visited(V, false);
	BFSUtil(s, visited);
	//return;
	for(int i=0;i<V;i++)
		if(visited[i]!=true)
		{
			BFSUtil(i, visited);
			cout<<endl;
		}
}

void Graph::BFSUtil(int s, vector<bool> &visited)
{
	if(visited[s]==true)
		return;
	list<int> q;
	visited[s]=true;
	q.push_back(s);
	list<int>::iterator it;
	while(!q.empty())
	{
		s=q.front();
		cout<<s<<" ";
		q.pop_front();
		for(it=adj[s].begin();it!=adj[s].end();it++)
		{
			if(!visited[*it])
			{
				visited[*it]=true;
				q.push_back(*it);
			}
		}
	}
}

void Graph::DFS_stronglyConnected(int root)
{
	vector<bool> visited(V, false);
	DFSUtil(root, visited);
	cout<<endl;
}

void Graph::DFSUtil(int root, vector<bool> &visited)
{
	if(visited[root])
		return;
	visited[root]=true;
	cout<<root<<" ";
	for(list<int>::iterator it=adj[root].begin();it!=adj[root].end();it++)
	{
		if(!visited[*it])
		{
			DFSUtil(*it, visited);
		}
	}
}

void Graph::DFS(int root)
{
	vector<bool> visited(V, false);
	DFSUtil(root, visited);
	cout<<endl;
	for(int i=0;i<V;i++)
	{
		if(!visited[i])
		{
			DFSUtil(i, visited);
			cout<<endl;
		}
	}	
}

void Graph::DFS_iterative(int root)
{
	vector<bool> visited(V, false);
	stack<int> st;
	st.push(root);
	visited[root]=true;
	while(!st.empty())
	{
		root=st.top();
		st.pop();
		cout<<root<<" ";
		for(list<int>::reverse_iterator it=adj[root].rbegin();it!=adj[root].rend();it++)
		{
			if(!visited[*it])
			{
				st.push(*it);
				visited[*it]=true;
			}
		}
	}
	cout<<endl;
}

Graph Graph::transpose()
{
	Graph g(V);
	for(int i=0;i<V;i++)
	{
		for(list<int>::iterator it=adj[i].begin();it!=adj[i].end();it++)
		{
			g.addEdge(*it, i);
		}
	}
	return g;
}

void Graph::printGraph()
{
	for(int i=0;i<V;i++)
	{
		cout<<i<<" -> ";
		for(list<int>::iterator it=adj[i].begin();it!=adj[i].end();it++)
		{
			cout<<*it<<" ";
		}
		cout<<endl;
	}
}

int main()
{
	Graph g(6); 
    g.addEdge(0, 1); 
    g.addEdge(0, 2); 
    g.addEdge(1, 2); 
    g.addEdge(2, 0); 
    g.addEdge(2, 3);
    g.addEdge(3, 3); 
    g.addEdge(4, 5);
	//g.BFS(2);
    //g.BFS_stronglyConnected(2);
    g.DFS_stronglyConnected(2);
    g.DFS_iterative(2);
    g.printGraph();
    g=g.transpose();
    g.printGraph();
	return 0;
}
