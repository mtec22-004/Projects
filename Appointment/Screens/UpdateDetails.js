import React, { useState } from 'react';
import { View, Text, StyleSheet, TextInput, TouchableOpacity, Alert } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

export default function UpdateDetails({ route, navigation }) {
  const { appointmentDetails } = route.params;
  const [newClientName, setNewClientName] = useState(appointmentDetails.name);
  const [newClientSurname, setNewClientSurname] = useState(appointmentDetails.surname);
  const [newMobileNumber, setNewMobileNumber] = useState(appointmentDetails.mobileNumber);
  const [newLocation, setNewLocation] = useState(appointmentDetails.location);

  const handleUpdateDetails = async () => {
    try {
      // Validation checks
      if (!newClientName || !newClientSurname || !newMobileNumber || !newLocation) {
        Alert.alert('Validation Error', 'Please fill in all the fields');
        return;
      }

      const mobileNumberRegex = /^\d+$/;
      if (!mobileNumberRegex.test(newMobileNumber)) {
        Alert.alert('Validation Error', 'Please enter a valid mobile number');
        return;
      }

      const storedAppointments = await AsyncStorage.getItem('appointments');
      if (storedAppointments) {
        const parsedAppointments = JSON.parse(storedAppointments);

        const isDuplicate = parsedAppointments.some(
          (app) =>
            app.appointmentId !== appointmentDetails.appointmentId &&
            app.name === newClientName &&
            app.surname === newClientSurname &&
            app.mobileNumber === newMobileNumber
        );

        if (isDuplicate) {
          Alert.alert(
            'Duplicate Error',
            'Client details already captured for another appointment. Please check and try again.'
          );
          return;
        }

        const updatedAppointments = parsedAppointments.map((app) => {
          if (app.appointmentId === appointmentDetails.appointmentId) {
            return {
              ...app,
              name: newClientName,
              surname: newClientSurname,
              mobileNumber: newMobileNumber,
              location: newLocation,
            };
          } else {
            return app;
          }
        });

        await AsyncStorage.setItem('appointments', JSON.stringify(updatedAppointments));

        Alert.alert('Success', 'Appointment details updated successfully');
      
      }
    } catch (error) {
      console.error('Error updating appointment details:', error);
      // Handle the error, e.g., display an error message to the user
      Alert.alert('Error', 'Error updating appointment details. Please try again.');
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.label}>Update Client Details</Text>
      <TextInput
        style={styles.textInput}
        placeholder="Enter client name"
        value={newClientName}
        onChangeText={setNewClientName}
      />
      <TextInput
        style={styles.textInput}
        placeholder="Enter client surname"
        value={newClientSurname}
        onChangeText={setNewClientSurname}
      />
      <TextInput
        style={styles.textInput}
        placeholder="Enter mobile number"
        keyboardType="numeric"
        value={newMobileNumber}
        onChangeText={setNewMobileNumber}
      />
      <TextInput
        style={styles.textInput}
        placeholder="Enter location"
        value={newLocation}
        onChangeText={setNewLocation}
      />
      <TouchableOpacity style={styles.button} onPress={handleUpdateDetails}>
        <Text style={styles.buttonText}>Update Details</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#19144c',
    padding: 20,
    justifyContent: 'center',
  },
  label: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 20,
    color: 'white',
  },
  textInput: {
    borderWidth: 1,
    borderColor: '#671913',
    padding: 10,
    borderRadius: 8,
    marginBottom: 15,
    color: 'white',
  },
  button: {
    backgroundColor: '#671913',
    borderRadius: 8,
    padding: 15,
  },
  buttonText: {
    fontSize: 18,
    fontWeight: '600',
    color: 'white',
    textAlign: 'center',
  },
});
