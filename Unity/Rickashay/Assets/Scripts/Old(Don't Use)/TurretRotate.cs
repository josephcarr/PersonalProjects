using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TurretRotate : MonoBehaviour
{
    /////*******************************************/////
    /////                   VARS                    /////  
    /////*******************************************/////

    public Camera cam;
    public Rigidbody2D rbTurret;
    Vector2 mousePos;

    private void Update()
    {
        mousePos = cam.ScreenToWorldPoint(Input.mousePosition);
    }

    private void FixedUpdate()
    {
        //Turret Rotation using Mouse
        Vector2 lookDir = mousePos - rbTurret.position;
        float angleZ = Mathf.Atan2(lookDir.y, lookDir.x) * Mathf.Rad2Deg - 90f;
        rbTurret.rotation = angleZ;
    }
}
