import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import ForecastPage from "./pages/ForecastPage";
import LoginPage from "./pages/LoginPage.tsx"; // Not sure why these threw an error without the .tsx extension
import SignupPage from "./pages/SignupPage.tsx";
import { authService } from "./services/authService";

// Protected route component
const ProtectedRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  if (!authService.isValidAuthToken()) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
};

const App: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route 
          path="/forecast" 
          element={
            <ProtectedRoute>
              <ForecastPage />
            </ProtectedRoute>
          } 
        />
        <Route path="/" element={<Navigate to="/forecast" replace />} />
        <Route path="*" element={<Navigate to="/forecast" replace />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;



// import { BrowserRouter, Routes, Route } from 'react-router-dom';

// // import './App.css'  // Using tailwind, so no need for this
// import TestPage from './pages/TestPage';

// function App() {
//   return (
//     <div>
//       <BrowserRouter>
//         <Routes>
//           <Route path="/" element={<TestPage />} />
//         </Routes>
//       </BrowserRouter>
//     </div>
//   );
// }

// export default App;
