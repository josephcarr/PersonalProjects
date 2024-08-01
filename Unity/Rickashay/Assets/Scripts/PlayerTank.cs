using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// The class for the player
/// Inherits the Tank Class and the Interface IMoveVelocity
/// Uses the Movement Class to calculate the movement of the player tank game object
/// </summary>
public class PlayerTank : Tank, IMoveVelocity
{
    private Camera mainCam;

    private Vector3 mousePos;
    private Vector3 movementVector;

    public Movement Movement;

    private float deltaTime;

    //called before the update function
    private void Start()
    {
        rbTank = GetComponent<Rigidbody2D>();
        tankTurret = transform.Find("Turret");
        gameObject.tag = "Player";
        mainCam = Camera.main;
        Movement = new Movement(rotateSpeed, moveSpeed);

        soundsGO = GameObject.Find("SoundsGameObject");
        s = soundsGO.GetComponent<Sounds>();
    }

    // Update is called once per frame
    private void Update()
    {
        //tracks mouse for tank aiming
        mousePos = mainCam.ScreenToWorldPoint(Input.mousePosition);

        //handles whether player is inputting for movement (forward or backward)
        moving = (inputVector.y > 0 || inputVector.y < 0) ? true : moving;
        moving = (inputVector.y == 0) ? false : moving;

        //handles whether player is inputting for rotation
        rotating = (inputVector.x > 0 || inputVector.x < 0) ? true : rotating;
        rotating = (inputVector.x == 0) ? false : rotating;

        Movement.SetMovementSpeed(moving);
        Movement.SetRotateSpeed(rotating);

        movementVector = Movement.Calculate(inputVector, deltaTime);

        //handles Animation for the tank tracks
        if (moving || rotating)
        {
            trackStart();
        }
        else
        {
            if (!isDead)
            {
                s.playMovingSound();
            }
            trackStop();
        }
    }

    // FixedUpdate is called every fixed framerate frame
    private void FixedUpdate()
    {
        deltaTime = Time.fixedDeltaTime;

        rotatePlayer(movementVector);

        movePlayer(movementVector);

        turretRotation();
    }

    /// <summary>
    /// Handles moving the player tank forwards or backwards
    /// </summary>
    /// <param name="move">The movement vector for the player tank</param>
    public void movePlayer(Vector3 move)
    {
        rbTank.velocity = transform.up * move.y;
    }

    /// <summary>
    /// Handles rotating the player tank left or right
    /// </summary>
    /// <param name="move">The movement vector for the player tank</param>
    public void rotatePlayer(Vector3 move)
    {
        rbTank.angularVelocity = move.x * -1f;
    }

    internal override void turretRotation()
    {
        //Turret Rotation using Mouse
        Vector3 lookDir = mousePos - tankTurret.position;
        float angleZ = Mathf.Atan2(lookDir.y, lookDir.x) * Mathf.Rad2Deg - 90f;
        tankTurret.rotation = Quaternion.Euler(0.0f, 0.0f, angleZ);
    }

    public void SetVelocity(Vector3 velocityVector)
    {
        inputVector = velocityVector;
    }
}
