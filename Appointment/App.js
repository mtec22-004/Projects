import { React, useState } from 'react';
import { StyleSheet, Text, View, TextInput, TouchableOpacity } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import Dashboard from './Screens/Dashboard';
import Add_Appointment from './Screens/Add_Appointment';
import Past_Appointments from './Screens/Past_Appointments';
import UpdateDetails from './Screens/UpdateDetails';
import SearchClient from './Screens/SearchClient';
import ClientAppointment from './Screens/ClientAppointment';
import ClientAppointmentsList from './Screens/ClientAppointmentsList';

const Stack = createStackNavigator();
export default function App() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const[loggedIn, setLoggedIn] = useState(false);
  const handleLogin = () => {
    if (username === 'Robby' && password === 'him') {
      setLoggedIn(true)

    } else {
      alert ('try again')
    }
  };
  const handleLogout = () => {
    setLoggedIn(false);
  };
  return (
    <NavigationContainer>
        <Stack.Navigator
       
        >
        <Stack.Screen name="Login"
        options={{ headerShown: false, }}>
          {() =>
            loggedIn ? (
              <Dashboard username={username} onLogout={handleLogout} />
            ) : (

    <View style={styles.container}>
      
      <View>
        <Text style={{fontSize: 25, fontWeight: "bold", marginBottom: 15, color: "white"}}>Login</Text>
      </View>
      
      <TextInput style={styles.textInput} placeholder='Username' placeholderTextColor="white" onChangeText={(text) => setUsername(text)} />
      
      <TextInput style={styles.textInput} placeholder='Password'  placeholderTextColor="white" secureTextEntry onChangeText={(text) => setPassword(text)} />
      

      <TouchableOpacity style={styles.button}
        onPress={handleLogin}
        >
        <Text style = {{fontSize: 18, fontWeight: "600", marginTop: 10, textAlign: 'center', color: "white",}}>Login</Text>
        </TouchableOpacity> 
    </View>
            )
}
</Stack.Screen>
<Stack.Screen
name="AddAppointment"
component={Add_Appointment}
options={{
  headerStyle:{backgroundColor:'#19144c'},
  headerTitleStyle:{
    color:"white"
  },
  headerTintColor: "white"
}}
/>
<Stack.Screen
name="PastAppointment"
component={Past_Appointments}
options={{
  headerStyle:{backgroundColor:'#19144c'},
  headerTitleStyle:{
    color:"white"
  },
  headerTintColor: "white"
}}
/>
<Stack.Screen
name="UpdateAppointment"
component={UpdateDetails}
options={{
  headerStyle:{backgroundColor:'#19144c'},
  headerTitleStyle:{
    color:"white"
  },
  headerTintColor: "white"
}}
/>
<Stack.Screen
name="SearchClient"
component={SearchClient}
options={{
  headerStyle:{backgroundColor:'#19144c'},
  headerTitleStyle:{
    color:"white"
  },
  headerTintColor: "white"
}}
/>
<Stack.Screen
name="ClientAppointment"
component={ClientAppointment}
options={{
  headerStyle:{backgroundColor:'#19144c'},
  headerTitleStyle:{
    color:"white"
  },
  headerTintColor: "white"
}}
/>
<Stack.Screen
name="ClientAppointmentsList"
component={ClientAppointmentsList}
options={{
  headerStyle:{backgroundColor:'#19144c'},
  headerTitleStyle:{
    color:"white"
  },
  headerTintColor: "white"
}}
/>
<Stack.Screen
name="ClientAppoint"
component={ClientAppointmentsList}
options={{
  headerStyle:{backgroundColor:'#19144c'},
  headerTitleStyle:{
    color:"white"
  },
  headerTintColor: "white"
}}
/>

      </Stack.Navigator>
    </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#19144c',
   padding: 50,
   justifyContent: "center"
  },
  textInput: {
    borderWidth: 1,
    borderColor: '#671913', 
    padding : 8,
    borderRadius: 10,
    marginBottom: 20,
    color: "white"
    
  }, 
  label: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 4,
    color: 'black'
  },
  button: {
    
    backgroundColor: "#671913",
    marginTop: 10,
    width: "100%",
    paddingVertical: 5,
    borderRadius: 10
    
  }
});
