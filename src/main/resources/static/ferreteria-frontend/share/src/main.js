import React from "react";
import { createRoot } from "react-dom/client";
import App from "./App_login_default";

const root = createRoot(document.getElementById("root"));
root.render(React.createElement(React.StrictMode, null, React.createElement(App)));