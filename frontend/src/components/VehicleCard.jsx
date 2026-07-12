import {
    CarFront,
    PackageCheck,
    PackageX,
} from "lucide-react";

export default function VehicleCard({vehicle, onPurchase, purchasing,}) {
    const stockColor = () => {
        if (vehicle.quantity === 0)
            return "bg-red-100 text-red-700";

        if (vehicle.quantity <= 3)
            return "bg-yellow-100 text-yellow-700";

        return "bg-green-100 text-green-700";
    };

    const stockText = () => {
        if (vehicle.quantity === 0)
            return "Out of Stock";

        if (vehicle.quantity <= 3)
            return "Low Stock";

        return "In Stock";
    };

    return (
        <div className="bg-white rounded-2xl shadow-lg hover:shadow-xl transition duration-300 p-6">

            <div className="flex justify-center mb-5">

                <div className="bg-blue-100 p-5 rounded-full">
                    <CarFront
                        size={45}
                        className="text-blue-600"
                    />
                </div>

            </div>

            <h2 className="text-2xl font-bold text-center">
                {vehicle.make}
            </h2>

            <p className="text-center text-gray-500 mb-4">
                {vehicle.model}
            </p>

            <div className="space-y-3">

                <div className="flex justify-between">
          <span className="text-gray-500">
            Category
          </span>

                    <span className="font-semibold">
            {vehicle.category}
          </span>
                </div>

                <div className="flex justify-between">
          <span className="text-gray-500">
            Price
          </span>

                    <span className="font-bold text-blue-600">
            {vehicle.price.toLocaleString("en-IN", {
                style: "currency",
                currency: "INR",
            })}
          </span>
                </div>

                <div className="flex justify-between items-center">

          <span className="text-gray-500">
            Stock
          </span>

                    <span
                        className={`px-3 py-1 rounded-full text-sm font-semibold ${stockColor()}`}
                    >
            {stockText()}
          </span>

                </div>

                <div className="flex justify-between">

          <span className="text-gray-500">
            Available
          </span>

                    <span className="font-semibold">
            {vehicle.quantity}
          </span>

                </div>

            </div>

            <button
                disabled={vehicle.quantity === 0 || purchasing}
                onClick={() => onPurchase(vehicle.id)}
                className={`mt-6 w-full py-3 rounded-xl font-semibold transition flex justify-center items-center gap-2
          ${
                    vehicle.quantity === 0
                        ? "bg-gray-300 cursor-not-allowed"
                        : "bg-blue-600 hover:bg-blue-700 text-white"
                }`}
            >
                {purchasing ? (
                <>
                    Purchasing...
                </>
                ) : vehicle.quantity === 0 ? (
                    <>
                        <PackageX size={18} />
                        Out of Stock
                    </>
                ) : (
                    <>
                        <PackageCheck size={18} />
                        Purchase
                    </>
                )}
            </button>
        </div>
    );
}