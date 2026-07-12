import { Routes, Route } from "react-router-dom";

import Register from "./pages/Register";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import AdminRoute from "./components/AdminRoute";
import AdminVehicles from "./pages/AdminVehicles";

export default function App() {
  return (
      <Routes>
        <Route
            path="/"
            element={<Dashboard />}
        />

        <Route
            path="/login"
            element={<Login />}
        />

        <Route
            path="/register"
            element={<Register />}
        />

          <Route
              path="/admin"
              element={
                  <AdminRoute>
                      <AdminVehicles />
                  </AdminRoute>
              }
          />
      </Routes>
  );
}