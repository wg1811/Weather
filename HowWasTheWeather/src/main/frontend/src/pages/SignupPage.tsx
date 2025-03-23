import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import SignupForm from "../components/auth/SignupForm";
import { authService } from "../services/authService";

const SignupPage: React.FC = () => {
  const navigate = useNavigate();
  
  // Redirect if already authenticated
  useEffect(() => {
    if (authService.isValidAuthToken()) {
      navigate("/forecast");
    }
  }, [navigate]);
  
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col justify-center">
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
          Create Your Weather App Account
        </h2>
      </div>
      
      <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
        <SignupForm />
      </div>
    </div>
  );
};

export default SignupPage;