using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpawnScript : MonoBehaviour
{
    [SerializeField] GameObject bunny;
    [SerializeField] GameObject bunnySpawns;
    private List<GameObject> bunnySpawnsList;

    [SerializeField] float spawnRate;
    private float spawnTimer;

    // Start is called before the first frame update
    void Start()
    {
        bunnySpawnsList = new List<GameObject>();
        //Gets list of spawns
        foreach (Transform child in bunnySpawns.transform)
        {
            GameObject spawn = child.gameObject;
            bunnySpawnsList.Add(spawn);
            //Debug.Log(spawn.name);
        }
        //Debug.Log(bunnySpawnsList.Count);
        //spawnRate = 0;
        spawnTimer = 0;

        spawnBunny();
    }

    // Update is called once per frame
    void Update()
    {
        if (!LogicScript.isGameOver())
        {
            if (spawnTimer < spawnRate)
            {
                spawnTimer += Time.deltaTime;
            }
            else
            {
                spawnBunny();
                spawnTimer = 0;
            }
        }
    }

    private void spawnBunny()
    {
        int randomInt = Random.Range(0, 4);
        //Debug.Log(randomInt);
        GameObject randomSpawn = bunnySpawnsList[randomInt];
        Instantiate(bunny, randomSpawn.transform.position, randomSpawn.transform.rotation);
    }
}
