// src/firebaseConfig.js
import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";

const firebaseConfig = {
  apiKey: "...",
  authDomain: "...",
  projectId: "...",
  storageBucket: "...",
  messagingSenderId: "...",
  appId: "..."
};
// Estos datos los sacas desde la consola de Firebase

const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

export { db };
