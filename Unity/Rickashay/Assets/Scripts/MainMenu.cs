using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MainMenu : MonoBehaviour
{
    GameObject soundsGO;
    Sounds s;

    private void Start()
    {
        soundsGO = GameObject.Find("SoundsGameObject");
        s = soundsGO.GetComponent<Sounds>();
    }

    private void Update()
    {
        s.stopMovingSound();
    }

    public void PlayGame()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex + 1);
        Debug.Log("Scene Count: " + SceneManager.sceneCountInBuildSettings);
    }
    public void QuitGame()
    {
        Debug.Log("QUIT");
        Application.Quit();
    }
    public void Menu()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex - 1);
        Debug.Log("Scene Count: " + SceneManager.sceneCountInBuildSettings);
    }
    
}
