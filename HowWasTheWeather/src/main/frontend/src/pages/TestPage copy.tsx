// <!DOCTYPE html>
// <html lang="en">
//     <head>
//         <meta charset="UTF-8" />
//         <meta name="viewport" content="width=device-width, initial-scale=1.0" />
//         <title>My Webcrumbs Plugin</title>
//         <style>
//             @import url(https://fonts.googleapis.com/css2?family=Lato&display=swap);
//             @import url(https://fonts.googleapis.com/css2?family=Open+Sans&display=swap);
//             @import url(https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200);
//         </style>
//     </head>
//     <body>
//         <div id="webcrumbs">
//             <div class="w-[380px] bg-gradient-to-br from-blue-100 to-blue-50 rounded-xl shadow-xl p-6 font-sans">
//                 <div class="flex justify-between items-center mb-6">
//                     <div>
//                         <h2 class="text-2xl font-bold text-blue-800">San Francisco</h2>
//                         <p class="text-blue-600 flex items-center">
//                             <span class="material-symbols-outlined text-sm mr-1">location_on</span> California, USA
//                         </p>
//                     </div>
//                     <div class="relative group">
//                         <button
//                             class="bg-blue-500 hover:bg-blue-600 text-white p-2 rounded-full transition-all duration-300 transform group-hover:rotate-180"
//                         >
//                             <span class="material-symbols-outlined">refresh</span>
//                         </button>
//                         <span
//                             class="absolute bottom-full right-0 mb-2 bg-blue-700 text-white text-xs px-2 py-1 rounded opacity-0 group-hover:opacity-100 transition-opacity duration-300"
//                             >Refresh</span
//                         >
//                     </div>
//                 </div>
//                 <div class="flex items-center justify-between mb-8 bg-white/70 p-4 rounded-lg shadow-md">
//                     <div class="flex flex-col items-center">
//                         <span class="material-symbols-outlined text-6xl text-yellow-500">wb_sunny</span>
//                         <p class="text-sm font-medium text-blue-700 mt-1">Sunny</p>
//                     </div>
//                     <div class="text-right">
//                         <div class="text-5xl font-bold text-blue-800">72°F</div>
//                         <div class="text-sm text-blue-600">Feels like 75°F</div>
//                         <div class="flex items-center justify-end mt-2 text-blue-700">
//                             <span class="material-symbols-outlined text-sm mr-1">water_drop</span>
//                             <span class="text-sm">42%</span> <span class="mx-2">|</span>
//                             <span class="material-symbols-outlined text-sm mr-1">air</span>
//                             <span class="text-sm">8 mph</span>
//                         </div>
//                     </div>
//                 </div>
//                 <div class="mb-8 bg-white/70 p-4 rounded-lg shadow-md hover:shadow-lg transition-all duration-300">
//                     <h3 class="text-lg font-semibold text-blue-800 mb-3">Hourly Forecast</h3>
//                     <div class="relative h-[120px] mb-2">
//                         <div class="absolute bottom-0 left-0 w-full h-[80px] flex items-end">
//                             <div class="w-full flex justify-between">
//                                 <div class="flex flex-col items-center group" style="width: 12.5%">
//                                     <div class="h-[1px] w-full bg-blue-200"></div>
//                                     <div
//                                         class="w-1.5 bg-blue-500 rounded-t-full transition-all duration-300 group-hover:bg-blue-600 group-hover:shadow-md cursor-pointer"
//                                         style="height: 22px"
//                                     ></div>
//                                     <div
//                                         class="absolute bottom-full mb-1 bg-blue-700 text-white text-xs px-1.5 py-0.5 rounded opacity-0 group-hover:opacity-100 transition-opacity duration-200"
//                                     >
//                                         72°F
//                                     </div>
//                                 </div>
//                                 <div class="flex flex-col items-center group" style="width: 12.5%">
//                                     <div class="h-[1px] w-full bg-blue-200"></div>
//                                     <div
//                                         class="w-1.5 bg-blue-500 rounded-t-full transition-all duration-300 group-hover:bg-blue-600 group-hover:shadow-md cursor-pointer"
//                                         style="height: 24px"
//                                     ></div>
//                                     <div
//                                         class="absolute bottom-full mb-1 bg-blue-700 text-white text-xs px-1.5 py-0.5 rounded opacity-0 group-hover:opacity-100 transition-opacity duration-200"
//                                     >
//                                         74°F
//                                     </div>
//                                 </div>
//                                 <div class="flex flex-col items-center group" style="width: 12.5%">
//                                     <div class="h-[1px] w-full bg-blue-200"></div>
//                                     <div
//                                         class="w-1.5 bg-blue-500 rounded-t-full transition-all duration-300 group-hover:bg-blue-600 group-hover:shadow-md cursor-pointer"
//                                         style="height: 26px"
//                                     ></div>
//                                     <div
//                                         class="absolute bottom-full mb-1 bg-blue-700 text-white text-xs px-1.5 py-0.5 rounded opacity-0 group-hover:opacity-100 transition-opacity duration-200"
//                                     >
//                                         76°F
//                                     </div>
//                                 </div>
//                                 <div class="flex flex-col items-center group" style="width: 12.5%">
//                                     <div class="h-[1px] w-full bg-blue-200"></div>
//                                     <div
//                                         class="w-1.5 bg-blue-500 rounded-t-full transition-all duration-300 group-hover:bg-blue-600 group-hover:shadow-md cursor-pointer"
//                                         style="height: 23px"
//                                     ></div>
//                                     <div
//                                         class="absolute bottom-full mb-1 bg-blue-700 text-white text-xs px-1.5 py-0.5 rounded opacity-0 group-hover:opacity-100 transition-opacity duration-200"
//                                     >
//                                         73°F
//                                     </div>
//                                 </div>
//                                 <div class="flex flex-col items-center group" style="width: 12.5%">
//                                     <div class="h-[1px] w-full bg-blue-200"></div>
//                                     <div
//                                         class="w-1.5 bg-blue-500 rounded-t-full transition-all duration-300 group-hover:bg-blue-600 group-hover:shadow-md cursor-pointer"
//                                         style="height: 21px"
//                                     ></div>
//                                     <div
//                                         class="absolute bottom-full mb-1 bg-blue-700 text-white text-xs px-1.5 py-0.5 rounded opacity-0 group-hover:opacity-100 transition-opacity duration-200"
//                                     >
//                                         71°F
//                                     </div>
//                                 </div>
//                                 <div class="flex flex-col items-center group" style="width: 12.5%">
//                                     <div class="h-[1px] w-full bg-blue-200"></div>
//                                     <div
//                                         class="w-1.5 bg-blue-500 rounded-t-full transition-all duration-300 group-hover:bg-blue-600 group-hover:shadow-md cursor-pointer"
//                                         style="height: 18px"
//                                     ></div>
//                                     <div
//                                         class="absolute bottom-full mb-1 bg-blue-700 text-white text-xs px-1.5 py-0.5 rounded opacity-0 group-hover:opacity-100 transition-opacity duration-200"
//                                     >
//                                         68°F
//                                     </div>
//                                 </div>
//                                 <div class="flex flex-col items-center group" style="width: 12.5%">
//                                     <div class="h-[1px] w-full bg-blue-200"></div>
//                                     <div
//                                         class="w-1.5 bg-blue-500 rounded-t-full transition-all duration-300 group-hover:bg-blue-600 group-hover:shadow-md cursor-pointer"
//                                         style="height: 15px"
//                                     ></div>
//                                     <div
//                                         class="absolute bottom-full mb-1 bg-blue-700 text-white text-xs px-1.5 py-0.5 rounded opacity-0 group-hover:opacity-100 transition-opacity duration-200"
//                                     >
//                                         65°F
//                                     </div>
//                                 </div>
//                                 <div class="flex flex-col items-center group" style="width: 12.5%">
//                                     <div class="h-[1px] w-full bg-blue-200"></div>
//                                     <div
//                                         class="w-1.5 bg-blue-500 rounded-t-full transition-all duration-300 group-hover:bg-blue-600 group-hover:shadow-md cursor-pointer"
//                                         style="height: 13px"
//                                     ></div>
//                                     <div
//                                         class="absolute bottom-full mb-1 bg-blue-700 text-white text-xs px-1.5 py-0.5 rounded opacity-0 group-hover:opacity-100 transition-opacity duration-200"
//                                     >
//                                         63°F
//                                     </div>
//                                 </div>
//                             </div>
//                         </div>
//                         <div class="absolute top-0 left-0 w-full">
//                             <div class="flex justify-between text-xs text-blue-600 mb-1">
//                                 <div>75°</div>
//                                 <div>65°</div>
//                             </div>
//                             <div class="h-[1px] w-full bg-blue-200"></div>
//                         </div>
//                     </div>
//                     <div class="flex justify-between text-xs text-blue-700 font-medium pt-1">
//                         <div class="flex flex-col items-center">
//                             <span>Now</span> <span class="material-symbols-outlined text-sm mt-1"> nights_stay </span>
//                         </div>
//                         <div class="flex flex-col items-center">
//                             <span>2PM</span> <span class="material-symbols-outlined text-sm mt-1"> wb_sunny </span>
//                         </div>
//                         <div class="flex flex-col items-center">
//                             <span>4PM</span> <span class="material-symbols-outlined text-sm mt-1"> wb_sunny </span>
//                         </div>
//                         <div class="flex flex-col items-center">
//                             <span>6PM</span> <span class="material-symbols-outlined text-sm mt-1"> wb_sunny </span>
//                         </div>
//                         <div class="flex flex-col items-center">
//                             <span>8PM</span> <span class="material-symbols-outlined text-sm mt-1"> nights_stay </span>
//                         </div>
//                         <div class="flex flex-col items-center">
//                             <span>10PM</span> <span class="material-symbols-outlined text-sm mt-1"> nights_stay </span>
//                         </div>
//                         <div class="flex flex-col items-center">
//                             <span>12AM</span> <span class="material-symbols-outlined text-sm mt-1"> nights_stay </span>
//                         </div>
//                         <div class="flex flex-col items-center">
//                             <span>2AM</span> <span class="material-symbols-outlined text-sm mt-1"> nights_stay </span>
//                         </div>
//                     </div>
//                 </div>
//                 <h3 class="text-lg font-semibold text-blue-800 mb-4">7-Day Forecast</h3>
//                 <div class="grid grid-cols-7 gap-2 mb-6">
//                     <div
//                         class="flex flex-col items-center bg-white/60 hover:bg-white/90 rounded-lg p-2 transition-all duration-300 transform hover:-translate-y-1 hover:shadow-md"
//                     >
//                         <p class="text-xs font-bold text-blue-700">Mon</p>
//                         <span class="material-symbols-outlined text-xl my-1" style="color: #f59e0b"> wb_sunny </span>
//                         <p class="text-xs font-medium text-blue-800">72°</p>
//                     </div>
//                     <div
//                         class="flex flex-col items-center bg-white/60 hover:bg-white/90 rounded-lg p-2 transition-all duration-300 transform hover:-translate-y-1 hover:shadow-md"
//                     >
//                         <p class="text-xs font-bold text-blue-700">Tue</p>
//                         <span class="material-symbols-outlined text-xl my-1" style="color: #f59e0b"> wb_sunny </span>
//                         <p class="text-xs font-medium text-blue-800">70°</p>
//                     </div>
//                     <div
//                         class="flex flex-col items-center bg-white/60 hover:bg-white/90 rounded-lg p-2 transition-all duration-300 transform hover:-translate-y-1 hover:shadow-md"
//                     >
//                         <p class="text-xs font-bold text-blue-700">Wed</p>
//                         <span class="material-symbols-outlined text-xl my-1" style="color: #60a5fa"> cloudy </span>
//                         <p class="text-xs font-medium text-blue-800">68°</p>
//                     </div>
//                     <div
//                         class="flex flex-col items-center bg-white/60 hover:bg-white/90 rounded-lg p-2 transition-all duration-300 transform hover:-translate-y-1 hover:shadow-md"
//                     >
//                         <p class="text-xs font-bold text-blue-700">Thu</p>
//                         <span class="material-symbols-outlined text-xl my-1" style="color: #9ca3af"> cloud </span>
//                         <p class="text-xs font-medium text-blue-800">66°</p>
//                     </div>
//                     <div
//                         class="flex flex-col items-center bg-white/60 hover:bg-white/90 rounded-lg p-2 transition-all duration-300 transform hover:-translate-y-1 hover:shadow-md"
//                     >
//                         <p class="text-xs font-bold text-blue-700">Fri</p>
//                         <span class="material-symbols-outlined text-xl my-1" style="color: #9ca3af"> cloud </span>
//                         <p class="text-xs font-medium text-blue-800">64°</p>
//                     </div>
//                     <div
//                         class="flex flex-col items-center bg-white/60 hover:bg-white/90 rounded-lg p-2 transition-all duration-300 transform hover:-translate-y-1 hover:shadow-md"
//                     >
//                         <p class="text-xs font-bold text-blue-700">Sat</p>
//                         <span class="material-symbols-outlined text-xl my-1" style="color: #9ca3af"> cloud </span>
//                         <p class="text-xs font-medium text-blue-800">62°</p>
//                     </div>
//                     <div
//                         class="flex flex-col items-center bg-white/60 hover:bg-white/90 rounded-lg p-2 transition-all duration-300 transform hover:-translate-y-1 hover:shadow-md"
//                     >
//                         <p class="text-xs font-bold text-blue-700">Sun</p>
//                         <span class="material-symbols-outlined text-xl my-1" style="color: #60a5fa"> cloudy </span>
//                         <p class="text-xs font-medium text-blue-800">60°</p>
//                     </div>
//                 </div>
//                 <div class="flex justify-between bg-blue-600/10 p-3 rounded-lg">
//                     <div class="flex items-center">
//                         <span class="material-symbols-outlined text-blue-700 mr-2">schedule</span>
//                         <div>
//                             <p class="text-xs text-blue-600">Updated</p>
//                             <p class="text-sm font-medium text-blue-800">Just now</p>
//                         </div>
//                     </div>
//                     <div class="flex">
//                         <button
//                             class="mr-2 bg-blue-500 hover:bg-blue-600 text-white rounded-full p-2 transition-all duration-300 transform hover:scale-110"
//                         >
//                             <span class="material-symbols-outlined text-sm">share</span>
//                         </button>
//                         <button
//                             class="bg-blue-500 hover:bg-blue-600 text-white rounded-full p-2 transition-all duration-300 transform hover:scale-110"
//                         >
//                             <span class="material-symbols-outlined text-sm">favorite</span>
//                         </button>
//                     </div>
//                 </div>
//                 <div class="mt-5 flex justify-between items-center">
//                     <button
//                         class="bg-white hover:bg-blue-50 text-blue-700 border border-blue-200 px-4 py-2 rounded-lg shadow-sm transition-all duration-300 hover:shadow"
//                     >
//                         <span class="material-symbols-outlined text-sm mr-1 align-text-bottom">map</span> View Map
//                     </button>
//                     <button
//                         class="bg-blue-700 hover:bg-blue-800 text-white px-4 py-2 rounded-lg shadow-sm transition-all duration-300 hover:shadow-md"
//                     >
//                         <span class="material-symbols-outlined text-sm mr-1 align-text-bottom">notifications</span> Set
//                         Alert
//                     </button>
//                 </div>
//             </div>
//         </div>

