import React, { useState } from 'react';
import { StyleSheet, Text, View, TextInput, TouchableOpacity } from 'react-native';
import DateTimePicker from '@react-native-community/datetimepicker';
import AsyncStorage from '@react-native-async-storage/async-storage';

export default function Add_Appointment({ navigation }) {
  const [clientName, setClientName] = useState('');
  const [clientSurname, setClientSurname] = useState('');
  const [mobileNumber, setMobileNumber] = useState('');
  const [location, setLocation] = useState('');
  const [appointmentDate, setAppointmentDate] = useState(new Date());
  const [showDatePicker, setShowDatePicker] = useState(false);

  const handleAppointments = async () => {
    // Validation checks
    if (!clientName || !clientSurname || !mobileNumber || !location) {
      alert('Please fill in all the fields');
      return;
    }

    // Additional validation for mobile number format (you can customize this according to your needs)
    const mobileNumberRegex = /^\d+$/;
    if (!mobileNumberRegex.test(mobileNumber)) {
      alert('Please enter a valid mobile number');
      return;
    }

    // Assuming you have a customer object like this:
    const customer = {
      name: clientName,
      surname: clientSurname,
      mobileNumber: mobileNumber,
      location: location,
      appointmentDate: appointmentDate.toLocaleString(),
      status: 'Pending',
    };

    try {
      // Retrieve existing appointments or initialize as an empty array
      const existingAppointments = await AsyncStorage.getItem('appointments');
      let parsedAppointments = existingAppointments
        ? JSON.parse(existingAppointments)
        : [];

      // Ensure that parsedAppointments is an array
      if (!Array.isArray(parsedAppointments)) {
        parsedAppointments = [];
      }

      // Append the new customer details to the array
      parsedAppointments.push(customer);

      // Store the updated array back in AsyncStorage
      await AsyncStorage.setItem('appointments', JSON.stringify(parsedAppointments));

      // Clear the form fields after storing the data
      setClientName('');
      setClientSurname('');
      setMobileNumber('');
      setLocation('');

      // Navigate back to the past appointments screen after storing the data
      navigation.goBack();

      alert('Customer details added successfully');
    } catch (error) {
      console.error('Error saving customer details:', error);
    }
  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.textInput}
        placeholder="Enter client name"
        placeholderTextColor="white"
        value={clientName}
        onChangeText={setClientName}
      />

      <TextInput
        style={styles.textInput}
        placeholder="Enter client surname"
        placeholderTextColor="white"
        value={clientSurname}
        onChangeText={setClientSurname}
      />

      <TextInput
        style={styles.textInput}
        placeholder="Enter mobile number"
        placeholderTextColor="white"
        keyboardType='numeric' // Use 'numeric' keyboard type
        value={mobileNumber}
        onChangeText={(text) => setMobileNumber(text)}
      />

      <TextInput
        style={styles.textInput}
        placeholder="Enter location"
        placeholderTextColor="white"
        value={location}
        onChangeText={setLocation}
      />

      <Text style={styles.label}>Appointment Date:</Text>
      <TouchableOpacity style={{ color: "white" }} onPress={() => setShowDatePicker(true)}>
        <Text style={{ color: "white", marginTop: 5, marginBottom: 20 }}>{appointmentDate.toLocaleString()}</Text>
      </TouchableOpacity>

      {showDatePicker && (
        <DateTimePicker
          value={appointmentDate}
          mode="date"
          onChange={(_event, date) => {
            setShowDatePicker(false);
            if (date) {
              setAppointmentDate(date);
            }
          }}
        />
      )}

      <TouchableOpacity style={styles.button} onPress={handleAppointments}>
        <Text style={styles.buttonText}>Submit</Text>
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
  title: {
    fontSize: 25,
    color: 'white',
    marginBottom: 20,
    textAlign: 'center',
  },
  textInput: {
    borderWidth: 1,
    borderColor: '#671913',
    padding: 10,
    borderRadius: 8,
    marginBottom: 15,
    color: 'white',
  },
  label: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 4,
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
