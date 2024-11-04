import React, { useState } from 'react';
import { View, Text, StyleSheet, TextInput, TouchableOpacity, Alert } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

export default function SearchClient({ navigation }) {
  const [mobileNumber, setMobileNumber] = useState('');
  const [clientDetails, setClientDetails] = useState(null);

  const searchClient = async () => {
    try {
      const existingAppointments = await AsyncStorage.getItem('appointments');
      console.log('Existing Appointments:', existingAppointments);
  
      const parsedAppointments = existingAppointments ? JSON.parse(existingAppointments) : [];
      console.log('Parsed Appointments:', parsedAppointments);
  
      const client = parsedAppointments.find((c) => {
        console.log(typeof c.mobileNumber, typeof mobileNumber);
        return c.mobileNumber.toString() === mobileNumber.toString();
      });
  
      console.log('Found Client:', client);
  
      if (client) {
        setClientDetails(client);
      } else {
        setClientDetails(null);
        Alert.alert('Error', 'Client not found.');
      }
    } catch (error) {
      console.error('Error searching client:', error);
    }
  };
  
  
  
  

  return (
    <View style={styles.container}>
      <Text style={styles.label}>Enter Mobile Number:</Text>
      <TextInput
        style={styles.textInput}
        placeholder="Enter mobile number"
        keyboardType="phone-pad"
        value={mobileNumber}
        onChangeText={setMobileNumber}
      />

      <TouchableOpacity style={styles.button} onPress={searchClient}>
        <Text style={styles.buttonText}>Search Client</Text>
      </TouchableOpacity>

      {clientDetails && (
        <View style={styles.clientDetailsContainer}>
          <Text style={styles.clientDetailsText}>Client Name: {clientDetails.name}</Text>
          <Text style={styles.clientDetailsText}>Client Surname: {clientDetails.surname}</Text>
          <Text style={styles.clientDetailsText}>Mobile Number: {clientDetails.mobileNumber}</Text>
          <Text style={styles.clientDetailsText}>Location: {clientDetails.location}</Text>
          <Text style={styles.clientDetailsText}>Date: {clientDetails.appointmentDate}</Text>
          {/* Add more details as needed */}
        </View>
      )}
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
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 4,
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
  clientDetailsContainer: {
    marginTop: 20,
    borderColor: 'white',
    borderWidth: 1,
    padding: 10,
    borderRadius: 8,
  },
  clientDetailsText: {
    color: 'white',
    fontSize: 16,
  },
});
