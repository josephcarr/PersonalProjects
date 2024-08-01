using System.Collections;
using System.Collections.Generic;
using UnityEngine;

//applied to the entire tank object
/// <summary>
/// Class used to handle player shooting
/// </summary>
public class Shooting : MonoBehaviour
{
    public Transform firePoint;
    public GameObject projectilePrefab;
    internal List<GameObject> projectiles;
    public float projectileForce = 20f;

    private Transform canvas;
    private GameObject pauseMenu;

    internal GameObject soundsGO;
    internal Sounds s;

    private void Start()
    {
        projectiles = new List<GameObject>();

        canvas = GameObject.Find("Canvas").transform;
        pauseMenu = canvas.Find("PauseMenu").gameObject;

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

        if (Input.GetButtonDown("Fire1") && !pauseMenu.activeSelf)
        {
            if (projectiles.Count < 3)
            {
                Shoot();
            }
        }
    }

    /// <summary>
    /// Creates a projectile and applies a force to make it move
    /// </summary>
    internal void Shoot()
    {
        s.playPewSound();
        GameObject rocket = Instantiate(projectilePrefab, firePoint.position, firePoint.rotation);
        Rigidbody2D rb = rocket.GetComponent<Rigidbody2D>();
        rb.AddForce(firePoint.up * projectileForce, ForceMode2D.Impulse);
        projectiles.Add(rocket);
    }
}
