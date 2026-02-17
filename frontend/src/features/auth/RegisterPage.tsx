import { useState } from "react";
import { useNavigate } from "react-router-dom";
import backgroundImage from "../../assets/bird-bg.png";
import axios from "../../api/axios";


export default function RegisterPage() {
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);

        if (password !== confirmPassword) {
            setError("Passwords do not match");
            return;
        }

        setLoading(true);

        try {
            await axios.post("/auth/register", {
                username,
                email,
                password,
            });

            navigate("/login");
        } catch (err: any) {
            setError(
                err?.response?.data?.message || "Registration failed"
            );
        } finally {
            setLoading(false);
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
            <div className="absolute inset-0 bg-black/75 backdrop-blur-sm" />

            <div className="relative z-10 w-full max-w-md px-10 py-12 bg-[#3b2a1f] rounded-2xl shadow-[0_20px_60px_rgba(0,0,0,0.6)] border border-[#6b4f3a]">

                <div className="text-center mb-10">
                    <h1 className="text-3xl font-semibold text-white mb-2 tracking-wide">
                        Create account
                    </h1>
                    <p className="text-[#e0cfc2] text-sm">
                        Join your digital study nest
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
                            Username
                        </label>
                        <input
                            type="text"
                            required
                            className="w-full bg-[#4c372a] border border-[#6b4f3a] rounded-lg px-4 py-3 text-white placeholder-[#c9b5a8] focus:outline-none focus:ring-2 focus:ring-[#d6b08c] transition"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="your_username"
                        />
                    </div>

                    <div>
                        <label className="block text-sm text-[#e0cfc2] mb-2">
                            Email
                        </label>
                        <input
                            type="email"
                            required
                            className="w-full bg-[#4c372a] border border-[#6b4f3a] rounded-lg px-4 py-3 text-white placeholder-[#c9b5a8] focus:outline-none focus:ring-2 focus:ring-[#d6b08c] transition"
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
                            className="w-full bg-[#4c372a] border border-[#6b4f3a] rounded-lg px-4 py-3 text-white placeholder-[#c9b5a8] focus:outline-none focus:ring-2 focus:ring-[#d6b08c] transition"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="••••••••"
                        />
                    </div>

                    <div>
                        <label className="block text-sm text-[#e0cfc2] mb-2">
                            Confirm password
                        </label>
                        <input
                            type="password"
                            required
                            className="w-full bg-[#4c372a] border border-[#6b4f3a] rounded-lg px-4 py-3 text-white placeholder-[#c9b5a8] focus:outline-none focus:ring-2 focus:ring-[#d6b08c] transition"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            placeholder="••••••••"
                        />
                    </div>

                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-[#d6b08c] hover:bg-[#e2c3a4] text-[#2a1b12] font-semibold py-3 rounded-lg transition disabled:opacity-60 disabled:cursor-not-allowed"
                    >
                        {loading ? "Creating account..." : "Create account"}
                    </button>

                    <button
                        type="button"
                        onClick={() => navigate("/login")}
                        className="w-full border border-[#d6b08c] text-white hover:bg-[#d6b08c]/20 py-3 rounded-lg transition"
                    >
                        Back to login
                    </button>

                </form>
            </div>
        </div>
    );
}
