using System;
using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.UIElements.Experimental;
using Random = UnityEngine.Random;

public class LogicScript : MonoBehaviour
{
    private float gameTimer;
    public Text timerText;
    private int playerTime;
    public Text levelText;
    private int level;

    public GameObject bunny;
    private GameObject bunnySpawns;
    private List<GameObject> bunnySpawnsList;
    public float spawnRate;
    private float spawnTimer;

    private bool gameOver = false;
    public Text finalTime;

    void Start()
    {
        gameTimer = 0;
        playerTime = 0;
        level = 0;

        bunnySpawns = GameObject.FindGameObjectWithTag("BunnySpawns");
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
        if (!gameOver)
        {

            gameTimer += Time.deltaTime;
            playerTime = (int)Math.Floor(gameTimer);
            timerText.text = "Time: " + playerTime.ToString();

            if (spawnTimer < spawnRate)
            {
                spawnTimer += Time.deltaTime;
            }
            else
            {
                spawnBunny();
                spawnTimer = 0;
            }

            if (playerTime < 10)
            {
                level = 1;
            }
            else if (playerTime < 20)
            {
                level = 2;
            }

            levelText.text = "Level: " + level.ToString();
        }
        else
        {
            finalTime.text = "Your Final Time:\n" + playerTime + " seconds!";
        }
    }

    public int getLevel()
    {
        return level;
    }

    private void spawnBunny()
    {
        int randomInt = Random.Range(0, 4);
        //Debug.Log(randomInt);
        GameObject randomSpawn = bunnySpawnsList[randomInt];
        Instantiate(bunny, randomSpawn.transform.position, randomSpawn.transform.rotation);
    }

    public void setGameOver(bool over)
    {
        gameOver = over;
    }

    public bool isGameOver()
    {
        return gameOver;
    }
}
