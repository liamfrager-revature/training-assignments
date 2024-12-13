import './App.css';
import Hello from './Hello.js'

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <Hello messageProp="Hello from props!" />
      </header>
    </div>
  );
}

export default App;
