// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getAuth } from "firebase/auth";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyDfSMFu0G1P2Iq8nIGhxdfRfg5b3sHC-Fk",
  authDomain: "ferreteria-v1.firebaseapp.com",
  projectId: "ferreteria-v1",
  storageBucket: "ferreteria-v1.firebasestorage.app",
  messagingSenderId: "57028218029",
  appId: "1:57028218029:web:e06979664152c4c98a0a24",
  measurementId: "G-5CCJ2B0TXZ"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
export const auth = getAuth(app);