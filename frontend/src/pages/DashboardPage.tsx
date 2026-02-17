import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function DashboardPage() {
    const { logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    return (
        <div className="min-h-screen bg-[#2a1b12] text-white flex flex-col">

            {/* Header */}
            <header className="flex items-center justify-between px-8 py-4 border-b border-[#4c372a] bg-[#3b2a1f]">

                <h1 className="text-xl font-semibold tracking-wide">
                    StudyNest
                </h1>

                <button
                    onClick={handleLogout}
                    className="px-4 py-2 border border-[#d6b08c] text-[#d6b08c] rounded-lg hover:bg-[#d6b08c]/20 transition"
                >
                    Logout
                </button>

            </header>

            {/* Content */}
            <main className="flex-1 flex items-center justify-center">

                <div className="text-center">
                    <h2 className="text-3xl font-semibold mb-4">
                        Welcome to your nest 🪺
                    </h2>

                    <p className="text-[#e0cfc2] max-w-md mx-auto">
                        This is your dashboard.
                        From here you will manage notes, tasks and your study sessions.
                    </p>
                </div>

            </main>

        </div>
    );
}
