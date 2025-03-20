import { BrowserRouter, Routes, Route } from 'react-router-dom';

// import './App.css'  // Using tailwind, so no need for this
import TestPage from './pages/TestPage';

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<TestPage />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
