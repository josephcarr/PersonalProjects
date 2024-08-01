using System.Collections;
using System.Collections.Generic;
using NUnit.Framework;
using UnityEngine;
using UnityEngine.TestTools;

public class MoveTests
{
    [UnityTest]
    public IEnumerator Test_Player_isMoving()
    {
        var gameObject = new GameObject();
        var playerTank = gameObject.AddComponent<PlayerTank>();
        gameObject.AddComponent<Rigidbody2D>();

        playerTank.movePlayer(new Vector3(0, 1));

        yield return new WaitForSeconds(1f);

        Assert.AreEqual(new Vector3(0, 1), gameObject.GetComponent<Rigidbody2D>().velocity);
    }

    [UnityTest]
    public IEnumerator Test_Player_isRotating()
    {
        var gameObject = new GameObject();
        var playerTank = gameObject.AddComponent<PlayerTank>();
        gameObject.AddComponent<Rigidbody2D>();

        playerTank.rotatePlayer(new Vector3(1, 0));

        yield return new WaitForSeconds(1f);

        Assert.AreEqual(new Vector3(1, 0), gameObject.GetComponent<Rigidbody2D>().angularVelocity);
    }
}
