import { initializeApp, getApps  } from "firebase/app";
import { getFirestore } from "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyC53AKgcfA6on_A_9_Iv-ZD6YwMks49GKA",
  authDomain: "ferreteria-productos-13202.firebaseapp.com",
  projectId: "ferreteria-productos-13202",
  storageBucket: "ferreteria-productos-13202.appspot.com",
  messagingSenderId: "769317091982",
  appId: "1:769317091982:web:d22e4efdaea38437e927df"
};


const app = getApps().length === 0 ? initializeApp(firebaseConfig) : getApps()[0];

const db = getFirestore(app);

export { db };

