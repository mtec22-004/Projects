import React, { useState, useEffect } from "react";
import { View, Text, StyleSheet, FlatList, TouchableOpacity, Modal, TextInput, Button } from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import Icon from "react-native-vector-icons/FontAwesome";

const PastAppointments = () => {
  const [appointments, setAppointments] = useState([]);
  const [selectedAppointmentIndex, setSelectedAppointmentIndex] = useState(null);
  const [newDate, setNewDate] = useState("");
  const [newLocation, setNewLocation] = useState("");
  const [isPostponedModalVisible, setPostponedModalVisible] = useState(false);
  const [newName, setNewName] = useState("");
  const [newSurname, setNewSurname] = useState("");
  const [newMobileNumber, setNewMobileNumber] = useState("");
  const [isUpdateClientInfoModalVisible, setUpdateClientInfoModalVisible] = useState(false);

  const handleFlagAppointment = async (index, action) => {
    if (action === "postponed") {
      setSelectedAppointmentIndex(index);
      setPostponedModalVisible(true);
    } else if (action === "updateClientInfo") {
      handleUpdateClientInfo(index);
    } else {
      updateAppointmentStatus(index, action);
    }
  };

  const updateAppointmentStatus = async (index, action) => {
    const updatedAppointments = [...appointments];
    updatedAppointments[index].status = action;

    if (action === "postponed") {
      updatedAppointments[index].appointmentDate = newDate;
      updatedAppointments[index].location = newLocation;
    }

    setAppointments(updatedAppointments);

    try {
      await AsyncStorage.setItem("appointments", JSON.stringify(updatedAppointments));
    } catch (error) {
      console.error("Error updating appointments:", error);
    }

    setPostponedModalVisible(false);
    setUpdateClientInfoModalVisible(false);
  };

  const handleUpdateClientInfo = (index) => {
    setSelectedAppointmentIndex(index);
    setUpdateClientInfoModalVisible(true);
  };

  const updateClientInfo = async (index) => {
    const updatedAppointments = [...appointments];
    updatedAppointments[index].name = newName;
    updatedAppointments[index].surname = newSurname;
    updatedAppointments[index].mobileNumber = newMobileNumber;

    setAppointments(updatedAppointments);

    try {
      await AsyncStorage.setItem("appointments", JSON.stringify(updatedAppointments));
    } catch (error) {
      console.error("Error updating appointments:", error);
    }

    setUpdateClientInfoModalVisible(false);
  };

  const handleDeleteAppointment = (index) => {
    const updatedAppointments = [...appointments];
    updatedAppointments.splice(index, 1);

    setAppointments(updatedAppointments);

    try {
      AsyncStorage.setItem("appointments", JSON.stringify(updatedAppointments));
    } catch (error) {
      console.error("Error deleting appointment:", error);
    }
  };

  useEffect(() => {
    const fetchAppointments = async () => {
      try {
        const storedAppointments = await AsyncStorage.getItem("appointments");
        if (storedAppointments) {
          const parsedAppointments = JSON.parse(storedAppointments);

          if (Array.isArray(parsedAppointments)) {
            parsedAppointments.sort(
              (a, b) => new Date(b.appointmentDate) - new Date(a.appointmentDate)
            );
            setAppointments(parsedAppointments);
          } else {
            console.error(
              "Stored appointments is not an array. Data:",
              parsedAppointments
            );
          }
        }
      } catch (error) {
        console.error("Error fetching appointments:", error);
      }
    };

    fetchAppointments();
  }, []);

  return {
    appointments,
    handleFlagAppointment,
    newDate,
    setNewDate,
    newLocation,
    setNewLocation,
    isPostponedModalVisible,
    setPostponedModalVisible,
    isUpdateClientInfoModalVisible,
    setUpdateClientInfoModalVisible,
    updateAppointmentStatus,
    selectedAppointmentIndex,
    newName,
    setNewName,
    newSurname,
    setNewSurname,
    newMobileNumber,
    setNewMobileNumber,
    handleUpdateClientInfo,
    updateClientInfo,
    handleDeleteAppointment,
  };
};

