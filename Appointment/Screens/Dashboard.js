import React from "react";
import { View, Text, StyleSheet, TouchableOpacity, ScrollView } from "react-native";
import Icon from "react-native-vector-icons/FontAwesome"; 
import { useNavigation } from '@react-navigation/native';

export default function ({ username, onLogout }) {
  const navigation = useNavigation();
  const Appointments = () => {
    navigation.navigate('AddAppointment');
  };
  const Past = () => {
    navigation.navigate('PastAppointment')
  };

 
  const Search = () => {
    navigation.navigate('SearchClient');
  };
  const Client = () => {
    navigation.navigate('ClientAppointment');
  }

  return (
    <View style={styles.container}>
      <Text style={styles.welcomeText}>Welcome {username}!!</Text>
      <ScrollView contentContainerStyle={styles.scrollContainer}>
        <TouchableOpacity style={styles.featureContainer} onPress={Appointments}>
          <View style={styles.iconBackground}>
            <Icon name="plus-circle" size={50} color="white" />
          </View>
          <Text style={styles.featureText}>Add Appointment</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.featureContainer} onPress={Past}>
          <View style={styles.iconBackground}>
            <Icon name="history" size={50} color="white" />
          </View>
          <Text style={styles.featureText}>Past Appointments</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.featureContainer} onPress={Search}>
          <View style={styles.iconBackground}>
            <Icon name="search" size={50} color="white" />
          </View>
          <Text style={styles.featureText}>Search Clients</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.featureContainer} onPress={Client}>
          <View style={styles.iconBackground}>
            <Icon name="list" size={50} color="white" />
          </View>
          <Text style={styles.featureText}>ClientAppointment</Text>
        </TouchableOpacity>

        <TouchableOpacity style={{ margin: 20 }} onPress={onLogout}>
          <Icon name="sign-out" size={50} color="white" />
          <Text style={{ color: 'white' }}>Sign-out</Text>
        </TouchableOpacity>
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#19144c",
    alignItems: "center",
    paddingTop: 20,
  },
  scrollContainer: {
    flexGrow: 1,
    alignItems: "center",
    paddingTop: 20,
  },
  welcomeText: {
    color: "white",
    fontWeight: "bold",
    fontSize: 24,
    marginTop: 20,
    marginBottom: 20,
    fontWeight: "bold",
  },
  featureContainer: {
    alignItems: "center",
    marginBottom: 20,
  },
  iconBackground: {
    backgroundColor: "#671913",
    padding: 20,
    borderRadius: 50,
  },
  featureText: {
    color: "white",
    fontSize: 18,
    marginTop: 10,
  },
});
