using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyPathfinding : MonoBehaviour
{
    private float moveAcceleration = 4f;
    private float moveDeceleration = 20f;
    private float moveSpeedMax = 200f;

    private float rotateAcceleration = 80f;
    private float rotateDeceleration = 200f;
    private float rotateSpeedMax = 5200f;

    private List<Vector3> pathVectorList;
    private int currentPathIndex;
    private float pathfindingTimer;
    private Vector3 moveDir;
    private Vector3 lastMoveDir;

    private Transform tankBase;
    private Rigidbody2D rbTank;

    bool rotating;
    bool moving;

    float moveSpeed = 0;
    float rotateSpeed = 0;

    private void Start()
    {
        rbTank = GetComponent<Rigidbody2D>();
        tankBase = transform.Find("Base");
    }

    // Update is called once per frame
    private void Update()
    {
        pathfindingTimer -= Time.deltaTime;

        if (rotating)
        {
            rotateSpeed = (rotateSpeed < rotateSpeedMax) ? rotateSpeed + rotateAcceleration : rotateSpeedMax;
        }
        else
        {
            rotateSpeed = (rotateSpeed > 0) ? rotateSpeed - rotateDeceleration : 0;
        }

        if (moving)
        {
            moveSpeed = (moveSpeed < moveSpeedMax) ? moveSpeed + moveAcceleration : moveSpeedMax;
        }
        else
        {
            moveSpeed = (moveSpeed > 0) ? moveSpeed - moveDeceleration : 0;
        }
    }

    private void FixedUpdate()
    {
        HandleMovement();
    }

    private void HandleMovement()
    {
        if (pathVectorList != null)
        {
            Vector3 targetPosition = pathVectorList[currentPathIndex];
            if (Vector3.Distance(transform.position, targetPosition) > 1f)
            {
                faceTarget(targetPosition);
                moving = true;
                rbTank.velocity = transform.up * moveSpeed * Time.fixedDeltaTime;
            }
            else
            {
                currentPathIndex++;
                if (currentPathIndex >= pathVectorList.Count)
                {
                    StopMoving();
                }
            }
        }
    }

    /// <summary>
    /// Rotates the tank body to face the player tank
    /// </summary>
    private void faceTarget(Vector3 targetPosition)
    {
        Vector3 targetDirection = targetPosition - transform.position;
        if (!isFacing(targetDirection.normalized))
        {
            rotating = true;
            Quaternion desiredRotation = Quaternion.LookRotation(Vector3.forward, targetDirection);
            desiredRotation = Quaternion.Euler(0, 0, desiredRotation.eulerAngles.z);
            rbTank.MoveRotation(Quaternion.RotateTowards(tankBase.rotation, desiredRotation, rotateSpeed / 15 * Time.deltaTime));
        }
        else
        {
            rotating = false;
        }
    }

    private bool isFacing(Vector3 targetDir)
    {
        float dot = Vector3.Dot(targetDir, transform.forward);

        if (dot >= 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void StopMoving()
    {
        pathVectorList = null;
        rbTank.angularVelocity = 0f;
        rbTank.velocity = Vector3.zero;
        moving = false;
        rotating = false;
    }

    public List<Vector3> GetPathVectorList()
    {
        return pathVectorList;
    }

    public void MoveTo(Vector3 targetPosition)
    {
        SetTargetPosition(targetPosition);
    }

    public void SetTargetPosition(Vector3 targetPosition)
    {
        currentPathIndex = 0;
        pathVectorList = Pathfinding.Instance.FindVectorPath(GetPos(), targetPosition);
        pathfindingTimer = 2f;

        if (pathVectorList != null && pathVectorList.Count > 1)
        {
            pathVectorList.RemoveAt(0);
        }
    }

    public Vector3 GetPos()
    {
        return transform.position;
    }
}
