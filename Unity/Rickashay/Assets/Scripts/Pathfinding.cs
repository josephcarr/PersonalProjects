using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Class to calculate path finding using the GridMap class
/// </summary>
public class Pathfinding
{
    private const int STRAIGHT_COST = 6;
    private const int DIAGONAL_COST = 8;

    public static Pathfinding Instance { get; private set; }

    private GridMap<PathNode> grid;
    private List<PathNode> openList;
    private List<PathNode> visitedList;

    /// <summary>
    /// Constructor for the Pathfinding class
    /// </summary>
    /// <param name="width">Sets the grid width</param>
    /// <param name="height">Sets the grid height</param>
    public Pathfinding(int width, int height, float cellsize)
    {
        grid = new GridMap<PathNode>(width, height, cellsize, Vector3.zero, (GridMap<PathNode> g, int x, int y) => new PathNode(g, x, y));
    }

    /// <summary>
    /// Creates an instance of the pathfinding grid
    /// </summary>
    /// <returns></returns>
    public GridMap<PathNode> getGrid()
    {
        Instance = this;
        return grid;
    }

    /// <summary>
    /// Finds the vector path for the AI
    /// </summary>
    /// <param name="startWorldPosition"></param>
    /// <param name="endWorldPosition"></param>
    /// <returns></returns>
    public List<Vector3> FindVectorPath(Vector3 startWorldPosition, Vector3 endWorldPosition)
    {
        grid.GetXY(startWorldPosition, out int startX, out int startY);
        grid.GetXY(endWorldPosition, out int endX, out int endY);

        List<PathNode> path = FindPath(startX, startY, endX, endY);

        if (path == null)
        {
            return null;
        }
        else
        {
            List<Vector3> vectorPath = new List<Vector3>();
            foreach (PathNode pathNode in path)
            {
                vectorPath.Add(new Vector3(pathNode.x, pathNode.y) * grid.GetCellSize() + Vector3.one * grid.GetCellSize() * .5f);
            }

            return vectorPath;
        }
    }

    /// <summary>
    /// Finds the path on the grid using the path nodes
    /// </summary>
    /// <param name="startX"></param>
    /// <param name="startY"></param>
    /// <param name="endX"></param>
    /// <param name="endY"></param>
    /// <returns></returns>
    public List<PathNode> FindPath(int startX, int startY, int endX, int endY)
    {
        PathNode startNode = grid.GetGridObject(startX, startY);
        PathNode endNode = grid.GetGridObject(endX, endY);

        openList = new List<PathNode> { startNode };
        visitedList = new List<PathNode>();

        for (int x = 0; x < grid.GetWidth(); x++)
        {
            for (int y = 0; y < grid.GetHeight(); y++)
            {
                PathNode pathNode = grid.GetGridObject(x, y);
                pathNode.gCost = int.MaxValue;
                pathNode.CalculateFCost();
                pathNode.previousNode = null;
            }
        }

        startNode.gCost = 0;
        startNode.hCost = CalculateDistanceCost(startNode, endNode);
        startNode.CalculateFCost();

        while (openList.Count > 0)
        {
            PathNode currentNode = GetLowestFCostNode(openList);
            if (currentNode == endNode)
            {
                //reached destination
                return CalculatePath(endNode);
            }

            openList.Remove(currentNode);
            visitedList.Add(currentNode);

            foreach (PathNode neighborNode in Neighbors(currentNode))
            {
                if (visitedList.Contains(neighborNode))
                {
                    continue;
                }

                if (!neighborNode.isWalkable)
                {
                    visitedList.Add(neighborNode);
                    continue;
                }

                int tentativeCost = currentNode.gCost + CalculateDistanceCost(currentNode, neighborNode);
                if (tentativeCost < neighborNode.gCost)
                {
                    neighborNode.previousNode = currentNode;
                    neighborNode.gCost = tentativeCost;
                    neighborNode.hCost = CalculateDistanceCost(neighborNode, endNode);
                    neighborNode.CalculateFCost();

                    if (!openList.Contains(neighborNode))
                    {
                        openList.Add(neighborNode);
                    }
                }
            }
        }

        //out of nodes on the open list
        return null;
    }

