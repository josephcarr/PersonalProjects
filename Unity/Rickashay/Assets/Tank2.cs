using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Tank2 : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void FixedUpdate()
    {
        if (Input.GetKey("w"))
            GetComponent<Rigidbody2D>().AddForce(transform.up * 1, ForceMode2D.Impulse);

        if (Input.GetKey("s"))
            GetComponent<Rigidbody2D>().AddForce(transform.up * -1, ForceMode2D.Impulse);
        
        if (Input.GetKeyUp("s") || Input.GetKeyUp("w")) 
           GetComponent<Rigidbody2D>().Sleep();


    }
}
