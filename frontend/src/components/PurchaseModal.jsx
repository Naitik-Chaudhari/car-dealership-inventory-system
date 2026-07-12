import { X, ShoppingCart } from "lucide-react";

export default function PurchaseModal({
                                          open,
                                          vehicle,
                                          onClose,
                                          onConfirm,
                                          loading,
                                      }) {
    if (!open || !vehicle) return null;

    return (
        <div className="fixed inset-0 bg-black/40 flex justify-center items-center z-50">

            <div className="bg-white rounded-2xl shadow-xl w-full max-w-md p-6">

                <div className="flex justify-between items-center">

                    <h2 className="text-2xl font-bold text-gray-800">
                        Confirm Purchase
                    </h2>

                    <button onClick={onClose}>
                        <X className="text-gray-500 hover:text-red-500" />
                    </button>

                </div>

                <div className="mt-6 space-y-4">

                    <div className="flex justify-center">

                        <div className="bg-blue-100 p-5 rounded-full">

                            <ShoppingCart
                                size={40}
                                className="text-blue-600"
                            />

                        </div>

                    </div>

                    <div className="text-center">

                        <h3 className="text-xl font-semibold">

                            {vehicle.make} {vehicle.model}

                        </h3>

                        <p className="text-gray-500">

                            {vehicle.category}

                        </p>

                    </div>

                    <div className="border rounded-xl p-4 space-y-3">

                        <div className="flex justify-between">

                            <span>Price</span>

                            <span className="font-semibold text-blue-600">

                {vehicle.price.toLocaleString("en-IN", {
                    style: "currency",
                    currency: "INR",
                })}

              </span>

                        </div>

                        <div className="flex justify-between">

                            <span>Available</span>

                            <span className="font-semibold">

                {vehicle.quantity}

              </span>

                        </div>

                        <div className="flex justify-between">

                            <span>Purchase Quantity</span>

                            <span className="font-semibold">

                1

              </span>

                        </div>

                    </div>

                    <p className="text-sm text-center text-gray-500">

                        Are you sure you want to purchase this vehicle?

                    </p>

                </div>

                <div className="flex justify-end gap-4 mt-8">

                    <button
                        onClick={onClose}
                        disabled={loading}
                        className="px-5 py-2 border rounded-lg hover:bg-gray-100"
                    >
                        Cancel
                    </button>

                    <button
                        disabled={loading}
                        onClick={onConfirm}
                        className="bg-blue-600 hover:bg-blue-700 text-white px-5 py-2 rounded-lg"
                    >
                        {loading
                            ? "Purchasing..."
                            : "Confirm Purchase"}
                    </button>

                </div>

            </div>

        </div>
    );
}