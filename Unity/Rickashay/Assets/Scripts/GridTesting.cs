using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GridTesting : MonoBehaviour
{
    private GridMap<bool> grid;
    private Pathfinding pathFinding;

    private void Start()
    {
        //grid = new GridMap<bool>(13, 8, 5f, new Vector3(-32.5f, -20), (GridMap<bool> g, int x, int y) => new bool());
        pathFinding = new Pathfinding(14, 7, 6f);

        pathFinding.GetNode(1, 0).SetIsWalkable(!pathFinding.GetNode(1, 0).isWalkable);
        pathFinding.GetNode(1, 1).SetIsWalkable(!pathFinding.GetNode(1, 1).isWalkable);
        pathFinding.GetNode(1, 2).SetIsWalkable(!pathFinding.GetNode(1, 2).isWalkable);
        pathFinding.GetNode(1, 3).SetIsWalkable(!pathFinding.GetNode(1, 3).isWalkable);
        pathFinding.GetNode(1, 4).SetIsWalkable(!pathFinding.GetNode(1, 4).isWalkable);
    }

    private void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            //grid.SetGridObject(GetMouseWorldPosition(), true);
            Vector3 mouseWorldPosition = GetMouseWorldPosition();
            pathFinding.getGrid().GetXY(mouseWorldPosition, out int x, out int y);
            List<PathNode> path = pathFinding.FindPath(0, 0, x, y);
            if (path != null)
            {
                for (int i = 0; i < path.Count - 1; i++)
                {
                    Debug.DrawLine(new Vector3(path[i].x, path[i].y) * 6f + Vector3.one * 3f, new Vector3(path[i + 1].x, path[i + 1].y) * 6f + Vector3.one * 3f, Color.green, 2, false);
                }
            }
            
        }

        if (Input.GetMouseButtonDown(1))
        {
            
        }
    }

    // Get Mouse Position in World with Z = 0f
    public static Vector3 GetMouseWorldPosition()
    {
        Vector3 vec = GetMouseWorldPositionWithZ(Input.mousePosition, Camera.main);
        vec.z = 0f;
        return vec;
    }

    public static Vector3 GetMouseWorldPositionWithZ()
    {
        return GetMouseWorldPositionWithZ(Input.mousePosition, Camera.main);
    }

    public static Vector3 GetMouseWorldPositionWithZ(Camera worldCamera)
    {
        return GetMouseWorldPositionWithZ(Input.mousePosition, worldCamera);
    }

    public static Vector3 GetMouseWorldPositionWithZ(Vector3 screenPosition, Camera worldCamera)
    {
        Vector3 worldPosition = worldCamera.ScreenToWorldPoint(screenPosition);
        return worldPosition;
    }

    public static Vector3 GetDirToMouse(Vector3 fromPosition)
    {
        Vector3 mouseWorldPosition = GetMouseWorldPosition();
        return (mouseWorldPosition - fromPosition).normalized;
    }
}
