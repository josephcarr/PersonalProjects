using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Interface for movement velocity of the player tank
/// </summary>
internal interface IMoveVelocity
{
    /// <summary>
    /// Sets the movement velocity direction based on player input
    /// </summary>
    /// <param name="velocityVector">The input vector for the player tank's velocity</param>
    void SetVelocity(Vector3 velocityVector);

}