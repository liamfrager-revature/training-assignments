import './App.css';
import Hello from './Hello.js'
import EventDemo from './EventDemo.js';
import StateDemo from './StateDemo.js';
import Post from './Post.js';

function App() {
  const post = {
    content: "This is my post content",
    comments: ["Hahaha!", "So funny!", "I love this!"]
  }
  return (
    <div className="App">
      <header className="App-header">
        <Hello messageProp="Hello from props!" />
        <hr />
        <EventDemo />
        <hr />
        <StateDemo />
        <Post post={post} />
      </header>
    </div>
  );
}

export default App;
