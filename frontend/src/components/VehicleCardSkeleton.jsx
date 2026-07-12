export default function VehicleCardSkeleton() {
    return (
        <div className="bg-white rounded-2xl shadow-lg p-6 animate-pulse">

            <div className="h-20 w-20 bg-gray-200 rounded-full mx-auto mb-6"></div>

            <div className="h-6 bg-gray-200 rounded w-2/3 mx-auto"></div>

            <div className="h-4 bg-gray-200 rounded w-1/2 mx-auto mt-3"></div>

            <div className="space-y-4 mt-8">

                <div className="h-4 bg-gray-200 rounded"></div>

                <div className="h-4 bg-gray-200 rounded"></div>

                <div className="h-4 bg-gray-200 rounded"></div>

                <div className="h-4 bg-gray-200 rounded"></div>

            </div>

            <div className="h-12 bg-gray-200 rounded-xl mt-8"></div>

        </div>
    );
}