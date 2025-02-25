import PropTypes from 'prop-types';
import { useContext, useState } from 'react';
import { Route, BrowserRouter as Router, Routes, useLocation } from 'react-router-dom';
import Dashboard from './components/Dashboard/Dashboard.jsx';
import ProtectedRoute from './components/ProtectedRoute.jsx';
import Login from './components/UserAuth/Login.jsx';
import Signup from './components/UserAuth/Signup.jsx';
import AuthContext from './context/AuthContext.jsx';

function App() {
  const [cartCount] = useState(0);
  const [wishlistCount] = useState(0);
  const { user } = useContext(AuthContext);

  return (
    <Router>
      <MainLayout user={user} cartCount={cartCount} wishlistCount={wishlistCount} />
    </Router>
  );
}

function MainLayout({ user, cartCount, wishlistCount }) {
  const location = useLocation();
  const hideNavbarRoutes = ['/login', '/signup'];

  return (
    <>
      {/* Hide Navbar on Login & Signup pages */}
      {!hideNavbarRoutes.includes(location.pathname) && (
        <Dashboard user={user} cartCount={cartCount} wishlistCount={wishlistCount} />
      )}

      <Routes>
        {/* <Route path="/" element={<Dashboard user={user} cartCount={cartCount} wishlistCount={wishlistCount} />} /> */}
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />

        {/* Protected Routes */}
        <Route element={<ProtectedRoute />}>
          {/* <Route path="/dashboard" element={<Dashboard user={user} cartCount={cartCount} wishlistCount={wishlistCount} />} /> */}
        </Route>
      </Routes>
    </>
  );
}

MainLayout.propTypes = {
  user: PropTypes.object,
  cartCount: PropTypes.number.isRequired,
  wishlistCount: PropTypes.number.isRequired,
};

export default App;
