import { AlertTriangle } from "lucide-react";

export default function DeleteModal({
                                        open,
                                        vehicle,
                                        loading,
                                        onClose,
                                        onConfirm,
                                    }) {
    if (!open || !vehicle) return null;

    return (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 px-4">
            <div className="bg-white rounded-2xl w-full max-w-md shadow-xl p-6">

                <div className="flex justify-center mb-5">
                    <div className="bg-red-100 p-4 rounded-full">
                        <AlertTriangle
                            className="text-red-600"
                            size={40}
                        />
                    </div>
                </div>

                <h2 className="text-2xl font-bold text-center">
                    Delete Vehicle
                </h2>

                <p className="text-center text-gray-500 mt-3">
                    Are you sure you want to delete
                </p>

                <p className="text-center font-semibold mt-2">
                    {vehicle.make} {vehicle.model}
                </p>

                <div className="flex justify-end gap-3 mt-8">

                    <button
                        onClick={onClose}
                        disabled={loading}
                        className="border px-5 py-2 rounded-lg hover:bg-gray-100"
                    >
                        Cancel
                    </button>

                    <button
                        disabled={loading}
                        onClick={onConfirm}
                        className="bg-red-600 hover:bg-red-700 text-white px-5 py-2 rounded-lg"
                    >
                        {loading ? "Deleting..." : "Delete"}
                    </button>

                </div>

            </div>
        </div>
    );
}