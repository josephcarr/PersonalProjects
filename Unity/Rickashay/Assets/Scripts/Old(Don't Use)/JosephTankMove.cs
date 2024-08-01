using System.Collections;
using System.Collections.Generic;
using UnityEngine;


//applied to entire tank object
public class JosephTankMove : MonoBehaviour
{
    public Track trackLeft;
    public Track trackRight;

    public Rigidbody2D rbTank;
    public Camera cam;

    Vector2 movement;
    Vector3 mousePos;

    Transform tankTurret;

    bool move = false;
    float moveSpeed = 0f;
    float moveAcceleration = 10f;
    float moveDeceleration = 50f;
    float moveSpeedMax = 500f;

    bool rotate = false;
    float rotateSpeed = 0f;
    float rotateAcceleration = 400f;
    float rotateDeceleration = 1000f;
    float rotateSpeedMax = 26000f;

    void Start()
    {
        tankTurret = transform.Find("Turret");
    }

    void Update()
    {
        movement.x = Input.GetAxisRaw("Horizontal");
        movement.y = Input.GetAxisRaw("Vertical");
        mousePos = cam.ScreenToWorldPoint(Input.mousePosition);

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
        rbTank.angularVelocity = movement.x * rotateSpeed * Time.deltaTime * -1f;

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
        rbTank.velocity = transform.up * movement.y * moveSpeed * Time.deltaTime;

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
        //Turret Rotation using Mouse
        Vector3 lookDir = mousePos - tankTurret.position;
        float angleZ = Mathf.Atan2(lookDir.y, lookDir.x) * Mathf.Rad2Deg - 90f;
        tankTurret.rotation = Quaternion.Euler(0.0f, 0.0f, angleZ);
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
}
