import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import axiosInstance from "../services/axiosInstance";
import { Eye, EyeOff, Car } from "lucide-react";
import { toast } from "react-hot-toast";

export default function Login() {
    const navigate = useNavigate();

    const [showPassword, setShowPassword] = useState(false);
    const [loading, setLoading] = useState(false);

    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm();

    const onSubmit = async (data) => {
        try {
            setLoading(true);

            const response = await axiosInstance.post("/auth/login", data);

            localStorage.setItem("token", response.data.token);

            toast.success("Login successful!");

            navigate("/");
        } catch (error) {
            if (error.response?.status === 401) {
                toast.error(error.response.data.message);
            } else {
                toast.error("Something went wrong.");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-blue-100 to-slate-200 flex items-center justify-center px-4">
            <div className="bg-white rounded-3xl shadow-2xl w-full max-w-5xl overflow-hidden grid md:grid-cols-2">

                {/* Left */}
                <div className="hidden md:flex flex-col justify-center items-center bg-blue-600 text-white p-10">
                    <Car size={70} />

                    <h1 className="text-4xl font-bold mt-6">
                        Vehicle Management
                    </h1>

                    <p className="mt-4 text-center text-blue-100">
                        Welcome back! Login to continue.
                    </p>
                </div>

                {/* Right */}
                <div className="p-10">

                    <h2 className="text-3xl font-bold text-gray-800">
                        Login
                    </h2>

                    <p className="text-gray-500 mt-2 mb-8">
                        Enter your credentials.
                    </p>

                    <form
                        onSubmit={handleSubmit(onSubmit)}
                        className="space-y-5"
                    >

                        <div>
                            <label className="block mb-2 font-medium">
                                Email
                            </label>

                            <input
                                type="email"
                                placeholder="Enter email"
                                className="w-full border rounded-xl p-3 focus:ring-2 focus:ring-blue-500 outline-none"
                                {...register("email", {
                                    required: "Email is required",
                                })}
                            />

                            {errors.email && (
                                <p className="text-red-500 text-sm mt-1">
                                    {errors.email.message}
                                </p>
                            )}
                        </div>

                        <div>
                            <label className="block mb-2 font-medium">
                                Password
                            </label>

                            <div className="relative">

                                <input
                                    type={showPassword ? "text" : "password"}
                                    placeholder="Password"
                                    className="w-full border rounded-xl p-3 pr-12 focus:ring-2 focus:ring-blue-500 outline-none"
                                    {...register("password", {
                                        required: "Password is required",
                                    })}
                                />

                                <button
                                    type="button"
                                    className="absolute right-4 top-4 text-gray-500"
                                    onClick={() => setShowPassword(!showPassword)}
                                >
                                    {showPassword ? (
                                        <EyeOff size={20} />
                                    ) : (
                                        <Eye size={20} />
                                    )}
                                </button>

                            </div>

                            {errors.password && (
                                <p className="text-red-500 text-sm mt-1">
                                    {errors.password.message}
                                </p>
                            )}
                        </div>

                        <button
                            disabled={loading}
                            className="w-full bg-blue-600 hover:bg-blue-700 transition text-white rounded-xl p-3 font-semibold disabled:opacity-60"
                        >
                            {loading ? "Logging in..." : "Login"}
                        </button>

                    </form>

                    <p className="mt-6 text-center text-gray-600">
                        Don't have an account?

                        <Link
                            to="/register"
                            className="text-blue-600 font-semibold ml-2 hover:underline"
                        >
                            Register
                        </Link>

                    </p>

                </div>

            </div>
        </div>
    );
}