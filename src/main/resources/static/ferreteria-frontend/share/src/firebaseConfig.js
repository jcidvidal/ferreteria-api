import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth"; 

const firebaseConfig = {
  apiKey: "AIzaSyDfSMFu0G1P2Iq8nIGhxdfRfg5b3sHC-Fk",
  authDomain: "ferreteria-v1.firebaseapp.com",
  databaseURL: "https://ferreteria-v1-default-rtdb.firebaseio.com",
  projectId: "ferreteria-v1",
  storageBucket: "ferreteria-v1.firebasestorage.app",
  messagingSenderId: "57028218029",
  appId: "1:57028218029:web:e06979664152c4c98a0a24",
  measurementId: "G-5CCJ2B0TXZ"
};

// Inicializa Firebase
const app = initializeApp(firebaseConfig);

// Inicializa Auth y EXPORTA
export const auth = getAuth(app); 
export default app;
