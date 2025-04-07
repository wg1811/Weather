// Need to put change password and delete account here (favoerites is handled in UI)

// // ProfilePage.tsx
// import React, { useState, useEffect } from "react";
// import { useNavigate } from "react-router-dom";
// import { weatherService } from "../services/weatherService";
// import { authService } from "../services/authService";

// interface Favorite {
//   id: string;
//   name: string;
//   latitude: number;
//   longitude: number;
// }

// const ProfilePage: React.FC = () => {
//   const [favorites, setFavorites] = useState<Favorite[]>([]);
//   const [oldPassword, setOldPassword] = useState("");
//   const [newPassword, setNewPassword] = useState("");
//   const [error, setError] = useState("");
//   const [success, setSuccess] = useState("");
//   const navigate = useNavigate();

//   useEffect(() => {
//     const fetchFavorites = async () => {
//       const data = await weatherService.getFavorites();
//       setFavorites(data);
//     };
//     fetchFavorites();
//   }, []);

//   const handleDeleteFavorite = async (id: string) => {
//     try {
//       await weatherService.deleteFavorite(id); // Assume this exists
//       setFavorites(favorites.filter((f) => f.id !== id));
//       setSuccess("Favorite removed.");
//     } catch (err) {
//       setError("Failed to remove favorite.");
//     }
//   };

//   const handlePasswordChange = async (e: React.FormEvent) => {
//     e.preventDefault();
//     try {
//       await authService.changePassword({ oldPassword, newPassword }); // Assume this exists
//       setSuccess("Password updated successfully.");
//       setOldPassword("");
//       setNewPassword("");
//     } catch (err) {
//       setError("Failed to update password.");
//     }
//   };

//   return (
//     <div className="container mx-auto p-4 max-w-4xl">
//       <h1 className="text-3xl font-bold text-blue-500 mb-6">Profile</h1>

//       {/* Favorites Section */}
//       <div className="mb-8">
//         <h2 className="text-xl font-semibold mb-4">Favorite Locations</h2>
//         {favorites.length === 0 ? (
//           <p className="text-gray-600">No favorites yet.</p>
//         ) : (
//           <ul className="space-y-2">
//             {favorites.map((fav) => (
//               <li
//                 key={fav.id}
//                 className="flex justify-between items-center p-2 bg-gray-100 rounded"
//               >
//                 <span>{fav.name}</span>
//                 <button
//                   onClick={() => handleDeleteFavorite(fav.id)}
//                   className="text-red-500 hover:text-red-700"
//                 >
//                   Delete
//                 </button>
//               </li>
//             ))}
//           </ul>
//         )}
//       </div>

//       {/* Password Change Section */}
//       <div>
//         <h2 className="text-xl font-semibold mb-4">Change Password</h2>
//         <form onSubmit={handlePasswordChange} className="space-y-4">
//           <input
//             type="password"
//             value={oldPassword}
//             onChange={(e) => setOldPassword(e.target.value)}
//             placeholder="Old Password"
//             className="w-full p-2 border rounded"
//           />
//           <input
//             type="password"
//             value={newPassword}
//             onChange={(e) => setNewPassword(e.target.value)}
//             placeholder="New Password"
//             className="w-full p-2 border rounded"
//           />
//           <button type="submit" className="bg-blue-500 text-white p-2 rounded">
//             Update Password
//           </button>
//         </form>
//         {error && <p className="text-red-500 mt-2">{error}</p>}
//         {success && <p className="text-green-500 mt-2">{success}</p>}
//       </div>
//     </div>
//   );
// };

// export default ProfilePage;
