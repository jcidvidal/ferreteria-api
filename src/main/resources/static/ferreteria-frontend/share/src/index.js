import React from "react";
import ReactDOM from "react-dom/client";
import App_login_default from "./App_login_default";
import { BrowserRouter } from "react-router-dom"; // <-- Importa esto

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <BrowserRouter>
    <App_login_default />
  </BrowserRouter>
);
