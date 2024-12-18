import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomeRoute from './routes/HomeRoute';
import ProfileRoute from './routes/ProfileRoute';
import PostRoute from './routes/PostRoute';
import RegisterRoute from './routes/RegisterRoute';
import LoginRoute from './routes/LoginRoute';
import FriendsRoute from './routes/FriendsRoute';
import ProtectedRoute from './utils/ProtectedRoute';
import { UserContextProvider } from './utils/Context';

function App() {
  return (
    <>
      <UserContextProvider>
        <Router>
          <Routes>
            <Route element={<ProtectedRoute />}>
              <Route path="/home" element={<HomeRoute />} />
              <Route path="/profile/:userID" element={<ProfileRoute />} />
              <Route path="/post/:postID" element={<PostRoute />} />
              <Route path="/friends" element={<FriendsRoute/>} />
            </Route>
            <Route path="/register" element={<RegisterRoute />} />
            <Route path="/login" element={<LoginRoute />} />
          </Routes>
        </Router>
      </UserContextProvider>
    </>
  );
}

export default App;
