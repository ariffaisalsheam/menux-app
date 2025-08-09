import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { Box } from '@mui/material';
import { Helmet } from 'react-helmet-async';

import { useAuth } from './contexts/AuthContext';
import Layout from './components/Layout/Layout';
import LoadingSpinner from './components/Common/LoadingSpinner';

// Pages
import HomePage from './pages/Home/HomePage';
import LoginPage from './pages/Auth/LoginPage';
import RegisterPage from './pages/Auth/RegisterPage';
import MenuPage from './pages/Menu/MenuPage';
import OrderPage from './pages/Order/OrderPage';
import FeedbackPage from './pages/Feedback/FeedbackPage';

// Restaurant Owner Pages
import RestaurantDashboard from './pages/Restaurant/RestaurantDashboard';
import MenuManagement from './pages/Restaurant/MenuManagement';
import OrderManagement from './pages/Restaurant/OrderManagement';
import FeedbackManagement from './pages/Restaurant/FeedbackManagement';
import QrCodeManagement from './pages/Restaurant/QrCodeManagement';

// Super Admin Pages
import AdminDashboard from './pages/Admin/AdminDashboard';
import UserManagement from './pages/Admin/UserManagement';
import RestaurantManagement from './pages/Admin/RestaurantManagement';
import SystemAnalytics from './pages/Admin/SystemAnalytics';

// Error Pages
import NotFoundPage from './pages/Error/NotFoundPage';
import UnauthorizedPage from './pages/Error/UnauthorizedPage';

/**
 * Protected Route Component
 */
const ProtectedRoute = ({ children, requiredRole = null }) => {
  const { user, isLoading } = useAuth();

  if (isLoading) {
    return <LoadingSpinner />;
  }

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  if (requiredRole && user.role !== requiredRole) {
    return <Navigate to="/unauthorized" replace />;
  }

  return children;
};

/**
 * Public Route Component (redirects authenticated users)
 */
const PublicRoute = ({ children }) => {
  const { user, isLoading } = useAuth();

  if (isLoading) {
    return <LoadingSpinner />;
  }

  if (user) {
    // Redirect based on user role
    if (user.role === 'SUPER_ADMIN') {
      return <Navigate to="/admin" replace />;
    } else if (user.role === 'RESTAURANT_OWNER') {
      return <Navigate to="/restaurant" replace />;
    }
  }

  return children;
};

/**
 * Main App Component
 */
function App() {
  return (
    <Box sx={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>
      <Helmet>
        <title>Menu.X - Digital Restaurant Communication System</title>
        <meta name="description" content="Modern digital solution for restaurant menu viewing, ordering, and feedback collection" />
      </Helmet>

      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<HomePage />} />
        <Route path="/menu/:restaurantId" element={<MenuPage />} />
        <Route path="/order/:restaurantId" element={<OrderPage />} />
        <Route path="/feedback/:restaurantId" element={<FeedbackPage />} />
        
        {/* Authentication Routes */}
        <Route 
          path="/login" 
          element={
            <PublicRoute>
              <LoginPage />
            </PublicRoute>
          } 
        />
        <Route 
          path="/register" 
          element={
            <PublicRoute>
              <RegisterPage />
            </PublicRoute>
          } 
        />

        {/* Restaurant Owner Routes */}
        <Route 
          path="/restaurant" 
          element={
            <ProtectedRoute requiredRole="RESTAURANT_OWNER">
              <Layout userType="restaurant">
                <RestaurantDashboard />
              </Layout>
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/restaurant/menu" 
          element={
            <ProtectedRoute requiredRole="RESTAURANT_OWNER">
              <Layout userType="restaurant">
                <MenuManagement />
              </Layout>
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/restaurant/orders" 
          element={
            <ProtectedRoute requiredRole="RESTAURANT_OWNER">
              <Layout userType="restaurant">
                <OrderManagement />
              </Layout>
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/restaurant/feedback" 
          element={
            <ProtectedRoute requiredRole="RESTAURANT_OWNER">
              <Layout userType="restaurant">
                <FeedbackManagement />
              </Layout>
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/restaurant/qr-codes" 
          element={
            <ProtectedRoute requiredRole="RESTAURANT_OWNER">
              <Layout userType="restaurant">
                <QrCodeManagement />
              </Layout>
            </ProtectedRoute>
          } 
        />

        {/* Super Admin Routes */}
        <Route 
          path="/admin" 
          element={
            <ProtectedRoute requiredRole="SUPER_ADMIN">
              <Layout userType="admin">
                <AdminDashboard />
              </Layout>
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/admin/users" 
          element={
            <ProtectedRoute requiredRole="SUPER_ADMIN">
              <Layout userType="admin">
                <UserManagement />
              </Layout>
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/admin/restaurants" 
          element={
            <ProtectedRoute requiredRole="SUPER_ADMIN">
              <Layout userType="admin">
                <RestaurantManagement />
              </Layout>
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/admin/analytics" 
          element={
            <ProtectedRoute requiredRole="SUPER_ADMIN">
              <Layout userType="admin">
                <SystemAnalytics />
              </Layout>
            </ProtectedRoute>
          } 
        />

        {/* Error Routes */}
        <Route path="/unauthorized" element={<UnauthorizedPage />} />
        <Route path="/404" element={<NotFoundPage />} />
        <Route path="*" element={<Navigate to="/404" replace />} />
      </Routes>
    </Box>
  );
}

export default App;