    /// <summary>
    /// Finds the neighbors of a grid path node
    /// </summary>
    /// <param name="currentNode"></param>
    /// <returns></returns>
    private List<PathNode> Neighbors(PathNode currentNode)
    {
        List<PathNode> neighborList = new List<PathNode>();

        if (currentNode.x - 1 >= 0)
        {
            //Left
            neighborList.Add(GetNode(currentNode.x - 1, currentNode.y));

            //Left Down
            /*if (currentNode.y - 1 >= 0)
            {
                neighborList.Add(GetNode(currentNode.x - 1, currentNode.y - 1));
            }

            //Left Up
            if (currentNode.y + 1 < grid.GetHeight())
            {
                neighborList.Add(GetNode(currentNode.x - 1, currentNode.y + 1));
            }*/

        }
        if (currentNode.x + 1 < grid.GetWidth())
        {
            //Right
            neighborList.Add(GetNode(currentNode.x + 1, currentNode.y));

            //Right Down
            /*if (currentNode.y - 1 >= 0)
            {
                neighborList.Add(GetNode(currentNode.x + 1, currentNode.y - 1));
            }

            //Right Up
            if (currentNode.y + 1 < grid.GetHeight())
            {
                neighborList.Add(GetNode(currentNode.x + 1, currentNode.y + 1));
            }*/
        }

        //Down
        if (currentNode.y - 1 >= 0)
        {
            neighborList.Add(GetNode(currentNode.x, currentNode.y - 1));
        }

        //Up
        if (currentNode.y + 1 < grid.GetHeight())
        {
            neighborList.Add(GetNode(currentNode.x, currentNode.y + 1));
        }

        return neighborList;
    }

    /// <summary>
    /// Calculates the path to follow
    /// </summary>
    /// <param name="endNode"></param>
    /// <returns></returns>
    private List<PathNode> CalculatePath(PathNode endNode)
    {
        List<PathNode> path = new List<PathNode>();
        path.Add(endNode);
        PathNode currentNode = endNode;
        while (currentNode.previousNode != null)
        {
            path.Add(currentNode.previousNode);
            currentNode = currentNode.previousNode;
        }
        path.Reverse();
        return path;
    }

    /// <summary>
    /// Calculates the cost for the distance from the start and end position
    /// </summary>
    /// <param name="start"></param>
    /// <param name="end"></param>
    /// <returns></returns>
    private int CalculateDistanceCost(PathNode start, PathNode end)
    {
        int xDistance = Mathf.Abs(start.x - end.x);
        int yDistance = Mathf.Abs(start.y - end.y);
        int remaining = Mathf.Abs(xDistance - yDistance);

        return DIAGONAL_COST * Mathf.Min(xDistance, yDistance) + STRAIGHT_COST * remaining;
    }

    /// <summary>
    /// Finds the lowest cost for the next neighbor in the path
    /// </summary>
    /// <param name="pathNodeList"></param>
    /// <returns></returns>
    private PathNode GetLowestFCostNode(List<PathNode> pathNodeList)
    {
        PathNode lowestFCostNode = pathNodeList[0];
        for (int i = 1; i < pathNodeList.Count; i++)
        {
            if (pathNodeList[i].fCost < lowestFCostNode.fCost)
            {
                lowestFCostNode = pathNodeList[i];
            }
        }

        return lowestFCostNode;
    }

    /// <summary>
    /// Gets the path node at a certain position
    /// </summary>
    /// <param name="x"></param>
    /// <param name="y"></param>
    /// <returns></returns>
    public PathNode GetNode(int x, int y)
    {
        PathNode node = grid.GetGridObject(x, y);
        return node;
    }
}
