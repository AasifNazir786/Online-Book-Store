import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css';
import Login from './components/Auth/Login';
import Signup from './components/Auth/Signup';

function App() {
  

    return (
      <Router>
        <Routes>

        <Route path="/" element={<h1>Welcome to My App</h1>} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          {/* <Route element={<ProtectedRoute />}> */}
            {/* <Route path="/" element={<Dashboard />}> */}
              {/* Add nested routes for dashboard components */}
            {/* </Route> */}
          {/* </Route> */}
        </Routes>
      </Router>
    );
}

export default App
