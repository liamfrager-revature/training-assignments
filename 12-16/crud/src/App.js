import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Hello from './routes/Hello';
import Todo from './routes/Todo';
import Posts from './routes/Posts';


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/hello" element={<Hello />} />
        <Route path="/todo" element={<Todo />} />
        <Route path="/posts" element={<Posts />} />
      </Routes>
    </Router>
  );
}

export default App;
