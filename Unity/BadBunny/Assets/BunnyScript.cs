using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BunnyScript : MonoBehaviour
{
    private LogicScript logic;
    public Rigidbody2D myRigidbody;
    private GameObject carrot;

    private Vector3 heading;
    private float distance;
    private Vector3 direction;
    public float speed;

    // Start is called before the first frame update
    void Start()
    {
        logic = GameObject.FindGameObjectWithTag("Logic").GetComponent<LogicScript>();
        carrot = GameObject.FindGameObjectWithTag("Carrot");

        heading = carrot.transform.position - transform.position;
        distance = heading.magnitude;
        direction = heading / distance; // This is now the normalized direction.
        //speed = 0;
    }

    // Update is called once per frame
    void Update()
    {
        if (!logic.isGameOver())
        {
            if (logic.getLevel() == 1)
            {
                speed = 2;
            }


            myRigidbody.velocity = direction * speed;
        }
        else
        {
            speed = 0;
            myRigidbody.velocity = new Vector3(0, 0, 0);
        }
    }
}
