using System.Collections;
using System.Collections.Generic;
using UnityEngine;

//applied only to the Rocket Prefab
/// <summary>
/// Class for the projectiles
/// </summary>
public class Projectile : MonoBehaviour
{
    //use explosion object Prefab
    public GameObject hitEffect;
    private Vector2 lastVelocity;
    private int numBounces = 0;
    private int maxBounces;
    private Rigidbody2D rbProjectile;

    GameObject soundsGO;
    Sounds s;

    private void Start()
    {
        rbProjectile = gameObject.GetComponent<Rigidbody2D>();
        Destroy(gameObject, 20f);
        gameObject.tag = "Projectile";

        maxBounces = 2;

        soundsGO = GameObject.Find("SoundsGameObject");
        s = soundsGO.GetComponent<Sounds>();
    }

    private void Update()
    {
        lastVelocity = rbProjectile.velocity;
    }

    // Detecting collisions, on collision -> explodes
    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.collider.CompareTag("Wall") && numBounces < maxBounces)
        {
            //handles projectile bounce
            s.playBounceSound();
            Rickashay(collision);
            numBounces++;

        }
        else
        {
            destroyProjectile();
        }

    }

    /// <summary>
    /// handles bouncing the projectile
    /// </summary>
    /// <param name="collision"></param>
    private void Rickashay(Collision2D collision)
    {
        Vector2 inNormal = collision.contacts[0].normal;
        Vector2 outDirection = Vector2.Reflect(lastVelocity, inNormal);
        rbProjectile.velocity = outDirection;

        //Rotates the rocket to face movement direction
        Quaternion rotation = Quaternion.FromToRotation(lastVelocity, rbProjectile.velocity);
        transform.rotation = rotation * transform.rotation;
    }

    /// <summary>
    /// Destroys the game object from the scene
    /// </summary>
    private void destroyProjectile()
    {
        GameObject effect = Instantiate(hitEffect, transform.position, Quaternion.identity);
        Destroy(effect, 1f);
        Destroy(gameObject);
        
    }

    /// <summary>
    /// 
    /// </summary>
    /// <returns>Returns how many times a projectile has bounced</returns>
    public int getNumBounces()
    {
        return numBounces;
    }
}