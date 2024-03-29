# -*- coding: utf-8 -*-
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn import metrics
import pickle
import requests
def main():
    roundabout_training_data = pd.read_csv(
         'https://github.com/Brokoth/TrafficAppData/blob/main/TrainingDataset%20-%20RoundaboutDatasetThreeRandMultipleGreens.csv?raw=true')
    roundabout_independent_variables = roundabout_training_data.drop(
        ['LightOneColour', 'LightTwoColour', 'LightThreeColour', 'LightFourColour'],
        axis='columns')
    roundabout_target = roundabout_training_data[
        ['LightOneColour', 'LightTwoColour', 'LightThreeColour', 'LightFourColour']].copy()
    roundabout_independent_variables_train, roundabout_independent_variables_test, roundabout_target_train, roundabout_target_test = train_test_split(
        roundabout_independent_variables,
        roundabout_target, test_size=0.3,
        random_state=1)

    t_junction_training_data = pd.read_csv(
        'https://github.com/Brokoth/TrafficAppData/blob/main/TrainingDataset%20-%20TjunctionDatasetThreeRandMultipleGreens.csv?raw=true')
    t_junction_independent_variables = t_junction_training_data.drop(
        ['LightOneColour', 'LightTwoColour', 'LightThreeColour'],
        axis='columns')
    t_junction_target = t_junction_training_data[
        ['LightOneColour', 'LightTwoColour', 'LightThreeColour']].copy()
    t_junction_independent_variables_train, t_junction_independent_variables_test, t_junction_target_train, t_junction_target_test = train_test_split(
        t_junction_independent_variables,
        t_junction_target, test_size=0.3,
        random_state=1)

    global rounda_model
    rounda_model = RandomForestClassifier(n_estimators=100)
    rounda_model.fit(roundabout_independent_variables_train, roundabout_target_train)
    target_prediction = rounda_model.predict(roundabout_independent_variables_test)
    print("Roundabout Random Forest Accuracy:", metrics.accuracy_score(roundabout_target_test, target_prediction))
    # pickle.dump(rounda_model, open('rounda_model.sav', 'wb'))
    global tjun_model
    tjun_model = RandomForestClassifier(n_estimators=100)
    tjun_model.fit(t_junction_independent_variables_train, t_junction_target_train)
    t_junction_target_prediction = tjun_model.predict(t_junction_independent_variables_test)
    print("T-junction Random Forest Accuracy:", metrics.accuracy_score(t_junction_target_test, t_junction_target_prediction))
    # pickle.dump(tjun_model, open('tjun_model.sav', 'wb'))


def roundabout(lane_1_density, lane_2_density, lane_3_density, lane_4_density):
    # rounda_model_url = \
    #     "https://github.com/Brokoth/TrafficAppData/blob/main/rounda_model.sav?raw=true"
    # rounda_model_file = requests.get(rounda_model_url).content
    # with open(initial_path + '/rounda_model.sav', 'wb') as file:
    #     file.write(rounda_model_file)
    # rounda_model = pickle.load(open(initial_path + '/rounda_model.sav', 'rb'))
    roundabout_target_prediction = rounda_model.predict(
        [[lane_1_density, lane_2_density, lane_3_density, lane_4_density]])
    print(roundabout_target_prediction)
    return roundabout_target_prediction


def t_junction(lane_1_density, lane_2_density, lane_3_density):
    # tjun_model_url = \
    #     "https://github.com/Brokoth/TrafficAppData/blob/main/tjun_model.sav?raw=true"
    # tjun_model_file = requests.get(tjun_model_url).content
    # with open(initial_path + '/tjun_model.sav', 'wb') as file:
    #     file.write(tjun_model_file)
    # tjun_model = pickle.load(open(initial_path + '/tjun_model.sav', 'rb'))
    tjun_target_prediction = tjun_model.predict([[lane_1_density, lane_2_density, lane_3_density]])
    print(tjun_target_prediction)
    return tjun_target_prediction