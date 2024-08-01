using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyTank : Tank
{
    private enum State
    {
        Roam,
        Chase,
        Stop,
    }

    private State state;

    private EnemyPathfinding pathfindingMove;
    private Vector3 startingPos;
    private Vector3 roamPos;

    private GameObject[] player;
    private Transform playerTransform;
    Vector3 playerPos;

    private EnemyShoot enemyShooting;

    private void Awake()
    {
        pathfindingMove = GetComponent<EnemyPathfinding>();
        enemyShooting = GetComponent<EnemyShoot>();
        state = State.Chase;
    }

    private void Start()
    {
        rbTank = GetComponent<Rigidbody2D>();
        tankTurret = transform.Find("Turret");
        gameObject.tag = "Enemy";

        player = GameObject.FindGameObjectsWithTag("Player");
        playerTransform = player[0].transform;
        playerPos = playerTransform.position;

        startingPos = transform.position;
        roamPos = GetRoamingPos();

        soundsGO = GameObject.Find("SoundsGameObject");
        s = soundsGO.GetComponent<Sounds>();
    }

    private void Update()
    {
        float distanceToPlayer = 0;
        if (player[0] != null)
        {
            turretRotation();
            playerPos = playerTransform.position;
            distanceToPlayer = Vector3.Distance(transform.position, playerPos);
        }

        switch (state)
        {
            default:
            case State.Roam:

                pathfindingMove.MoveTo(roamPos);
                float reachedPosDistance = 1f;
                if (Vector3.Distance(transform.position, roamPos) <= reachedPosDistance)
                {
                    //reached Roam Position
                    rotating = false;
                    moving = false;
                    roamPos = GetRoamingPos();
                }
                else
                {
                    moving = true;
                }

                float chaseRange = 27f;
                if (player[0] != null)
                {
                    if (distanceToPlayer >= chaseRange)
                    {
                        //within target range
                        state = State.Chase;
                    }
                }
                else
                {
                    state = State.Stop;
                }

                float attackRange = 25f;
                if (player[0] != null)
                {
                    if (distanceToPlayer <= attackRange)
                    {
                        //attack target range
                        enemyShooting.shoot = true;
                    }
                    else
                    {
                        enemyShooting.shoot = false;
                    }
                }

                break;
            case State.Chase:

                float roamRange = 23f;
                attackRange = 25f;

                if (player[0] != null)
                {
                    pathfindingMove.MoveTo(playerPos);

                    if (distanceToPlayer <= attackRange)
                    {
                        //attack target range
                        enemyShooting.shoot = true;
                    }
                    else
                    {
                        enemyShooting.shoot = false;
                    }

                    if (distanceToPlayer <= roamRange)
                    {
                        //start roaming
                        roamPos = GetRoamingPos();
                        state = State.Roam;
                    }
                }
                else
                {
                    rotating = false;
                    moving = false;
                    state = State.Stop;
                }
                break;
            case State.Stop:

                enemyShooting.shoot = false;
                pathfindingMove.MoveTo(startingPos);
                break;
        }

        //handles Animation for the tank tracks
        if (moving | rotating)
        {
            trackStart();
        }
        else
        {
            trackStop();
        }
    }

    private void FixedUpdate()
    {
        rbTank.velocity = transform.up * 0.1f * Time.fixedDeltaTime;
    }

    internal override void turretRotation()
    {
        Vector3 lookDir = playerPos - tankTurret.position;
        float angleZ = Mathf.Atan2(lookDir.y, lookDir.x) * Mathf.Rad2Deg - 90f;
        tankTurret.rotation = Quaternion.Euler(0.0f, 0.0f, angleZ);
    }

    private Vector3 GetRoamingPos()
    {
        bool inBounds = false;
        Vector3 randomDirection = new Vector3();
        float randomNum = UnityEngine.Random.Range(5f, 21f);
        Vector3 randomPosition = new Vector3();

        while (!inBounds)
        {
            randomNum = UnityEngine.Random.Range(7f, 36f);
            randomDirection = GetRandomDir();
            randomPosition = randomDirection * randomNum;
            if ((randomPosition + transform.position).x > 6f && (randomPosition + transform.position).x < 77f && (randomPosition + transform.position).y > 6f && (randomPosition + transform.position).y < 36)
            {
                inBounds = true;
            }
        }

        return transform.position + randomPosition;
    }

    // Generate random normalized direction
    public static Vector3 GetRandomDir()
    {
        return new Vector3(UnityEngine.Random.Range(-1f, 1f), UnityEngine.Random.Range(-1f, 1f)).normalized;
    }
}
