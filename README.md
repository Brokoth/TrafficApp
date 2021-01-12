# Traffic Light Sequencing Application

This repository contains a java based android application that simulates traffic flow at a junction.
With the use of the random forest machine learning model, the application predicts the best light to turn green at a junction.
The machine learning model is written in python and implemented on the frontend with Chaquopy. The application also uses
firebase as the backend.

The python code can be found under [traffic_light_python_script.py](https://github.com/Brokoth/TrafficApp/blob/main/app/src/main/python/traffic_light_python_script.py) file,
the datasets used to train the model can be located at [this](https://github.com/Brokoth/TrafficAppData/) repository and the Chaquopy implementation under the run() method of the [ai_traffic_sequencing_thread.java](https://github.com/Brokoth/TrafficApp/blob/main/app/src/main/java/com/example/trafficapp/ai_traffic_sequencing_thread.java) file.

Development of the appllcation will continue once the Chaquopy company offers me an Open-source license.

## HomeActivity
![HomeActiviy.png](https://github.com/Brokoth/TrafficApp/blob/main/HomeActivity.PNG)
## SimulationActivity
![SimulationActivityy.png](https://github.com/Brokoth/TrafficApp/blob/main/SimulationActivity.PNG)
