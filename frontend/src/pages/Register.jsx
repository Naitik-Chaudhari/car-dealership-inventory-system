import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import axiosInstance from "../services/axiosInstance";
import { Eye, EyeOff, Car } from "lucide-react";
import { toast } from "react-hot-toast";

export default function Register() {
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

            await axiosInstance.post("/auth/register", data);

            toast.success("Registration successful!");

            navigate("/login");
        } catch (error) {
            if (error.response?.status === 409) {
                toast.error("Email already exists.");
            } else if (error.response?.status === 400) {
                toast.error("Please enter valid details.");
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

                {/* Left Section */}
                <div className="hidden md:flex flex-col justify-center items-center bg-blue-600 text-white p-10">

                    <Car size={70} />

                    <h1 className="text-4xl font-bold mt-6">
                        Vehicle Management
                    </h1>

                    <p className="mt-4 text-center text-blue-100">
                        Register to purchase vehicles and manage your inventory.
                    </p>

                </div>

                {/* Right Section */}

                <div className="p-10">

                    <h2 className="text-3xl font-bold text-gray-800">
                        Create Account
                    </h2>

                    <p className="text-gray-500 mt-2 mb-8">
                        Please fill in your details.
                    </p>

                    <form
                        onSubmit={handleSubmit(onSubmit)}
                        className="space-y-5"
                    >

                        {/* Name */}

                        <div>

                            <label className="block mb-2 font-medium">
                                Name
                            </label>

                            <input
                                type="text"
                                placeholder="Enter your name"
                                className="w-full border rounded-xl p-3 focus:ring-2 focus:ring-blue-500 outline-none"
                                {...register("name", {
                                    required: "Name is required",
                                    minLength: {
                                        value: 3,
                                        message: "Minimum 3 characters",
                                    },
                                })}
                            />

                            {errors.name && (
                                <p className="text-red-500 text-sm mt-1">
                                    {errors.name.message}
                                </p>
                            )}

                        </div>

                        {/* Email */}

                        <div>

                            <label className="block mb-2 font-medium">
                                Email
                            </label>

                            <input
                                type="email"
                                placeholder="Enter your email"
                                className="w-full border rounded-xl p-3 focus:ring-2 focus:ring-blue-500 outline-none"
                                {...register("email", {
                                    required: "Email is required",
                                    pattern: {
                                        value:
                                            /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/,
                                        message: "Invalid email",
                                    },
                                })}
                            />

                            {errors.email && (
                                <p className="text-red-500 text-sm mt-1">
                                    {errors.email.message}
                                </p>
                            )}

                        </div>

                        {/* Password */}

                        <div>

                            <label className="block mb-2 font-medium">
                                Password
                            </label>

                            <div className="relative">

                                <input
                                    type={showPassword ? "text" : "password"}
                                    placeholder="Enter password"
                                    className="w-full border rounded-xl p-3 pr-12 focus:ring-2 focus:ring-blue-500 outline-none"
                                    {...register("password", {
                                        required: "Password is required",
                                        minLength: {
                                            value: 8,
                                            message: "Minimum 8 characters",
                                        },
                                    })}
                                />

                                <button
                                    type="button"
                                    className="absolute right-4 top-4 text-gray-500"
                                    onClick={() =>
                                        setShowPassword(!showPassword)
                                    }
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

                        {/* Button */}

                        <button
                            disabled={loading}
                            className="w-full bg-blue-600 hover:bg-blue-700 transition text-white rounded-xl p-3 font-semibold disabled:opacity-60"
                        >
                            {loading ? "Creating Account..." : "Register"}
                        </button>

                    </form>

                    <p className="mt-6 text-center text-gray-600">

                        Already have an account?

                        <Link
                            to="/login"
                            className="text-blue-600 font-semibold ml-2 hover:underline"
                        >
                            Login
                        </Link>

                    </p>

                </div>

            </div>

        </div>
    );
}