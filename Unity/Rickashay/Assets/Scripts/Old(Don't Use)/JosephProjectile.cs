using System.Collections;
using System.Collections.Generic;
using UnityEngine;

//only applied to the projectile prefab
public class JosephProjectile : MonoBehaviour
{
    //use explosion object Prefab
    public GameObject hitEffect;
    Vector3 lastVelocity;
    Rigidbody2D rbProjectile;

    public int maxBounces;
    int bounce = 0;

    void Start()
    {
        Destroy(gameObject, 20f); //destoys rocket if out of bounds
        rbProjectile = gameObject.GetComponent<Rigidbody2D>();
    }

    void Update()
    {
        lastVelocity = rbProjectile.velocity;
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.collider.CompareTag("Wall") && bounce < maxBounces)
        {
            //Handles rocket bounce
            float speed = lastVelocity.magnitude;
            Vector3 direction = Vector3.Reflect(lastVelocity.normalized, collision.contacts[0].normal);
            rbProjectile.velocity = direction * speed;

            //Rotates the rocket to face movement direction
            Quaternion rotation = Quaternion.FromToRotation(lastVelocity, rbProjectile.velocity);
            transform.rotation = rotation * transform.rotation;

            bounce++;
        }
        else
        {
            //Explosion effect and destroy rocket object
            GameObject effect = Instantiate(hitEffect, transform.position, Quaternion.identity);
            Destroy(effect, 1f);
            Destroy(gameObject);
        }

    }
}
