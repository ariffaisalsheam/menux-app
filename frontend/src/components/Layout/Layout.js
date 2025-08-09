import React from 'react';
import { Box, AppBar, Toolbar, Typography, Button, Avatar, Menu, MenuItem } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';

const Layout = ({ children, userType }) => {
  const navigate = useNavigate();
  const { user, logout } = useAuth();
  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = async () => {
    await logout();
    navigate('/');
  };

  const getNavigationItems = () => {
    if (userType === 'admin') {
      return [
        { label: 'Dashboard', path: '/admin' },
        { label: 'Users', path: '/admin/users' },
        { label: 'Restaurants', path: '/admin/restaurants' },
        { label: 'Analytics', path: '/admin/analytics' },
      ];
    } else if (userType === 'restaurant') {
      return [
        { label: 'Dashboard', path: '/restaurant' },
        { label: 'Menu', path: '/restaurant/menu' },
        { label: 'Orders', path: '/restaurant/orders' },
        { label: 'Feedback', path: '/restaurant/feedback' },
        { label: 'QR Codes', path: '/restaurant/qr-codes' },
      ];
    }
    return [];
  };

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Menu.X {userType === 'admin' ? '- Admin Panel' : '- Restaurant Dashboard'}
          </Typography>
          
          {/* Navigation Items */}
          <Box sx={{ display: { xs: 'none', md: 'flex' }, gap: 2, mr: 2 }}>
            {getNavigationItems().map((item) => (
              <Button
                key={item.path}
                color="inherit"
                onClick={() => navigate(item.path)}
              >
                {item.label}
              </Button>
            ))}
          </Box>

          {/* User Menu */}
          <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
            <Typography variant="body2">
              {user?.firstName} {user?.lastName}
            </Typography>
            <Avatar
              sx={{ cursor: 'pointer' }}
              onClick={handleMenuOpen}
            >
              {user?.firstName?.charAt(0)}
            </Avatar>
          </Box>

          <Menu
            anchorEl={anchorEl}
            open={Boolean(anchorEl)}
            onClose={handleMenuClose}
          >
            <MenuItem onClick={handleMenuClose}>Profile</MenuItem>
            <MenuItem onClick={handleMenuClose}>Settings</MenuItem>
            <MenuItem onClick={handleLogout}>Logout</MenuItem>
          </Menu>
        </Toolbar>
      </AppBar>

      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        {children}
      </Box>
    </Box>
  );
};

export default Layout;
