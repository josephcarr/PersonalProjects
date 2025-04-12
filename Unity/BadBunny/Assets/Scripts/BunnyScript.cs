using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BunnyScript : MonoBehaviour
{
    //[SerializeField] LogicScript logic;
    public Rigidbody2D myRigidbody;
    [SerializeField] GameObject carrot;

    private Vector3 heading;
    private float distance;
    private Vector3 direction;
    public float speed;

    // Start is called before the first frame update
    void Start()
    {
        //logic = GameObject.FindGameObjectWithTag("Logic").GetComponent<LogicScript>();

        heading = carrot.transform.position - transform.position;
        distance = heading.magnitude;
        direction = heading / distance; // This is now the normalized direction.
        //speed = 0;
    }

    // Update is called once per frame
    void Update()
    {
        if (!LogicScript.isGameOver())
        {
            if (Input.touchCount > 0 )
            {
                foreach(Touch t in Input.touches)
                {
                    Vector2 touchPos = t.position;

                    if (t.phase == TouchPhase.Began)
                    {
                        Ray ray = Camera.main.ScreenPointToRay(touchPos);
                        RaycastHit2D hit = Physics2D.Raycast(ray.origin, ray.direction);

                        if (hit.collider != null)
                        {
                            Debug.Log("Touched " + hit.transform.gameObject.transform.name);
                        }
                    }
                }
            }

            if (LogicScript.getLevel() == 1)
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
