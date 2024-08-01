using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Enemy : MonoBehaviour
{
    public GameObject hitEffect;

    public Track trackLeft;
    public Track trackRight;
    private GameObject[] player;
    private Rigidbody2D rbPlayer;

    public Rigidbody2D rbTank;

    public Camera cam;

    Vector2 movement;
    Vector3 mousePos;
    Vector3 playerPos;

    Transform tankTurret;


    public string keyMoveForward;
    public string keyMoveReverse;
    public string keyRotateRight;
    public string keyRotateLeft;

    bool move = false;
    float moveSpeed = 0f;
    float moveAcceleration = 4f;
    float moveDeceleration = 20f;
    float moveSpeedMax = 200f;

    bool rotate = false;
    float rotateSpeed = 0f;
    float rotateAcceleration = 80f;
    float rotateDeceleration = 200f;
    float rotateSpeedMax = 5200f;

    void Start()
    {
        tankTurret = transform.Find("Turret");
        gameObject.tag = "Enemy";

        player = GameObject.FindGameObjectsWithTag("Player");
        rbPlayer = player[0].GetComponent<Rigidbody2D>();

    }

    void Update()
    {
        if (rbPlayer != null)
        {
            playerPos = rbPlayer.position;
        }
        

        if (Input.GetKey(keyRotateLeft) && Input.GetKey(keyRotateRight))
        {
            movement.x = 0f;
        }
        else if (Input.GetKey(keyRotateLeft))
        {
            movement.x = -1f;
        }
        else if (Input.GetKey(keyRotateRight))
        {
            movement.x = 1f;
        }
        else
        {
            movement.x = 0f;
        }

        rotate = (movement.x > 0 || movement.x < 0) ? true : rotate;
        rotate = (movement.x == 0) ? false : rotate;
        if (rotate)
        {
            rotateSpeed = (rotateSpeed < rotateSpeedMax) ? rotateSpeed + rotateAcceleration : rotateSpeedMax;
        }
        else
        {
            rotateSpeed = (rotateSpeed > 0) ? rotateSpeed - rotateDeceleration : 0;
        }

        if (Input.GetKey(keyMoveReverse) && Input.GetKey(keyMoveForward))
        {
            movement.y = 0f;
        }
        else if (Input.GetKey(keyMoveReverse))
        {
            movement.y = -1f;
        }
        else if (Input.GetKey(keyMoveForward))
        {
            movement.y = 1f;
        }
        else
        {
            movement.y = 0f;
        }

        move = (movement.y > 0 || movement.y < 0) ? true : move;
        move = (movement.y == 0) ? false : move;
        if (move)
        {
            moveSpeed = (moveSpeed < moveSpeedMax) ? moveSpeed + moveAcceleration : moveSpeedMax;
        }
        else
        {
            moveSpeed = (moveSpeed > 0) ? moveSpeed - moveDeceleration : 0;
        }

        //handles Animation for the tank tracks
        if (move | rotate)
        {
            trackStart();
        }
        else
        {
            trackStop();
        }
    }

    void FixedUpdate()
    {
        //rotates tank body
        rbTank.angularVelocity = movement.x * rotateSpeed * Time.deltaTime * -1f;

        //moves tank forward and backward
        rbTank.velocity = transform.up * movement.y * moveSpeed * Time.deltaTime;

        if (rbPlayer != null)
        {
            //Turret Rotation using Mouse
            Vector3 lookDir = playerPos - tankTurret.position;
            float angleZ = Mathf.Atan2(lookDir.y, lookDir.x) * Mathf.Rad2Deg - 90f;
            tankTurret.rotation = Quaternion.Euler(0.0f, 0.0f, angleZ);
        }
    }

    void trackStart()
    {
        trackLeft.animator.SetBool("isMoving", true);
        trackRight.animator.SetBool("isMoving", true);
    }

    void trackStop()
    {
        trackLeft.animator.SetBool("isMoving", false);
        trackRight.animator.SetBool("isMoving", false);
    }

    private void OnCollisionEnter2D(Collision2D collision)
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

    private void destroyPlayer()
    {
        GameObject effect = Instantiate(hitEffect, transform.position, Quaternion.identity);
        Destroy(effect, 1f);
        Destroy(gameObject);
    }
}
