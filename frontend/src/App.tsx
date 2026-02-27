import { Routes, Route } from "react-router-dom";
import LoginPage from "./features/auth/LoginPage";
import DashboardPage from "./pages/DashboardPage";
import ProtectedRoute from "./router/ProtectedRoute";
import RegisterPage from "./features/auth/RegisterPage";
import SetDetailsPage from "./pages/SetDetailsPage";
import EditSetPage from "./pages/EditSetPage.tsx";


function App() {
    return (
        <Routes>
            <Route path="/" element={<LoginPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/flashcard-sets/:setId" element={<SetDetailsPage />} />
            <Route path="/flashcard-sets/:setId/edit" element={<EditSetPage />} />
            <Route
                path="/dashboard"
                element={
                    <ProtectedRoute>
                        <DashboardPage />
                    </ProtectedRoute>
                }
            />
        </Routes>
    );
}

export default App;