export default function Past_Appointments() {
  const {
    appointments,
    handleFlagAppointment,
    newDate,
    setNewDate,
    newLocation,
    setNewLocation,
    isPostponedModalVisible,
    setPostponedModalVisible,
    isUpdateClientInfoModalVisible,
    setUpdateClientInfoModalVisible,
    updateAppointmentStatus,
    selectedAppointmentIndex,
    newName,
    setNewName,
    newSurname,
    setNewSurname,
    newMobileNumber,
    setNewMobileNumber,
    handleUpdateClientInfo,
    updateClientInfo,
    handleDeleteAppointment,
  } = PastAppointments();

  return (
    <View style={styles.container}>
      {appointments.length === 0 ? (
        <Text style={styles.noAppointmentsText}>No past appointments found.</Text>
      ) : (
        <>
          <FlatList
            data={appointments}
            keyExtractor={(item) => item.appointmentDate}
            renderItem={({ item, index }) => (
              <View style={styles.appointmentItem}>
                <Text style={styles.appointmentText}>{`Client: ${item.name} ${item.surname}`}</Text>
                <Text style={styles.appointmentText}>{`Mobile Number: ${item.mobileNumber}`}</Text>
                <Text style={styles.appointmentText}>{`Location: ${item.location}`}</Text>
                <Text style={styles.appointmentText}>{`Appointment Date: ${item.appointmentDate}`}</Text>
                <Text style={styles.appointmentText}>{`Status: ${item.status || "Not specified"}`}</Text>
                <Text style={styles.appointmentText}>{`Honored by Agent: ${item.honoredByAgent ? "Yes" : "No"}`}</Text>
                <View style={styles.iconContainer}>
                  <TouchableOpacity
                    onPress={() => handleFlagAppointment(index, "cancelled")}
                    style={styles.iconButton}
                  >
                    <Icon name="times" size={20} color="white" />
                  </TouchableOpacity>
                  <TouchableOpacity
                    onPress={() => handleFlagAppointment(index, "postponed")}
                    style={styles.iconButton}
                  >
                    <Icon name="calendar-plus-o" size={20} color="white" />
                  </TouchableOpacity>
                  <TouchableOpacity
                    onPress={() => handleFlagAppointment(index, "honored")}
                    style={styles.iconButton}
                  >
                    <Icon name="check" size={20} color="white" />
                  </TouchableOpacity>
                  <TouchableOpacity
                    onPress={() => handleFlagAppointment(index, "updateClientInfo")}
                    style={styles.iconButton}
                  >
                    <Icon name="pencil" size={20} color="white" />
                  </TouchableOpacity>
                  <TouchableOpacity
                    onPress={() => handleDeleteAppointment(index)}
                    style={styles.iconButton}
                  >
                    <Icon name="trash" size={20} color="white" />
                  </TouchableOpacity>
                </View>
              </View>
            )}
          />
          {/* Postponed Modal */}
          <Modal
            visible={isPostponedModalVisible}
            animationType="slide"
            transparent={true}
          >
            <View style={styles.modalContainer}>
              <View style={styles.modalContent}>
                <Text style={styles.modalTitle}>Enter new date and location</Text>
                <TextInput
                  style={styles.input}
                  placeholder="New Date"
                  value={newDate}
                  onChangeText={(text) => setNewDate(text)}
                />
                <TextInput
                  style={styles.input}
                  placeholder="New Location"
                  value={newLocation}
                  onChangeText={(text) => setNewLocation(text)}
                />
                <Button
                  title="Update"
                  onPress={() => updateAppointmentStatus(selectedAppointmentIndex, "postponed")}
                />
                <Button
                  title="Cancel"
                  onPress={() => setPostponedModalVisible(false)}
                />
              </View>
            </View>
          </Modal>
          {/* Update Client Info Modal */}
          <Modal
            visible={isUpdateClientInfoModalVisible}
            animationType="slide"
            transparent={true}
          >
            <View style={styles.modalContainer}>
              <View style={styles.modalContent}>
                <Text style={styles.modalTitle}>Update Client Information</Text>
                <TextInput
                  style={styles.input}
                  placeholder="New Name"
                  value={newName}
                  onChangeText={(text) => setNewName(text)}
                />
                <TextInput
                  style={styles.input}
                  placeholder="New Surname"
                  value={newSurname}
                  onChangeText={(text) => setNewSurname(text)}
                />
                <TextInput
                  style={styles.input}
                  placeholder="New Mobile Number"
                  value={newMobileNumber}
                  onChangeText={(text) => setNewMobileNumber(text)}
                />
                <Button
                  title="Update"
                  onPress={() => updateClientInfo(selectedAppointmentIndex)}
                />
                <Button
                  title="Cancel"
                  onPress={() => setUpdateClientInfoModalVisible(false)}
                />
              </View>
            </View>
          </Modal>
        </>
      )}
    </View>
  );
}


const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: "#19144c",
  },
  noAppointmentsText: {
    fontSize: 18,
    fontWeight: "bold",
    textAlign: "center",
    marginTop: 20,
  },
  appointmentItem: {
    backgroundColor: "#130444",
    borderRadius: 8,
    padding: 15,
    marginBottom: 15,
    shadowColor: "#130444",
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
    color: "white",
  },
  iconContainer: {
    flexDirection: "row",
    justifyContent: "space-around",
    marginTop: 10,
  },
  iconButton: {
    padding: 10,
    borderRadius: 5,
    backgroundColor: "#671913",
  },
  modalContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  modalContent: {
    backgroundColor: "white",
    padding: 20,
    borderRadius: 10,
    elevation: 5,
  },
  modalTitle: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 10,
  },
  input: {
    height: 40,
    borderColor: "gray",
    borderWidth: 1,
    marginBottom: 10,
    paddingHorizontal: 10,
  },
});
