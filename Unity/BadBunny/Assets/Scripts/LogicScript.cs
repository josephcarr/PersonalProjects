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
    private static int playerTime;
    private static int level;

    private static bool gameOver = false;
    //public Text txtFinalTime;

    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public static void setPlayerTime(int time)
    {
        playerTime = time;
    }

    public static int getPlayerTime()
    {
        return playerTime;
    }

    public static void setLevel(int lvl)
    {
        level = lvl;
    }

    public static int getLevel()
    {
        return level;
    }

    public static void setGameOver(bool over)
    {
        gameOver = over;
    }

    public static bool isGameOver()
    {
        return gameOver;
    }
}
