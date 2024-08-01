using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// The general tank class to be inherited
/// Sets up everything that every tank game object should have to function
/// </summary>
public abstract class Tank : MonoBehaviour
{
    public GameObject hitEffect;

    public Track trackLeft;
    public Track trackRight;

    [HideInInspector]
    public Rigidbody2D rbTank;

    internal Vector3 inputVector;

    internal Transform tankTurret;

    internal bool moving = false;
    internal float moveSpeed = 0f;

    internal bool rotating = false;
    internal float rotateSpeed = 0f;

    internal bool isDead = false;

    internal GameObject soundsGO;
    internal Sounds s;

    /// <summary>
    /// Starts the Tank Track animation
    /// </summary>
    internal void trackStart()
    {
        trackLeft.animator.SetBool("isMoving", true);
        trackRight.animator.SetBool("isMoving", true);
    }

    /// <summary>
    /// Stops the Tank Track animation
    /// </summary>
    internal void trackStop()
    {
        trackLeft.animator.SetBool("isMoving", false);
        trackRight.animator.SetBool("isMoving", false);
    }

    //called when game object collides with something
    internal void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.collider.CompareTag("Projectile"))
        {
            int bounces = collision.gameObject.GetComponent<Projectile>().getNumBounces();

            if (bounces >= 1)
            {
                destroyPlayer();
            }
        }
    }

    /// <summary>
    /// Destroys the player game object from the scene
    /// </summary>
    internal void destroyPlayer()
    {
        isDead = true;
        GameObject effect = Instantiate(hitEffect, transform.position, Quaternion.identity);
        s.stopMovingSound();
        s.playExplosionSound();
        Destroy(effect, 1f);
        Destroy(gameObject);
    }

    /// <summary>
    /// Handles the implementation of rotating the Tank Turret
    /// </summary>
    internal abstract void turretRotation();
}
