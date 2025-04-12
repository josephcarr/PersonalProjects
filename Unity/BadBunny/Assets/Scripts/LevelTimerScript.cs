using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class TimerScript : MonoBehaviour
{
    [SerializeField] Text txtTimer;
    [SerializeField] Text txtLevel;
    [SerializeField] Text txtFinalTime;

    private float gameTimer;
    private int playerTime;
    private int level;

    // Start is called before the first frame update
    void Start()
    {
        gameTimer = 0;
        playerTime = 0;
        level = 0;
    }

    // Update is called once per frame
    void Update()
    {
        if (!LogicScript.isGameOver())
        {
            gameTimer += Time.deltaTime;
            playerTime = (int) Math.Floor(gameTimer);

            if (playerTime < 10)
            {
                level = 1;
            }
            else if (playerTime < 20)
            {
                level = 2;
            }

            txtTimer.text = "Time: " + playerTime.ToString();
            txtLevel.text = "Level: " + level.ToString();
        }
        else
        {
            txtFinalTime.text = "Your Final Time:\n" + LogicScript.getPlayerTime().ToString() + " seconds!";
        }
    }
}
