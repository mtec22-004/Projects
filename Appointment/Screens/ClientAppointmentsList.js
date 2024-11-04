// ClientAppointmentsList.js

import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, FlatList } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

const ClientAppointmentsList = ({ route }) => {
  const { clientName } = route.params;
  const [clientAppointments, setClientAppointments] = useState([]);

  useEffect(() => {
    const fetchClientAppointments = async () => {
      try {
        const existingAppointments = await AsyncStorage.getItem('appointments');
        const parsedAppointments = existingAppointments ? JSON.parse(existingAppointments) : [];

        // Filter appointments for the selected client
        const appointmentsForClient = parsedAppointments.filter(appointment => appointment.name === clientName);
        setClientAppointments(appointmentsForClient);
      } catch (error) {
        console.error('Error fetching client appointments:', error);
      }
    };

    fetchClientAppointments();
  }, [clientName]);

  return (
    <View style={styles.container}>
      <Text style={styles.heading}>{`Appointments for ${clientName}`}</Text>

      {clientAppointments.length === 0 ? (
        <Text style={styles.noAppointmentsText}>No appointments found for this client.</Text>
      ) : (
        <FlatList
          data={clientAppointments}
          keyExtractor={(item) => item.appointmentDate}
          renderItem={({ item }) => (
            <View style={styles.appointmentItem}>
              <Text style={styles.appointmentText}>{`Date: ${item.appointmentDate}`}</Text>
              <Text style={styles.appointmentText}>{`Location: ${item.location}`}</Text>
              {/* Add more details if needed */}
            </View>
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
  appointmentItem: {
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
  appointmentText: {
    fontSize: 16,
    marginBottom: 8,
    color: 'white',
  },
});

export default ClientAppointmentsList;
