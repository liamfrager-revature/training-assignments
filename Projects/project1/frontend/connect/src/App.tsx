import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomeRoute from './routes/HomeRoute';
import ProfileRoute from './routes/ProfileRoute';
import PostRoute from './routes/PostRoute';
import RegisterRoute from './routes/RegisterRoute';
import LoginRoute from './routes/LoginRoute';
import FollowingRoute from './routes/FollowingRoute';
import ProtectedRoute from './utils/ProtectedRoute';
import { UserContextProvider } from './utils/Context';
import LogoutRoute from './routes/LogoutRoute';
import SearchRoute from './routes/SearchRoute';

function App() {
  return (
    <>
      <UserContextProvider>
        <Router>
          <Routes>
            <Route element={<ProtectedRoute />}>
              <Route path="/home" element={<HomeRoute />} />
              <Route path="/profile" element={<ProfileRoute />} />
              <Route path="/profile/:userID" element={<ProfileRoute />} />
              <Route path="/post/:postID" element={<PostRoute />} />
              <Route path="/following" element={<FollowingRoute/>} />
              <Route path="/search" element={<SearchRoute/>} />
            </Route>
            <Route path="/register" element={<RegisterRoute />} />
            <Route path="/login" element={<LoginRoute />} />
            <Route path="/logout" element={<LogoutRoute />} />
          </Routes>
        </Router>
      </UserContextProvider>
    </>
  );
}

export default App;
