/*==============================================================================
Copyright 2017 Maxst, Inc. All Rights Reserved.
==============================================================================*/

using UnityEngine;
using System;
using System.Collections.Generic;
using System.Text;
using System.Runtime.InteropServices;
using UnityEngine.UI;
using UnityEngine.EventSystems;

using maxstAR;

public class InstantTrackerSample : ARBehaviour
{
    [SerializeField]
    private EventSystem eventSystem;

    [SerializeField]
    private Text startBtnText = null;

    [SerializeField]
    private InstantTrackableBehaviour pannel;

    private bool startTrackerDone = false;
    private bool findSurfaceDone = false;

    private List<InstantTrackableBehaviour> instantTrackables = new List<InstantTrackableBehaviour>();

    private List<Vector3> touchToWorldPositions = new List<Vector3>();
    private List<Vector3> touchSumPositions = new List<Vector3>();

    private int id = -1;

    private CameraBackgroundBehaviour cameraBackgroundBehaviour = null;


    void Awake()
    {
		Init();
        cameraBackgroundBehaviour = FindObjectOfType<CameraBackgroundBehaviour>();
        if (cameraBackgroundBehaviour == null)
        {
            Debug.LogError("Can't find CameraBackgroundBehaviour.");
            return;
        }
    }

    void Start()
    {
        pannel = Resources.Load<InstantTrackableBehaviour>("solarPannel");
        instantTrackables.Clear();

        //InstantTrackableBehaviour[] trackables = FindObjectsOfType<InstantTrackableBehaviour>();
        //foreach (var trackable in trackables)
        //{
        //    instantTrackables.Add(trackable);
        //    touchToWorldPositions.Add(new Vector3(0.0f, 0.0f, 0.0f));
        //    touchSumPositions.Add(new Vector3(0.0f, 0.0f, 0.0f));
        //}
    }
    void Update()
    {
        StartCamera();
    
        if (!startTrackerDone)
        {
            TrackerManager.GetInstance().StartTracker(TrackerManager.TRACKER_TYPE_INSTANT);
            SensorDevice.GetInstance().Start();
            startTrackerDone = true;
        }

        TrackingState state = TrackerManager.GetInstance().UpdateTrackingState();
        TrackingResult trackingResult = state.GetTrackingResult();
        cameraBackgroundBehaviour.UpdateCameraBackgroundImage(state);

        if (trackingResult.GetCount() == 0)
        {
            foreach (var trackable in instantTrackables)
            {
                trackable.OnTrackFail();
            }
            return;
        }

#if UNITY_EDITOR
        if (id < 0) return;
        if (Input.GetMouseButton(0))
        {
            PointerEventData eventData = new PointerEventData(EventSystem.current);
            eventData.position = Input.mousePosition;
            List<RaycastResult> raycastResults = new List<RaycastResult>();
            EventSystem.current.RaycastAll(eventData, raycastResults);
            if (raycastResults.Count > 0)
            {
                for (int j = 0; j < raycastResults.Count; j++)
                {
                    Button btn = raycastResults[j].gameObject.GetComponent<Button>();
                    if (btn == null)
                    {
                        return;
                    }
                }
            }

            touchSumPositions[id] = TrackerManager.GetInstance().GetWorldPositionFromScreenCoordinate(Input.mousePosition);
        }
#else
        if (id < 0) return;
        if (Input.touchCount > 0)
        {
            PointerEventData eventData = new PointerEventData(EventSystem.current);
            eventData.position = Input.GetTouch(0).position;
            List<RaycastResult> raycastResults = new List<RaycastResult>();
            EventSystem.current.RaycastAll(eventData, raycastResults);
            if (raycastResults.Count > 0)
            {
                for (int j = 0; j < raycastResults.Count; j++)
                {
                    Button btn = raycastResults[j].gameObject.GetComponent<Button>();
                    if (btn == null)
                    {
                        return;
                    }
                }
            }

            UpdateTouchPositionDelta(id);
        }
#endif

        for (int i = 0; i < instantTrackables.Count; i++)
        {
            Trackable track = trackingResult.GetTrackable(0);
            Matrix4x4 poseMatrix = track.GetPose() * Matrix4x4.Translate(touchSumPositions[i]);
            instantTrackables[i].OnTrackSuccess(track.GetId(), track.GetName(), poseMatrix);
        }
    }

    private void UpdateTouchPositionDelta(int id)
    {
        switch (Input.GetTouch(0).phase)
        {
            case TouchPhase.Began:
                touchToWorldPositions[id] = TrackerManager.GetInstance().GetWorldPositionFromScreenCoordinate(Input.GetTouch(0).position);
                break;

            case TouchPhase.Moved:
                Vector3 currentWorldPosition = TrackerManager.GetInstance().GetWorldPositionFromScreenCoordinate(Input.GetTouch(0).position);
                touchSumPositions[id] += (currentWorldPosition - touchToWorldPositions[id]);
                touchToWorldPositions[id] = currentWorldPosition;
                break;
        }
    }

    public void ClickNumber1()
    {
        id++;
        InstantTrackableBehaviour trackable = (InstantTrackableBehaviour)Instantiate(pannel, Vector3.zero, Quaternion.identity);
        trackable.OnTrackFail();

        instantTrackables.Add(trackable);
        touchToWorldPositions.Add(new Vector3(0.0f, 0.0f, 0.0f));
        touchSumPositions.Add(new Vector3(0.0f, 0.0f, 0.0f));

        Debug.Log(instantTrackables.Count);

    }

    public void ClickNumber2()
    {
        if (id >= 0) {
            InstantTrackableBehaviour.Destroy(instantTrackables[id]);
            instantTrackables[id].MyDestroy();
            instantTrackables.Remove(instantTrackables[id]);
            id--;
        }
        Debug.Log(instantTrackables.Count);

    }

    public void ClickNumber3()
    {
        Application.Quit();
    }

    void OnApplicationPause(bool pause)
    {
        if (pause)
        {
            SensorDevice.GetInstance().Stop();
            TrackerManager.GetInstance().StopTracker();
            startTrackerDone = false;
            StopCamera();
        }
    }

    void OnDestroy()
    {
        SensorDevice.GetInstance().Stop();
        TrackerManager.GetInstance().StopTracker();
        TrackerManager.GetInstance().DestroyTracker();
        StopCamera();
    }

    public void OnClickStart()
    {
        if (!findSurfaceDone)
        {
            TrackerManager.GetInstance().FindSurface();
            if (startBtnText != null)
            {
                startBtnText.text = "Stop Tracking";
            }
            findSurfaceDone = true;
            for (int i = 0; i < touchSumPositions.Count; i++)
            {
                touchSumPositions[i] = Vector3.zero;
            }
        }
        else
        {
            TrackerManager.GetInstance().QuitFindingSurface();
            if (startBtnText != null)
            {
                startBtnText.text = "Start Tracking";
            }
            findSurfaceDone = false;
        }
    }
}