import React from 'react';
import { Navigate } from 'react-router-dom';
import { isAuthenticated } from '../services/auth';

// 导入页面组件
import Login from '../pages/Login';
import Home from '../pages/Home';
import Dashboard from '../pages/Dashboard';
import ConferenceList from '../pages/ConferenceList';
import ConferenceDetail from '../pages/ConferenceDetail';
import ConferenceForm from '../pages/ConferenceForm';
import Profile from '../pages/Profile';
import MainLayout from '../layouts/MainLayout';

// 路由守卫组件
const PrivateRoute = ({ children }) => {
  return isAuthenticated() ? children : <Navigate to="/login" />;
};

// 路由配置
export const routes = [
  {
    path: '/login',
    element: <Login />,
  },
  {
    path: '/',
    element: (
      <PrivateRoute>
        <MainLayout>
          <Home />
        </MainLayout>
      </PrivateRoute>
    ),
  },
  {
    path: '/dashboard',
    element: (
      <PrivateRoute>
        <MainLayout>
          <Dashboard />
        </MainLayout>
      </PrivateRoute>
    ),
  },
  {
    path: '/app',
    element: (
      <PrivateRoute>
        <MainLayout />
      </PrivateRoute>
    ),
    children: [
      {
        path: 'conferences',
        element: <ConferenceList />,
      },
      {
        path: 'conferences/:id',
        element: <ConferenceDetail />,
      },
      {
        path: 'conferences/create',
        element: <ConferenceForm />,
      },
      {
        path: 'conferences/:id/edit',
        element: <ConferenceForm />,
      },
      {
        path: 'profile',
        element: <Profile />,
      },
    ],
  },
  {
    path: '*',
    element: <Navigate to="/" />,
  },
]; 