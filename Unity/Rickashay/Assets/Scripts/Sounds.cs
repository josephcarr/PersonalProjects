using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Sounds : MonoBehaviour
{
    public AudioSource bounceSound;
    public AudioSource buttonSound;
    public AudioSource explosionSound;
    public AudioSource movingSound;
    public AudioSource pewSound;

    public void Start()
    {
        DontDestroyOnLoad(this.gameObject);
    }

    public void playBounceSound()
    {
        bounceSound.Play();
    }
    public void playButtonSound()
    {
        buttonSound.Play();
    }
    public void playExplosionSound()
    {
        explosionSound.Play();
    }
    public void playMovingSound()
    {
        movingSound.Play();
    }
    public void stopMovingSound()
    {
        movingSound.Stop();
    }
    public void playPewSound()
    {
        pewSound.Play();
    }
}