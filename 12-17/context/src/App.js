import './App.css';
import AlertButton from './components/AlertButton';
import Counter from './components/Counter';
import TextComponent from './components/TextComponent';
import UpdatingText from './components/UpdatingText';
import Context from './Context';

function App() {
  const sharedData = "This is some shared data.";
  return (
    <>
      <Context.Provider value={sharedData}>
        <TextComponent /><br />
        <AlertButton /><br /><br />
        <UpdatingText /><br />
        <Counter />
      </Context.Provider>
    </>
  );
}

export default App;
