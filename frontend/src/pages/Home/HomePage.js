import React from 'react';
import {
  Box,
  Container,
  Typography,
  Button,
  Grid,
  Card,
  CardContent,
  CardActions,
  AppBar,
  Toolbar,
} from '@mui/material';
import {
  QrCode,
  Restaurant,
  Feedback,
  Analytics,
  Phone,
  Speed,
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { Helmet } from 'react-helmet-async';

const HomePage = () => {
  const navigate = useNavigate();

  const features = [
    {
      icon: <QrCode sx={{ fontSize: 40, color: 'primary.main' }} />,
      title: 'QR Code Menu Access',
      description: 'Customers can scan QR codes to instantly access digital menus without downloading apps.',
    },
    {
      icon: <Restaurant sx={{ fontSize: 40, color: 'primary.main' }} />,
      title: 'Digital Ordering',
      description: 'Pro restaurants can accept orders directly through the digital menu interface.',
    },
    {
      icon: <Feedback sx={{ fontSize: 40, color: 'primary.main' }} />,
      title: 'Customer Feedback',
      description: 'Collect and analyze customer feedback with AI-powered sentiment analysis.',
    },
    {
      icon: <Analytics sx={{ fontSize: 40, color: 'primary.main' }} />,
      title: 'Real-time Analytics',
      description: 'Get insights into customer preferences, popular items, and business performance.',
    },
    {
      icon: <Phone sx={{ fontSize: 40, color: 'primary.main' }} />,
      title: 'Contactless Experience',
      description: 'Reduce physical contact while maintaining excellent customer service.',
    },
    {
      icon: <Speed sx={{ fontSize: 40, color: 'primary.main' }} />,
      title: 'Fast & Efficient',
      description: 'Streamline operations and reduce wait times for better customer satisfaction.',
    },
  ];

  return (
    <>
      <Helmet>
        <title>Menu.X - Digital Restaurant Communication System</title>
        <meta name="description" content="Transform your restaurant with Menu.X - the modern digital solution for menu viewing, ordering, and customer feedback." />
      </Helmet>

      {/* Header */}
      <AppBar position="static" color="transparent" elevation={0}>
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1, fontWeight: 'bold' }}>
            Menu.X
          </Typography>
          <Button color="primary" onClick={() => navigate('/login')}>
            Login
          </Button>
          <Button variant="contained" sx={{ ml: 1 }} onClick={() => navigate('/register')}>
            Get Started
          </Button>
        </Toolbar>
      </AppBar>

      {/* Hero Section */}
      <Box
        sx={{
          background: 'linear-gradient(135deg, #1976d2 0%, #42a5f5 100%)',
          color: 'white',
          py: 8,
          textAlign: 'center',
        }}
      >
        <Container maxWidth="md">
          <Typography variant="h2" component="h1" gutterBottom fontWeight="bold">
            Transform Your Restaurant Experience
          </Typography>
          <Typography variant="h5" component="p" sx={{ mb: 4, opacity: 0.9 }}>
            Menu.X provides a complete digital solution for modern restaurants - 
            from QR code menus to AI-powered analytics
          </Typography>
          <Box sx={{ display: 'flex', gap: 2, justifyContent: 'center', flexWrap: 'wrap' }}>
            <Button
              variant="contained"
              size="large"
              sx={{ 
                bgcolor: 'white', 
                color: 'primary.main',
                '&:hover': { bgcolor: 'grey.100' }
              }}
              onClick={() => navigate('/register')}
            >
              Start Free Trial
            </Button>
            <Button
              variant="outlined"
              size="large"
              sx={{ 
                borderColor: 'white', 
                color: 'white',
                '&:hover': { borderColor: 'white', bgcolor: 'rgba(255,255,255,0.1)' }
              }}
            >
              Watch Demo
            </Button>
          </Box>
        </Container>
      </Box>

      {/* Features Section */}
      <Container maxWidth="lg" sx={{ py: 8 }}>
        <Typography variant="h3" component="h2" textAlign="center" gutterBottom>
          Why Choose Menu.X?
        </Typography>
        <Typography variant="h6" textAlign="center" color="text.secondary" sx={{ mb: 6 }}>
          Everything you need to modernize your restaurant operations
        </Typography>

        <Grid container spacing={4}>
          {features.map((feature, index) => (
            <Grid item xs={12} md={6} lg={4} key={index}>
              <Card sx={{ height: '100%', textAlign: 'center', p: 2 }}>
                <CardContent>
                  <Box sx={{ mb: 2 }}>
                    {feature.icon}
                  </Box>
                  <Typography variant="h6" component="h3" gutterBottom>
                    {feature.title}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {feature.description}
                  </Typography>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Container>

      {/* Pricing Section */}
      <Box sx={{ bgcolor: 'grey.50', py: 8 }}>
        <Container maxWidth="md">
          <Typography variant="h3" component="h2" textAlign="center" gutterBottom>
            Simple Pricing
          </Typography>
          <Typography variant="h6" textAlign="center" color="text.secondary" sx={{ mb: 6 }}>
            Choose the plan that fits your restaurant's needs
          </Typography>

          <Grid container spacing={4} justifyContent="center">
            <Grid item xs={12} md={6}>
              <Card>
                <CardContent sx={{ textAlign: 'center', p: 4 }}>
                  <Typography variant="h4" component="h3" gutterBottom>
                    Free
                  </Typography>
                  <Typography variant="h2" component="div" color="primary.main" gutterBottom>
                    $0
                    <Typography variant="h6" component="span" color="text.secondary">
                      /month
                    </Typography>
                  </Typography>
                  <Typography variant="body1" sx={{ mb: 3 }}>
                    Perfect for getting started
                  </Typography>
                  <Box sx={{ textAlign: 'left', mb: 3 }}>
                    <Typography variant="body2">✓ QR Code Menu Access</Typography>
                    <Typography variant="body2">✓ Basic Menu Management</Typography>
                    <Typography variant="body2">✓ Customer Feedback Collection</Typography>
                    <Typography variant="body2">✓ Basic Analytics</Typography>
                  </Box>
                </CardContent>
                <CardActions sx={{ justifyContent: 'center', pb: 3 }}>
                  <Button variant="outlined" size="large" onClick={() => navigate('/register')}>
                    Get Started Free
                  </Button>
                </CardActions>
              </Card>
            </Grid>

            <Grid item xs={12} md={6}>
              <Card sx={{ border: 2, borderColor: 'primary.main' }}>
                <CardContent sx={{ textAlign: 'center', p: 4 }}>
                  <Typography variant="h4" component="h3" gutterBottom>
                    Pro
                  </Typography>
                  <Typography variant="h2" component="div" color="primary.main" gutterBottom>
                    $29
                    <Typography variant="h6" component="span" color="text.secondary">
                      /month
                    </Typography>
                  </Typography>
                  <Typography variant="body1" sx={{ mb: 3 }}>
                    For growing restaurants
                  </Typography>
                  <Box sx={{ textAlign: 'left', mb: 3 }}>
                    <Typography variant="body2">✓ Everything in Free</Typography>
                    <Typography variant="body2">✓ Digital Ordering System</Typography>
                    <Typography variant="body2">✓ Live Order Management</Typography>
                    <Typography variant="body2">✓ AI Menu Descriptions</Typography>
                    <Typography variant="body2">✓ Advanced Analytics</Typography>
                    <Typography variant="body2">✓ Sentiment Analysis</Typography>
                  </Box>
                </CardContent>
                <CardActions sx={{ justifyContent: 'center', pb: 3 }}>
                  <Button variant="contained" size="large" onClick={() => navigate('/register')}>
                    Start Pro Trial
                  </Button>
                </CardActions>
              </Card>
            </Grid>
          </Grid>
        </Container>
      </Box>

      {/* Footer */}
      <Box sx={{ bgcolor: 'grey.900', color: 'white', py: 4 }}>
        <Container maxWidth="lg">
          <Grid container spacing={4}>
            <Grid item xs={12} md={6}>
              <Typography variant="h6" gutterBottom>
                Menu.X
              </Typography>
              <Typography variant="body2" color="grey.400">
                Transforming restaurant experiences through digital innovation.
              </Typography>
            </Grid>
            <Grid item xs={12} md={6} sx={{ textAlign: { xs: 'left', md: 'right' } }}>
              <Typography variant="body2" color="grey.400">
                © 2024 Menu.X. All rights reserved.
              </Typography>
            </Grid>
          </Grid>
        </Container>
      </Box>
    </>
  );
};

export default HomePage;
