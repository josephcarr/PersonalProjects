using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Responsible for creating and generating the level randomly
/// </summary>
public class LevelCreator : MonoBehaviour
{
    private Pathfinding pathFinding;

    public GameObject playerTankPrefab;
    private GameObject player;
    
    public GameObject enemyTankPrefab;
    private GameObject enemy;

    
    public List<GameObject> tilePrefabs;
    private List<GameObject> walls;
    private List<GameObject> floors;

    private Vector3 nodePos;
    int levelWidth;
    int levelHeight;
    int[,] levelGrid;

    // Start is called before the first frame update
    private void Awake()
    {
        levelWidth = 14;
        levelHeight = 7;
        pathFinding = new Pathfinding(levelWidth, levelHeight, 6f);
        walls = new List<GameObject>();
        floors = new List<GameObject>();

        levelGrid = new int[levelWidth, levelHeight];

        //sets the borders of the level
        for (int i = 0; i < 14; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (i == 0 || i == 13)
                {
                    levelGrid[i, j] = 1;
                }
                if (j == 0 || j == 6)
                {
                    levelGrid[i, j] = 1;
                }
            }
        }

        levelGridCreate();

        SetPathWalls();

        GenerateLevel();
    }

    private void Update()
    {
        bool debugging = false;
        //Debigging
        if (Input.GetMouseButtonDown(0) && debugging == true)
        {
            Vector3 mouseWorldPosition = GetMouseWorldPosition();
            pathFinding.getGrid().GetXY(mouseWorldPosition, out int x, out int y);
            List<PathNode> path = pathFinding.FindPath(1, 1, x, y);
            if (path != null)
            {
                for (int i = 0; i < path.Count - 1; i++)
                {
                    Debug.DrawLine(new Vector3(path[i].x, path[i].y) * 6f + Vector3.one * 3f, new Vector3(path[i + 1].x, path[i + 1].y) * 6f + Vector3.one * 3f, Color.green, 2, false);
                }
            }

        }
    }

    /// <summary>
    /// Sets which parts of the grid is walkable for pathfinding
    /// </summary>
    private void SetPathWalls()
    {
        for (int i = 0; i < 14; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                fixEnclosed(i, j);

                if (levelGrid[i, j] == 1)
                {
                    pathFinding.GetNode(i, j).SetIsWalkable(false);

                    if (i > 0 && i < 13 && j > 0 && j < 6)
                    {
                        if (levelGrid[i - 1, j - 1] == 1 && i - 1 != 0 && j - 1 != 0)
                        {
                            pathFinding.GetNode(i - 1, j).SetIsWalkable(false);
                            pathFinding.GetNode(i, j - 1).SetIsWalkable(false);
                        }

                        if (levelGrid[i - 1, j + 1] == 1 && i - 1 != 0 && j + 1 != 6)
                        {
                            pathFinding.GetNode(i - 1, j).SetIsWalkable(false);
                            pathFinding.GetNode(i, j + 1).SetIsWalkable(false);
                        }

                        if (levelGrid[i + 1, j - 1] == 1 && i + 1 != 13 && j - 1 != 0)
                        {
                            pathFinding.GetNode(i + 1, j).SetIsWalkable(false);
                            pathFinding.GetNode(i, j - 1).SetIsWalkable(false);
                        }

                        if (levelGrid[i + 1, j + 1] == 1 && i + 1 != 13 && j + 1 != 6)
                        {
                            pathFinding.GetNode(i + 1, j).SetIsWalkable(false);
                            pathFinding.GetNode(i, j + 1).SetIsWalkable(false);
                        }
                    }
                }
                
                if (levelGrid[i, j] == 2 || levelGrid[i, j] == 3)
                {
                    pathFinding.GetNode(i, j).SetIsWalkable(true);
                }
            }
        }
    }

    /// <summary>
    /// Generates the level with wall and floor prefabs
    /// </summary>
    private void GenerateLevel()
    {
        for (int i = 1; i < 13; i++)
        {
            for (int j = 1; j < 6; j++)
            {
                nodePos = pathFinding.GetNode(i, j).GetPos();
                if (levelGrid[i, j] == 1)
                {
                    floors.Add(Instantiate(tilePrefabs[0], new Vector3(nodePos.x + .5f, nodePos.y + .5f) * pathFinding.getGrid().GetCellSize(), Quaternion.identity));
                    walls.Add(Instantiate(tilePrefabs[1], new Vector3(nodePos.x + .5f, nodePos.y + .5f) * pathFinding.getGrid().GetCellSize(), Quaternion.identity));
                }
                else if (levelGrid[i, j] == 2)
                {
                    floors.Add(Instantiate(tilePrefabs[0], new Vector3(nodePos.x + .5f, nodePos.y + .5f) * pathFinding.getGrid().GetCellSize(), Quaternion.identity));
                    enemy = Instantiate(enemyTankPrefab, new Vector3(nodePos.x + .5f, nodePos.y + .5f) * pathFinding.getGrid().GetCellSize(), Quaternion.identity);
                    enemy.tag = "Enemy";
                    enemy.transform.parent = transform;
                }
                else if (levelGrid[i, j] == 3)
                {
                    floors.Add(Instantiate(tilePrefabs[0], new Vector3(nodePos.x + .5f, nodePos.y + .5f) * pathFinding.getGrid().GetCellSize(), Quaternion.identity));
                    player =Instantiate(playerTankPrefab, new Vector3(nodePos.x + .5f, nodePos.y + .5f) * pathFinding.getGrid().GetCellSize(), Quaternion.identity);
                    player.tag = "Player";
                    player.transform.parent = transform;
                }
                else
                {                    
                    floors.Add(Instantiate(tilePrefabs[0], new Vector3(nodePos.x + .5f, nodePos.y + .5f) * pathFinding.getGrid().GetCellSize(), Quaternion.identity));
                }
            }
        }
    }

    /// <summary>
    /// Creates the grid used to generate the level randomly
    /// </summary>
    public void levelGridCreate()
    {
        int randomTile;

        int wallSpawnCounter = 0;
        int enemyCounter = 0;

        // 1 = wall
        // 2 = enemy
        // 3 = player

        for (int i = 1; i < levelWidth - 1; i++)
        {
            for (int j = 1; j < levelHeight - 1; j++)
            {
                randomTile = UnityEngine.Random.Range(1, 17);

                //Walls have 1 / 8 chance of spawning
                if ((randomTile == 1 || randomTile == 2) && wallSpawnCounter < 10)
                {
                    levelGrid[i, j] = 1;
                    wallSpawnCounter++;
                }
                //enemies have 1 / 16 chance of spawning
                else if (randomTile == 3 && enemyCounter < 3)
                {
                    levelGrid[i, j] = 2;
                    enemyCounter++;
                }

            }
        }

        int randI = UnityEngine.Random.Range(1, 13);
        int randJ = UnityEngine.Random.Range(1, 6);

        if (enemyCounter == 0)
        {
            while (levelGrid[randI, randJ] == 1)
            {
                randI = UnityEngine.Random.Range(1, 13);
                randJ = UnityEngine.Random.Range(1, 6);
            }
            levelGrid[randI, randJ] = 2;
        }

        while (levelGrid[randI, randJ] == 1 || levelGrid[randI, randJ] == 2)
        {
            randI = UnityEngine.Random.Range(1, 13);
            randJ = UnityEngine.Random.Range(1, 6);
        }

        levelGrid[randI, randJ] = 3;
    }

    /// <summary>
    /// Removes walls if they form an inescapable circle for one node
    /// </summary>
    /// <param name="i">Position for the grid rows</param>
    /// <param name="j">Position for the grid columns</param>
    private void fixEnclosed(int i, int j)
    {
        if (checkEnclosed(i, j))
        {
            if (i - 1 <= 0)
            {
                levelGrid[i + 1, j] = 0;
            }
            if (i + 1 >= levelWidth - 1)
            {
                levelGrid[i - 1, j] = 0;
            }
            if (i > 1 && i < 12)
            {
                levelGrid[i - 1, j] = 0;
                levelGrid[i + 1, j] = 0;
            }

            if (j - 1 <= 0)
            {
                levelGrid[i, j + 1] = 0;
            }
             if (j + 1 >= levelHeight - 1)
            {
                levelGrid[i, j - 1] = 0;
            }
            if (j > 1 && j < 5)
            {
                levelGrid[i, j - 1] = 0;
                levelGrid[i, j + 1] = 0;
            }

            //levelGrid[i, j] = 0;
        }
    }

    /// <summary>
    /// Checks if a grid node is surounded by walls
    /// </summary>
    /// <param name="i">Position for grid rows</param>
    /// <param name="j">Position for grid columns</param>
    /// <returns></returns>
    private bool checkEnclosed(int i, int j)
    {
        if (i == 0 || j == 0 || i == 13 || j == 6)
        {
            return false;
        }

        if (levelGrid[i - 1, j] == 1 && levelGrid[i - 1, j - 1] == 1 && levelGrid[i, j - 1] == 1 && levelGrid[i + 1, j - 1] == 1 && levelGrid[i + 1, j] == 1 && levelGrid[i + 1, j + 1] == 1 && levelGrid[i, j + 1] == 1 && levelGrid[i - 1, j + 1] == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Pathfinding GetPathGrid()
    {
        return pathFinding;
    }

    //Debugging
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
