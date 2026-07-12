import { useEffect, useState } from "react";
import { X } from "lucide-react";

const categories = [
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

export default function VehicleFormModal({
                                             open,
                                             onClose,
                                             onSave,
                                             vehicle,
                                             loading,
                                         }) {
    const [formData, setFormData] = useState({
        make: "",
        model: "",
        category: "SUV",
        price: "",
        quantity: "",
    });

    useEffect(() => {
        if (vehicle) {
            setFormData({
                make: vehicle.make,
                model: vehicle.model,
                category: vehicle.category,
                price: vehicle.price,
                quantity: vehicle.quantity,
            });
        } else {
            setFormData({
                make: "",
                model: "",
                category: "SUV",
                price: "",
                quantity: "",
            });
        }
    }, [vehicle, open]);

    if (!open) return null;

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]:
                e.target.type === "number"
                    ? Number(e.target.value)
                    : e.target.value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSave(formData);
    };

    return (
        <div className="fixed inset-0 bg-black/40 flex justify-center items-center z-50 px-4">

            <div className="bg-white rounded-2xl shadow-xl w-full max-w-lg">

                <div className="flex justify-between items-center border-b p-6">

                    <h2 className="text-2xl font-bold">

                        {vehicle ? "Edit Vehicle" : "Add Vehicle"}

                    </h2>

                    <button onClick={onClose}>
                        <X />
                    </button>

                </div>

                <form
                    onSubmit={handleSubmit}
                    className="p-6 space-y-5"
                >

                    <div>

                        <label className="block mb-2 font-medium">
                            Make
                        </label>

                        <input
                            name="make"
                            value={formData.make}
                            onChange={handleChange}
                            className="w-full border rounded-xl p-3"
                            required
                        />

                    </div>

                    <div>

                        <label className="block mb-2 font-medium">
                            Model
                        </label>

                        <input
                            name="model"
                            value={formData.model}
                            onChange={handleChange}
                            className="w-full border rounded-xl p-3"
                            required
                        />

                    </div>

                    <div>

                        <label className="block mb-2 font-medium">
                            Category
                        </label>

                        <select
                            name="category"
                            value={formData.category}
                            onChange={handleChange}
                            className="w-full border rounded-xl p-3"
                        >

                            {categories.map(category => (

                                <option
                                    key={category}
                                    value={category}
                                >

                                    {category}

                                </option>

                            ))}

                        </select>

                    </div>

                    <div>

                        <label className="block mb-2 font-medium">
                            Price
                        </label>

                        <input
                            type="number"
                            name="price"
                            value={formData.price}
                            onChange={handleChange}
                            className="w-full border rounded-xl p-3"
                            required
                        />

                    </div>

                    <div>

                        <label className="block mb-2 font-medium">
                            Quantity
                        </label>

                        <input
                            type="number"
                            name="quantity"
                            value={formData.quantity}
                            onChange={handleChange}
                            className="w-full border rounded-xl p-3"
                            required
                        />

                    </div>

                    <div className="flex justify-end gap-3 pt-4">

                        <button
                            type="button"
                            onClick={onClose}
                            className="border px-5 py-2 rounded-lg hover:bg-gray-100"
                        >

                            Cancel

                        </button>

                        <button
                            disabled={loading}
                            className="bg-blue-600 hover:bg-blue-700 text-white px-5 py-2 rounded-lg"
                        >

                            {loading
                                ? "Saving..."
                                : vehicle
                                    ? "Update Vehicle"
                                    : "Add Vehicle"}

                        </button>

                    </div>

                </form>

            </div>

        </div>
    );
}