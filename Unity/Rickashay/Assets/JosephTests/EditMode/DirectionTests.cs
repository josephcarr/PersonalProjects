using System.Collections;
using System.Collections.Generic;
using NUnit.Framework;
using UnityEngine;
using UnityEngine.TestTools;

public class DirectionTests
{
    [Test(Author = "JosephCarrasco", Description = "Tests whether the player tank moves forward according to the input vector")]
    public void Test_Player_Move_Forward()
    {
        Vector3 moveVector = new Vector3(0, 1);

        Assert.AreEqual(1, new Movement(0, 1).Calculate(moveVector, 1).y, 0.1f);
    }

    [Test(Author = "JosephCarrasco", Description = "Tests whether the player tank moves backward according to the input vector")]
    public void Test_Player_Move_Backward()
    {
        Vector3 moveVector = new Vector3(0, -1);

        Assert.AreEqual(-1, new Movement(0, 1).Calculate(moveVector, 1).y, 0.1f);
    }

    [Test(Author = "JosephCarrasco", Description = "Tests whether the player tank rotates right according to the input vector")]
    public void Test_Player_Rotate_Right()
    {
        Vector3 moveVector = new Vector3(1, 0);

        Assert.AreEqual(1, new Movement(1, 0).Calculate(moveVector, 1).x, 0.1f);
    }

    [Test(Author = "JosephCarrasco", Description = "Tests whether the player tank rotates left according to the input vector")]
    public void Test_Player_Rotate_Left()
    {
        Vector3 moveVector = new Vector3(-1, 0);

        Assert.AreEqual(-1, new Movement(1, 0).Calculate(moveVector, 1).x, 0.1f);
    }
}
