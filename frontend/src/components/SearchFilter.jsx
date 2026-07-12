import { Search, RotateCcw } from "lucide-react";

const categories = [
    "ALL",
    "SEDAN",
    "SUV",
    "HATCHBACK",
    "COUPE",
    "CONVERTIBLE",
    "WAGON",
    "PICKUP",
    "VAN",
    "MINIVAN",
    "SPORTS",
    "LUXURY",
    "ELECTRIC",
    "HYBRID",
];

export default function SearchFilter({
                                         filters,
                                         setFilters,
                                         onSearch,
                                         onReset,
                                     }) {
    const handleChange = (e) => {
        setFilters({
            ...filters,
            [e.target.name]: e.target.value,
        });
    };

    return (
        <div className="bg-white shadow-lg rounded-2xl p-6 mb-8">

            <h2 className="text-xl font-bold text-gray-800 mb-6">
                Search Vehicles
            </h2>

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-4">

                {/* Make */}

                <div>
                    <label className="block text-sm font-medium mb-2">
                        Make
                    </label>

                    <input
                        type="text"
                        name="make"
                        value={filters.make}
                        onChange={handleChange}
                        placeholder="Toyota"
                        className="w-full border rounded-xl p-3 focus:ring-2 focus:ring-blue-500 outline-none"
                    />
                </div>

                {/* Model */}

                <div>
                    <label className="block text-sm font-medium mb-2">
                        Model
                    </label>

                    <input
                        type="text"
                        name="model"
                        value={filters.model}
                        onChange={handleChange}
                        placeholder="Fortuner"
                        className="w-full border rounded-xl p-3 focus:ring-2 focus:ring-blue-500 outline-none"
                    />
                </div>

                {/* Category */}

                <div>
                    <label className="block text-sm font-medium mb-2">
                        Category
                    </label>

                    <select
                        name="category"
                        value={filters.category}
                        onChange={handleChange}
                        className="w-full border rounded-xl p-3 focus:ring-2 focus:ring-blue-500 outline-none"
                    >
                        {categories.map((category) => (
                            <option
                                key={category}
                                value={category}
                            >
                                {category}
                            </option>
                        ))}
                    </select>
                </div>

                {/* Min Price */}

                <div>
                    <label className="block text-sm font-medium mb-2">
                        Min Price
                    </label>

                    <input
                        type="number"
                        name="minPrice"
                        value={filters.minPrice}
                        onChange={handleChange}
                        placeholder="1000000"
                        className="w-full border rounded-xl p-3 focus:ring-2 focus:ring-blue-500 outline-none"
                    />
                </div>

                {/* Max Price */}

                <div>
                    <label className="block text-sm font-medium mb-2">
                        Max Price
                    </label>

                    <input
                        type="number"
                        name="maxPrice"
                        value={filters.maxPrice}
                        onChange={handleChange}
                        placeholder="5000000"
                        className="w-full border rounded-xl p-3 focus:ring-2 focus:ring-blue-500 outline-none"
                    />
                </div>

            </div>

            <div className="flex justify-end gap-4 mt-6">

                <button
                    onClick={onReset}
                    className="flex items-center gap-2 border border-gray-300 px-6 py-3 rounded-xl hover:bg-gray-100 transition"
                >
                    <RotateCcw size={18} />
                    Reset
                </button>

                <button
                    onClick={onSearch}
                    className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-xl transition"
                >
                    <Search size={18} />
                    Search
                </button>

            </div>

        </div>
    );
}