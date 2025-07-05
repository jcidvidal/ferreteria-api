import { Routes, Route } from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import AppCliente from "./App_cliente";


function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/Login" element={<Login />} />
      <Route path="/Register" element={<Register />} />
      <Route path="/cliente/*" element={<AppCliente />} /> {AppCliente} 
    </Routes>
  );
}

export default App;
