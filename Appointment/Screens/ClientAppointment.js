// ClientAppointment.js

import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

const ClientAppointment = ({ navigation }) => {
  const [clientNames, setClientNames] = useState([]);

  useEffect(() => {
    const fetchClientNames = async () => {
      try {
        const existingAppointments = await AsyncStorage.getItem('appointments');
        const parsedAppointments = existingAppointments ? JSON.parse(existingAppointments) : [];

        // Extract unique client names from past appointments
        const uniqueClientNames = [...new Set(parsedAppointments.map(appointment => appointment.name))];
        setClientNames(uniqueClientNames);
      } catch (error) {
        console.error('Error fetching client names:', error);
      }
    };

    fetchClientNames();
  }, []);

  const handleClientSelection = (clientName) => {
    // Navigate to a screen showing all appointments for the selected client
    navigation.navigate('ClientAppointmentsList', { clientName });
  };

  return (
    <View style={styles.container}>
      <Text style={styles.heading}>Client Appointments</Text>

      {clientNames.length === 0 ? (
        <Text style={styles.noAppointmentsText}>No client names found.</Text>
      ) : (
        <FlatList
          data={clientNames}
          keyExtractor={(item) => item}
          renderItem={({ item }) => (
            <TouchableOpacity
              style={styles.clientItem}
              onPress={() => handleClientSelection(item)}
            >
              <Text style={styles.clientText}>{item}</Text>
            </TouchableOpacity>
          )}
        />
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#19144c',
  },
  heading: {
    fontSize: 24,
    fontWeight: 'bold',
    color: 'white',
    marginBottom: 10,
  },
  noAppointmentsText: {
    fontSize: 18,
    fontWeight: 'bold',
    textAlign: 'center',
    marginTop: 20,
    color: 'white',
  },
  clientItem: {
    backgroundColor: '#130444',
    borderRadius: 8,
    padding: 15,
    marginBottom: 15,
    shadowColor: '#130444',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
    elevation: 10,
  },
  clientText: {
    fontSize: 16,
    marginBottom: 8,
    color: 'white',
  },
});

export default ClientAppointment;
