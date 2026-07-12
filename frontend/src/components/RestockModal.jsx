import { useEffect, useState } from "react";
import { PackagePlus } from "lucide-react";

export default function RestockModal({
                                         open,
                                         vehicle,
                                         loading,
                                         onClose,
                                         onConfirm,
                                     }) {
    const [quantity, setQuantity] = useState(1);

    useEffect(() => {
        setQuantity(1);
    }, [open]);

    if (!open || !vehicle) return null;

    return (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 px-4">

            <div className="bg-white rounded-2xl shadow-xl w-full max-w-md p-6">

                <div className="flex justify-center mb-5">

                    <div className="bg-green-100 p-4 rounded-full">

                        <PackagePlus
                            className="text-green-600"
                            size={40}
                        />

                    </div>

                </div>

                <h2 className="text-2xl font-bold text-center">
                    Restock Vehicle
                </h2>

                <p className="text-center font-semibold mt-4">
                    {vehicle.make} {vehicle.model}
                </p>

                <p className="text-center text-gray-500 mt-2">
                    Current Stock : {vehicle.quantity}
                </p>

                <div className="mt-6">

                    <label className="block mb-2 font-medium">
                        Quantity
                    </label>

                    <input
                        type="number"
                        min={1}
                        value={quantity}
                        onChange={(e) =>
                            setQuantity(Number(e.target.value))
                        }
                        className="w-full border rounded-xl p-3"
                    />

                </div>

                <div className="flex justify-end gap-3 mt-8">

                    <button
                        onClick={onClose}
                        disabled={loading}
                        className="border px-5 py-2 rounded-lg"
                    >
                        Cancel
                    </button>

                    <button
                        disabled={loading}
                        onClick={() => onConfirm(quantity)}
                        className="bg-green-600 hover:bg-green-700 text-white px-5 py-2 rounded-lg"
                    >
                        {loading ? "Restocking..." : "Restock"}
                    </button>

                </div>

            </div>

        </div>
    );
}