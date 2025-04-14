import React from 'react';
import HomePage from './pages/Home';
import MapPage from './pages/MapPage';
import AdminDashboard from './pages/Admin/Dashboard';
import AdminLogin from './pages/Admin/Login';
import UserLogin from './pages/User/Login';
import UserSettings from './pages/User/Settings';
import Register from './pages/Register';
import SchedulePage from './pages/Schedule/SchedulePage';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { isAuthenticated, isAdmin } from './services/auth';

// 受保护的路由组件
function ProtectedRoute({ children }) {
  if (!isAuthenticated()) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

// 管理员路由组件
function AdminRoute({ children }) {
  if (!isAuthenticated() || !isAdmin()) {
    return <Navigate to="/admin/login" replace />;
  }
  return children;
}

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<UserLogin />} />
        <Route path="/admin/login" element={<AdminLogin />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/" element={<Navigate to="/home" />} />
        <Route path="/map" element={<MapPage />} />
        <Route path="/admin" element={<AdminDashboard />} />
        <Route path="/settings" element={<UserSettings />} />
        <Route path="/register" element={<Register />} />
        <Route path="/schedule" element={<SchedulePage />} />
        <Route path="*" element={<Navigate to="/home" />} />
      </Routes>
    </Router>
  );
}

export default App;