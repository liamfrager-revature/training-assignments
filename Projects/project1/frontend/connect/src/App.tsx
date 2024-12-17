import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomeRoute from './routes/HomeRoute';
import ProfileRoute from './routes/ProfileRoute';
import PostRoute from './routes/PostRoute';
import RegisterRoute from './routes/RegisterRoute';
import LoginRoute from './routes/LoginRoute';
import FriendsRoute from './routes/FriendsRoute';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/home" element={<HomeRoute />} />
        <Route path="/profile/:userID" element={<ProfileRoute />} />
        <Route path="/post/:postID" element={<PostRoute />} />
        <Route path="/friends" element={<FriendsRoute/>} />
        <Route path="/register" element={<RegisterRoute />} />
        <Route path="/login" element={<LoginRoute />} />
      </Routes>
    </Router>
  );
}

export default App;
