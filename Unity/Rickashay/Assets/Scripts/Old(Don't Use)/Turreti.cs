using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Turreti : MonoBehaviour
{

    /////*******************************************/////
    /////                   VARS                    /////  
    /////*******************************************/////



    public string keyRotateRight;
    public string keyRotateLefti;


    bool rotateRight = false;
    bool rotateLeft = false;
    float rotateSpeedRight = 0f;
    float rotateSpeedLeft = 0f;
    float rotateAcceleration = 8f;
    float rotateDeceleration = 10f;
    float rotateSpeedMax = 160f;


    /////*******************************************/////
    /////                 UPDATE                    /////  
    /////*******************************************/////

    void Update()
    {

        rotateLeft = (Input.GetKeyDown(keyRotateLefti)) ? true : rotateLeft;
        rotateLeft = (Input.GetKeyUp(keyRotateLefti)) ? false : rotateLeft;
        if (rotateLeft)
        {
            rotateSpeedLeft = (rotateSpeedLeft < rotateSpeedMax) ? rotateSpeedLeft + rotateAcceleration : rotateSpeedMax;
        }
        else
        {
            rotateSpeedLeft = (rotateSpeedLeft > 0) ? rotateSpeedLeft - rotateDeceleration : 0;
        }
        transform.Rotate(0f, 0f, rotateSpeedLeft * Time.deltaTime);

        rotateRight = (Input.GetKeyDown(keyRotateRight)) ? true : rotateRight;
        rotateRight = (Input.GetKeyUp(keyRotateRight)) ? false : rotateRight;
        if (rotateRight)
        {
            rotateSpeedRight = (rotateSpeedRight < rotateSpeedMax) ? rotateSpeedRight + rotateAcceleration : rotateSpeedMax;
        }
        else
        {
            rotateSpeedRight = (rotateSpeedRight > 0) ? rotateSpeedRight - rotateDeceleration : 0;
        }
        transform.Rotate(0f, 0f, rotateSpeedRight * Time.deltaTime * -1f);




    }



}