//         <script src="https://cdn.tailwindcss.com"></script>
//         <script>
//             tailwind.config = {
//                 content: ["./src/**/*.{html,js}"],
//                 theme: {
//                     name: "Bluewave",
//                     fontFamily: {
//                         sans: [
//                             "Open Sans",
//                             "ui-sans-serif",
//                             "system-ui",
//                             "sans-serif",
//                             '"Apple Color Emoji"',
//                             '"Segoe UI Emoji"',
//                             '"Segoe UI Symbol"',
//                             '"Noto Color Emoji"'
//                         ]
//                     },
//                     extend: {
//                         fontFamily: {
//                             title: [
//                                 "Lato",
//                                 "ui-sans-serif",
//                                 "system-ui",
//                                 "sans-serif",
//                                 '"Apple Color Emoji"',
//                                 '"Segoe UI Emoji"',
//                                 '"Segoe UI Symbol"',
//                                 '"Noto Color Emoji"'
//                             ],
//                             body: [
//                                 "Open Sans",
//                                 "ui-sans-serif",
//                                 "system-ui",
//                                 "sans-serif",
//                                 '"Apple Color Emoji"',
//                                 '"Segoe UI Emoji"',
//                                 '"Segoe UI Symbol"',
//                                 '"Noto Color Emoji"'
//                             ]
//                         },
//                         colors: {
//                             neutral: {
//                                 50: "#f7f7f7",
//                                 100: "#eeeeee",
//                                 200: "#e0e0e0",
//                                 300: "#cacaca",
//                                 400: "#b1b1b1",
//                                 500: "#999999",
//                                 600: "#7f7f7f",
//                                 700: "#676767",
//                                 800: "#545454",
//                                 900: "#464646",
//                                 950: "#282828"
//                             },
//                             primary: {
//                                 50: "#f3f1ff",
//                                 100: "#e9e5ff",
//                                 200: "#d5cfff",
//                                 300: "#b7a9ff",
//                                 400: "#9478ff",
//                                 500: "#7341ff",
//                                 600: "#631bff",
//                                 700: "#611bf8",
//                                 800: "#4607d0",
//                                 900: "#3c08aa",
//                                 950: "#220174",
//                                 DEFAULT: "#611bf8"
//                             }
//                         }
//                     },
//                     fontSize: {
//                         xs: ["12px", {lineHeight: "19.200000000000003px"}],
//                         sm: ["14px", {lineHeight: "21px"}],
//                         base: ["16px", {lineHeight: "25.6px"}],
//                         lg: ["18px", {lineHeight: "27px"}],
//                         xl: ["20px", {lineHeight: "28px"}],
//                         "2xl": ["24px", {lineHeight: "31.200000000000003px"}],
//                         "3xl": ["30px", {lineHeight: "36px"}],
//                         "4xl": ["36px", {lineHeight: "41.4px"}],
//                         "5xl": ["48px", {lineHeight: "52.800000000000004px"}],
//                         "6xl": ["60px", {lineHeight: "66px"}],
//                         "7xl": ["72px", {lineHeight: "75.60000000000001px"}],
//                         "8xl": ["96px", {lineHeight: "100.80000000000001px"}],
//                         "9xl": ["128px", {lineHeight: "134.4px"}]
//                     },
//                     borderRadius: {
//                         none: "0px",
//                         sm: "6px",
//                         DEFAULT: "12px",
//                         md: "18px",
//                         lg: "24px",
//                         xl: "36px",
//                         "2xl": "48px",
//                         "3xl": "72px",
//                         full: "9999px"
//                     },
//                     spacing: {
//                         0: "0px",
//                         1: "4px",
//                         2: "8px",
//                         3: "12px",
//                         4: "16px",
//                         5: "20px",
//                         6: "24px",
//                         7: "28px",
//                         8: "32px",
//                         9: "36px",
//                         10: "40px",
//                         11: "44px",
//                         12: "48px",
//                         14: "56px",
//                         16: "64px",
//                         20: "80px",
//                         24: "96px",
//                         28: "112px",
//                         32: "128px",
//                         36: "144px",
//                         40: "160px",
//                         44: "176px",
//                         48: "192px",
//                         52: "208px",
//                         56: "224px",
//                         60: "240px",
//                         64: "256px",
//                         72: "288px",
//                         80: "320px",
//                         96: "384px",
//                         px: "1px",
//                         0.5: "2px",
//                         1.5: "6px",
//                         2.5: "10px",
//                         3.5: "14px"
//                     }
//                 },
//                 plugins: [],
//                 important: "#webcrumbs"
//             }
//         </script>
//     </body>
// </html>
