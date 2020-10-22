#include<bits/stdc++.h>
using namespace std;

class Graph{
	int V;
	list<int> *adj;
	public:
		Graph(int V);
		void addEdge(int u, int v);
		void printGraph();
		void topoSortDFS();
		void topoSortDFSUtil(int curr, vector<bool> &visited, stack<int> &stk);
		void topoSortKahn();
		void topoSortAll();
		void topoSortAllUtil(vector<int> res, vector<bool> &v, vector<int> in_degree);
};

Graph::Graph(int V)
{
	this->V=V;
	this->adj=new list<int>[V];
}

void Graph::addEdge(int u, int v)
{
	adj[u].push_back(v);
	return;
}

void Graph::printGraph()
{
	for(int i=0;i<V;i++)
	{
		cout<<i<<" -> ";
		for(list<int>::iterator it=adj[i].begin();it!=adj[i].end();it++)
			cout<<*it<<" ";
		cout<<endl;
	}
}

void Graph::topoSortDFS()
{
	vector<bool> visited(V, false);
	stack<int> stk;
	//Recursively call util function for all vertices to hold the topological order
	for(int i=0;i<V;i++)
		if(visited[i]==false)
			topoSortDFSUtil(i, visited, stk);

	cout<<"Topological sorted order : ";
	while(!stk.empty())
	{
		cout<<stk.top()<<" ";
		stk.pop();
	}
	cout<<endl;
}

void Graph::topoSortDFSUtil(int curr, vector<bool> &visited, stack<int> &stk)
{
	visited[curr]=true;
	for(list<int>::iterator it=adj[curr].begin();it!=adj[curr].end();it++)
	{
		if(!visited[*it])
			topoSortDFSUtil(*it, visited, stk);
	}
	//Push curr vertex to stack as stack holds the topological ordering
	stk.push(curr);
}

void Graph::topoSortKahn()
{
	vector<int> in_degree(V, 0);
	//O(V+E)
	for(int i=0;i<V;i++)
		for(list<int>::iterator it=adj[i].begin();it!=adj[i].end();it++)
			in_degree[*it]++;
	
	//A queue to enqueue all vertices of in degree 0
	queue<int> q;
	for(int i=0;i<V;i++)
		if(in_degree[i]==0)
			q.push(i);
	
	//Initialize count of all visited vertices
	int visited_count=0;
	//Vector to hold topologically sorted order of vertices
	vector<int> top_order;
	
	while(!q.empty())
	{
		int curr=q.front();
		q.pop();
		top_order.push_back(curr);
		for(list<int>::iterator it=adj[curr].begin();it!=adj[curr].end();it++)
		{
			in_degree[*it]--;
			if(in_degree[*it]==0)
				q.push(*it);
		}
		visited_count++;
	}
	//Check if there is a cycle
	if(visited_count!=V)
	{
		cout<<"Given graph contains cycle\nTopological sorting is not possible for this\n";
		return;
	}
	cout<<"Topological sorted order : ";
	for(int i=0;i<V;i++)
		cout<<top_order[i]<<" ";
	cout<<endl;
}

void Graph::topoSortAll()
{
	vector<bool> visited(V, false);
	vector<int> in_degree(V, 0);
	for(int i=0;i<V;i++)
		for(list<int>::iterator it=adj[i].begin();it!=adj[i].end();it++)
			in_degree[*it]++;
	vector<int> sort;
	topoSortAllUtil(sort, visited, in_degree);
}

void Graph::topoSortAllUtil(vector<int> sort, vector<bool> &visited, vector<int> in_degree)
{
	//Flag to inidicate whether all topological orders have been found or not
	bool flag=false;
	for(int i=0;i<V;i++)
	{
		if(visited[i]==false && in_degree[i]==0)
		{
			visited[i]=true;
			sort.push_back(i);
			for(list<int>::iterator it=adj[i].begin();it!=adj[i].end();it++)
				in_degree[*it]--;
			topoSortAllUtil(sort, visited, in_degree);
			//Resetting in_degree, sort vector and visited for backtracking
			for(list<int>::iterator it=adj[i].begin();it!=adj[i].end();it++)
				in_degree[*it]++;
			sort.erase(sort.end()-1);
			visited[i]=false;
			flag=true;
		}
	}
	//Flag=False means all the vertices are visited
	if(flag!=true)
	{
		for(unsigned i=0;i<sort.size();i++)
			cout<<sort[i]<<" ";
		cout<<endl;
	}
}

int main()
{
	Graph g(6);
	g.addEdge(0, 1); 
    g.addEdge(0, 2); 
    g.addEdge(1, 2); 
    //g.addEdge(2, 0); 
    g.addEdge(2, 3);
    //g.addEdge(3, 3); 
    g.addEdge(4, 5);
    g.printGraph();
    g.topoSortDFS();
    g.topoSortKahn();
    g.topoSortAll();
    
/*     g.addEdge(5, 2); 
    g.addEdge(5, 0); 
    g.addEdge(4, 0); 
    g.addEdge(4, 1); 
    g.addEdge(2, 3); 
    g.addEdge(3, 1); 
    g.topoSortAll();*/
}
