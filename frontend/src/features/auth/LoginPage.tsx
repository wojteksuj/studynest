import { useState } from "react";
import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import backgroundImage from "../../assets/bird-bg.png";

export default function LoginPage() {
    const { login } = useAuth();
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setLoading(true);

        try {
            await login({ email, password });
            navigate("/dashboard");
        } catch {
            setError("Invalid credentials");
        } finally {
            setLoading(false);
        }
        try {
            await login({ email, password });
            console.log("LOGIN SUCCESS");
            navigate("/dashboard");
        } catch (err) {
            console.log("LOGIN ERROR", err);
            setError("Invalid credentials");
        }

    };

    return (
        <div
            className="min-h-screen flex items-center justify-center relative"
            style={{
                backgroundImage: `url(${backgroundImage})`,
                backgroundSize: "cover",
                backgroundPosition: "center",
            }}
        >
            {/* Strong neutral overlay for contrast */}
            <div className="absolute inset-0 bg-black/75 backdrop-blur-sm" />

            {/* Card */}
            <div className="relative z-10 w-full max-w-md px-10 py-12 bg-[#3b2a1f] rounded-2xl shadow-[0_20px_60px_rgba(0,0,0,0.6)] border border-[#6b4f3a]">

                <div className="text-center mb-10">
                    <h1 className="text-3xl font-semibold text-white mb-2 tracking-wide">
                        StudyNest
                    </h1>
                    <p className="text-[#e0cfc2] text-sm">
                        A calm space for focused learning
                    </p>
                </div>

                {error && (
                    <div className="mb-5 text-sm text-red-300 bg-red-900/40 border border-red-700 px-3 py-2 rounded-lg">
                        {error}
                    </div>
                )}

                <form onSubmit={handleSubmit} className="space-y-6">

                    <div>
                        <label className="block text-sm text-[#e0cfc2] mb-2">
                            Email
                        </label>
                        <input
                            type="email"
                            required
                            className="w-full bg-[#4c372a] border border-[#6b4f3a] rounded-lg px-4 py-3 text-white placeholder-[#c9b5a8] focus:outline-none focus:ring-2 focus:ring-[#d6b08c] focus:border-transparent transition"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="you@example.com"
                        />
                    </div>

                    <div>
                        <label className="block text-sm text-[#e0cfc2] mb-2">
                            Password
                        </label>
                        <input
                            type="password"
                            required
                            className="w-full bg-[#4c372a] border border-[#6b4f3a] rounded-lg px-4 py-3 text-white placeholder-[#c9b5a8] focus:outline-none focus:ring-2 focus:ring-[#d6b08c] focus:border-transparent transition"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="••••••••"
                        />
                    </div>

                    {/* Primary */}
                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-[#d6b08c] hover:bg-[#e2c3a4] text-[#2a1b12] font-semibold py-3 rounded-lg transition disabled:opacity-60 disabled:cursor-not-allowed"
                    >
                        {loading ? "Signing in..." : "Sign in"}
                    </button>

                    {/* Secondary */}
                    <button
                        type="button"
                        onClick={() => navigate("/register")}
                        className="w-full border border-[#d6b08c] text-white hover:bg-[#d6b08c]/20 py-3 rounded-lg transition"
                    >
                        Register
                    </button>

                </form>

                <p className="text-center text-xs text-[#bfae9f] mt-10">
                    © {new Date().getFullYear()} StudyNest
                </p>

            </div>
        </div>
    );
}
