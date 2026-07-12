import { CarFront, LogOut } from "lucide-react";
import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
    const navigate = useNavigate();

    const token = localStorage.getItem("token");

    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/login");
    };

    return (
        <nav className="bg-white shadow-md sticky top-0 z-50">
            <div className="max-w-7xl mx-auto px-6 py-4 flex justify-between items-center">

                <div className="flex items-center gap-3">

                    <div className="bg-blue-600 p-2 rounded-xl">
                        <CarFront className="text-white" size={28} />
                    </div>

                    <div>
                        <h1 className="text-xl font-bold text-gray-800">
                            VehicleHub
                        </h1>

                        <p className="text-sm text-gray-500">
                            Car Dealership
                        </p>
                    </div>

                </div>

                {token ? (
                    <button
                        onClick={handleLogout}
                        className="flex items-center gap-2 bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg transition"
                    >
                        <LogOut size={18} />
                        Logout
                    </button>
                ) : (
                    <div className="flex gap-3">

                        <Link
                            to="/login"
                            className="px-4 py-2 rounded-lg border border-blue-600 text-blue-600 hover:bg-blue-50"
                        >
                            Login
                        </Link>

                        <Link
                            to="/register"
                            className="px-4 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700"
                        >
                            Register
                        </Link>

                    </div>
                )}

            </div>
        </nav>
    );
}