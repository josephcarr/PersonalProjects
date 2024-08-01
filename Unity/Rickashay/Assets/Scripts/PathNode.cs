using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Object used for the pathfinding algorithm
/// </summary>
public class PathNode
{
    private GridMap<PathNode> grid;
    public int x;
    public int y;

    public int gCost;
    public int hCost;
    public int fCost;

    public bool isWalkable;
    public PathNode previousNode;

    /// <summary>
    /// Constructor for the pathnode class
    /// </summary>
    /// <param name="grid">The grid used</param>
    /// <param name="x">the x position of the node</param>
    /// <param name="y">the y position of the node</param>
    public PathNode(GridMap<PathNode> grid, int x, int y)
    {
        this.grid = grid;
        this.x = x;
        this.y = y;
        isWalkable = true;
    }

    /// <summary>
    /// Calculates the full cost of the node
    /// </summary>
    public void CalculateFCost()
    {
        fCost = gCost + hCost;
    }

    /// <summary>
    /// Sets if a node is walkable or not
    /// </summary>
    /// <param name="walkable"></param>
    public void SetIsWalkable(bool walkable)
    {
        this.isWalkable = walkable;
        grid.TriggerGridObjectChanged(x, y);
    }

    /// <summary>
    /// 
    /// </summary>
    /// <returns>Returns the position of a pathnode</returns>
    public Vector3 GetPos()
    {
        return new Vector3(x, y);
    }

    //for debugging
    public override string ToString()
    {
        return x + ", " + y;
    }
}
