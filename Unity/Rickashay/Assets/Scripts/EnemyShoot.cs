using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyShoot : Shooting
{
    private float timeBtwShots;
    public float startTimeBtwShots;
    private int randNum;
    public bool shoot = false;

    // Start is called before the first frame update
    void Start()
    {
        timeBtwShots = startTimeBtwShots;
        projectiles = new List<GameObject>();

        soundsGO = GameObject.Find("SoundsGameObject");
        s = soundsGO.GetComponent<Sounds>();
    }

    // Update is called once per frame
    private void Update()
    {
        if (projectiles.Count != 0)
        {
            for (int i = 0; i < projectiles.Count; i++)
            {
                if (projectiles[i] == null)
                {
                    projectiles.RemoveAt(i);
                }
            }
        }

        if (timeBtwShots <= 0)
        {
            randNum = Random.Range(0, 4);
            
            if (projectiles.Count < randNum && shoot)
            {
                Shoot();
            }
            timeBtwShots = startTimeBtwShots;
        }
        else
        {
            timeBtwShots -= Time.deltaTime;
        }
    }
}
