import { Routes, Route } from "react-router-dom";
import Login from "../pages/Login";
import Connection from "../pages/Connection";

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/connexion" element={<Connection />} />
    </Routes>
  );
}

export default AppRoutes;
