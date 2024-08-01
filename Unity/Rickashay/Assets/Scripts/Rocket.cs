using System.Collections;
using System.Collections.Generic;
using UnityEngine;

//applied only to the Rocket Prefab
public class Rocket : MonoBehaviour
{
    //use explosion object Prefab
    public GameObject hitEffect;

    private void OnCollisionEnter2D(Collision2D collision)
    {
        GameObject effect = Instantiate(hitEffect, transform.position, Quaternion.identity);
        Destroy(effect, 1f);
        Destroy(gameObject);
    }
}
