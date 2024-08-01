using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Class that handles the movement calculations for the player
/// </summary>
public class Movement
{
    private float mSpeed;
    private float rSpeed;

    private float moveAcceleration;
    private float moveDeceleration;
    private float moveSpeedMax;

    private float rotateAcceleration;
    private float rotateDeceleration;
    private float rotateSpeedMax;

    /// <summary>
    /// Constructor for the Movement class that initialized the parameters
    /// </summary>
    /// <param name="rotateSpeed">The player tank's rotation speed</param>
    /// <param name="movementSpeed">The player tank's movement speed</param>
    public Movement(float rotateSpeed, float movementSpeed)
    {
        rSpeed = rotateSpeed;
        mSpeed = movementSpeed;

        moveAcceleration = 4f;
        moveDeceleration = 20f;
        moveSpeedMax = 200f;

        rotateAcceleration = 80f;
        rotateDeceleration = 200f;
        rotateSpeedMax = 5200f;
    }

    /// <summary>
    /// Sets the movement speed of the player when the player inputs to move
    /// </summary>
    /// <param name="move">Boolean to check whether the player is moving</param>
    public void SetMovementSpeed(bool move)
    {
        if (move)
        {
            mSpeed = (mSpeed < moveSpeedMax) ? mSpeed + moveAcceleration : moveSpeedMax;
        }
        else
        {
            mSpeed = (mSpeed > 0) ? mSpeed - moveDeceleration : 0;
        }
    }

    /// <summary>
    /// Sets the rotation speed of the player when the player inputs to rotate
    /// </summary>
    /// <param name="rotate">Boolean to check whether the player is rotating</param>
    public void SetRotateSpeed(bool rotate)
    {
        if (rotate)
        {
            rSpeed = (rSpeed < rotateSpeedMax) ? rSpeed + rotateAcceleration : rotateSpeedMax;
        }
        else
        {
            rSpeed = (rSpeed > 0) ? rSpeed - rotateDeceleration : 0;
        }
    }

    /// <summary>
    /// Calculates the movement for the player tank
    /// </summary>
    /// <param name="inputVector">Vector that holds the player input directions</param>
    /// <param name="deltaTime">Float that holds the time for smooth rigid body movement</param>
    /// <returns>The final vector that will be used to move the player tank game object</returns>
    public Vector3 Calculate(Vector3 inputVector, float deltaTime)
    {
        float rotation = inputVector.x * rSpeed * deltaTime;
        float movement = inputVector.y * mSpeed * deltaTime;

        return new Vector3(rotation, movement);
    }
}
