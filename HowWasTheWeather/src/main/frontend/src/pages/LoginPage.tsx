import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import LoginForm from "../components/auth/LoginForm";
import { authService } from "../services/authService";

const LoginPage: React.FC = () => {
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
          How Was The Weather?
        </h2>
      </div>

      <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
        <LoginForm />
      </div>
    </div>
  );
};

export default LoginPage;
