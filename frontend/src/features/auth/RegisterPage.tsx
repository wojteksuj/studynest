import {useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "../../api/axios";

type FieldErrors = {
    username?: string;
    email?: string;
    password?: string;
};

export default function RegisterPage() {
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const [fieldErrors, setFieldErrors] = useState<FieldErrors>({});
    const [generalError, setGeneralError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    const isFormValid =
        username.trim() !== "" &&
        email.trim() !== "" &&
        password.trim() !== "" &&
        confirmPassword.trim() !== "";

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setFieldErrors({});
        setGeneralError(null);

        if (password !== confirmPassword) {
            setFieldErrors({password: "Passwords do not match"});
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
            const status = err.response?.status;
            const data = err.response?.data;

            if (status === 400 && data?.errors) {
                const errors: FieldErrors = {};

                data.errors.forEach((error: any) => {
                    errors[error.field as keyof FieldErrors] = error.message;
                });

                setFieldErrors(errors);
            } else if (status === 409 && data?.field) {
                setFieldErrors({
                    [data.field]: data.message,
                });
            } else {
                setGeneralError(data?.message || "Registration failed");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center relative overflow-hidden">

            <div className="absolute inset-0 bg-gradient-to-br from-[#8FC3B1] via-[#E6BC9C] to-[#EE7F87]"/>
            <div className="absolute inset-0 bg-slate-900/70 backdrop-blur-2xl"/>

            <div
                className="relative z-10 w-full max-w-md px-10 py-12 bg-slate-800/90 rounded-2xl shadow-2xl border border-slate-700">

                <div className="text-center mb-10">
                    <h1 className="text-3xl font-semibold text-white mb-2 tracking-wide">
                        Create account
                    </h1>
                    <p className="text-slate-400 text-sm">
                        Join your digital study nest
                    </p>
                </div>

                {generalError && (
                    <div className="mb-5 text-sm text-red-300 bg-red-900/40 border border-red-700 px-3 py-2 rounded-lg">
                        {generalError}
                    </div>
                )}

                <form onSubmit={handleSubmit} className="space-y-6">

                    {/* Username */}
                    <div>
                        <label className="block text-sm text-slate-300 mb-2">
                            Username
                        </label>
                        <input
                            type="text"
                            required
                            className={`w-full bg-slate-900 border rounded-lg px-4 py-3 text-white placeholder-slate-500 focus:outline-none focus:ring-2 transition
                                ${fieldErrors.username
                                ? "border-red-500 focus:ring-red-500"
                                : "border-slate-700 focus:ring-[#8FC3B1]"
                            }`}
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="your_username"
                        />
                        {fieldErrors.username && (
                            <p className="text-red-400 text-xs mt-2">
                                {fieldErrors.username}
                            </p>
                        )}
                    </div>

                    {/* Email */}
                    <div>
                        <label className="block text-sm text-slate-300 mb-2">
                            Email
                        </label>
                        <input
                            type="email"
                            required
                            className={`w-full bg-slate-900 border rounded-lg px-4 py-3 text-white placeholder-slate-500 focus:outline-none focus:ring-2 transition
                                ${fieldErrors.email
                                ? "border-red-500 focus:ring-red-500"
                                : "border-slate-700 focus:ring-[#8FC3B1]"
                            }`}
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="you@example.com"
                        />
                        {fieldErrors.email && (
                            <p className="text-red-400 text-xs mt-2">
                                {fieldErrors.email}
                            </p>
                        )}
                    </div>

                    {/* Password */}
                    <div>
                        <label className="block text-sm text-slate-300 mb-2">
                            Password
                        </label>
                        <input
                            type="password"
                            required
                            className={`w-full bg-slate-900 border rounded-lg px-4 py-3 text-white placeholder-slate-500 focus:outline-none focus:ring-2 transition
                                ${fieldErrors.password
                                ? "border-red-500 focus:ring-red-500"
                                : "border-slate-700 focus:ring-[#8FC3B1]"
                            }`}
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="••••••••"
                        />
                        {fieldErrors.password && (
                            <p className="text-red-400 text-xs mt-2">
                                {fieldErrors.password}
                            </p>
                        )}
                    </div>

                    {/* Confirm password */}
                    <div>
                        <label className="block text-sm text-slate-300 mb-2">
                            Confirm password
                        </label>
                        <input
                            type="password"
                            required
                            className="w-full bg-slate-900 border border-slate-700 rounded-lg px-4 py-3 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-[#8FC3B1] transition"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            placeholder="••••••••"
                        />
                    </div>

                    <button
                        type="submit"
                        disabled={loading || !isFormValid}
                        className={`w-full font-semibold py-3 rounded-lg transition
                        ${loading || !isFormValid
                            ? "bg-slate-600 text-slate-400 cursor-not-allowed"
                            : "bg-[#EE7F87] hover:bg-[#E89A95] text-slate-900"
                        }`}
                    >
                        {loading ? "Creating account..." : "Create account"}
                    </button>


                    <button
                        type="button"
                        onClick={() => navigate("/login")}
                        className="w-full border border-slate-600 text-slate-300 hover:bg-slate-700 py-3 rounded-lg transition"
                    >
                        Back to login
                    </button>

                </form>
            </div>
        </div>
    );
}
