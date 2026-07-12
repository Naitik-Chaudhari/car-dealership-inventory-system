import { useEffect, useState } from "react";
import { toast } from "react-hot-toast";
import { Search, ChevronDown, ChevronUp } from "lucide-react";

import Navbar from "../components/Navbar";
import SearchFilter from "../components/SearchFilter";
import VehicleCard from "../components/VehicleCard";
import VehicleCardSkeleton from "../components/VehicleCardSkeleton";
import { useNavigate } from "react-router-dom";

import {
    getAllVehicles,
    searchVehicles,
} from "../api/vehicleApi";

export default function Dashboard() {
    const [vehicles, setVehicles] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showFilters, setShowFilters] = useState(false);
    const navigate = useNavigate();

    const [filters, setFilters] = useState({
        make: "",
        model: "",
        category: "ALL",
        minPrice: "",
        maxPrice: "",
    });

    useEffect(() => {
        loadVehicles();
    }, []);

    const loadVehicles = async () => {
        try {
            setLoading(true);

            const data = await getAllVehicles();

            setVehicles(data);
        } catch (error) {
            toast.error("Failed to load vehicles.");
        } finally {
            setLoading(false);
        }
    };

    const handleSearch = async () => {
        try {
            setLoading(true);

            const data = await searchVehicles(filters);

            setVehicles(data);
        } catch (error) {
            toast.error("Search failed.");
        } finally {
            setLoading(false);
        }
    };

    const handleReset = () => {
        setFilters({
            make: "",
            model: "",
            category: "ALL",
            minPrice: "",
            maxPrice: "",
        });

        loadVehicles();
    };

    const handlePurchase = (vehicleId) => {

        const token = localStorage.getItem("token");

        if (!token) {

            toast.error("Please login to purchase a vehicle.");

            navigate("/login");

            return;
        }

        console.log(vehicleId);

    };

    return (
        <div className="min-h-screen bg-slate-100">

            <Navbar />

            <div className="max-w-7xl mx-auto px-6 py-8">

                <div className="mb-10">

                    <h1 className="text-5xl font-extrabold text-gray-800">
                        Find Your Dream Car
                    </h1>

                    <p className="text-gray-500 mt-4 max-w-2xl">
                        Explore our premium collection of SUVs, Sedans, Hatchbacks,
                        Luxury, Sports and Electric vehicles. Find the perfect car
                        that fits your lifestyle and budget.
                    </p>

                </div>

                <div className="mb-8">

                    <button
                        onClick={() => setShowFilters(!showFilters)}
                        className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-xl flex items-center gap-2"
                    >
                        <Search size={20} />

                        Search & Filters

                        {showFilters ? (
                            <ChevronUp size={18} />
                        ) : (
                            <ChevronDown size={18} />
                        )}
                    </button>

                </div>

                {showFilters && (

                    <SearchFilter
                        filters={filters}
                        setFilters={setFilters}
                        onSearch={handleSearch}
                        onReset={handleReset}
                    />

                )}

                <div className="flex justify-between items-center mb-6">

                    <h2 className="text-2xl font-bold">

                        Available Vehicles

                    </h2>

                    <span className="text-gray-500">

        {vehicles.length} vehicle(s)

    </span>

                </div>

                {loading ? (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">

                        {[...Array(8)].map((_, index) => (
                            <VehicleCardSkeleton key={index} />
                        ))}

                    </div>
                ) : vehicles.length === 0 ? (
                    <div className="bg-white rounded-2xl shadow-md py-20">

                        <div className="text-center">

                            <div className="text-7xl mb-4">
                                🚗
                            </div>

                            <h2 className="text-2xl font-bold text-gray-700">
                                No Vehicles Found
                            </h2>

                            <p className="text-gray-500 mt-2">
                                Try changing your search filters.
                            </p>

                        </div>

                    </div>
                ) : (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">

                        {vehicles.map((vehicle) => (
                            <VehicleCard
                                key={vehicle.id}
                                vehicle={vehicle}
                                onPurchase={handlePurchase}
                            />
                        ))}

                    </div>
                )}

            </div>

        </div>
    );
}