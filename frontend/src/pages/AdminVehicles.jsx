import { useEffect, useState } from "react";
import { Plus, Pencil, Trash2, PackagePlus } from "lucide-react";
import { toast } from "react-hot-toast";

import Navbar from "../components/Navbar";
import VehicleFormModal from "../components/VehicleFormModal";
import DeleteModal from "../components/DeleteModal";
import RestockModal from "../components/RestockModal";

import {
    getAllVehicles,
    addVehicle,
    updateVehicle,
    deleteVehicle,
    restockVehicle,
} from "../api/vehicleApi";

export default function AdminVehicles() {
    const [vehicles, setVehicles] = useState([]);
    const [loading, setLoading] = useState(true);

    const [showFormModal, setShowFormModal] = useState(false);
    const [selectedVehicle, setSelectedVehicle] = useState(null);
    const [saving, setSaving] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [showRestockModal, setShowRestockModal] = useState(false);
    const [actionLoading, setActionLoading] = useState(false);


    useEffect(() => {
        loadVehicles();
    }, []);

    const loadVehicles = async () => {
        try {
            setLoading(true);

            const data = await getAllVehicles();

            setVehicles(data);
        } catch {
            toast.error("Failed to load vehicles.");
        } finally {
            setLoading(false);
        }
    };

    const handleAdd = () => {
        setSelectedVehicle(null);
        setShowFormModal(true);
    };

    const handleEdit = (vehicle) => {
        setSelectedVehicle(vehicle);
        setShowFormModal(true);
    };

    const handleDelete = (vehicle) => {
        setSelectedVehicle(vehicle);
        setShowDeleteModal(true);
    };

    const handleRestock = (vehicle) => {
        setSelectedVehicle(vehicle);
        setShowRestockModal(true);
    };

    const handleSave = async (formData) => {
        try {
            setSaving(true);

            if (selectedVehicle) {
                const updatedVehicle = await updateVehicle(
                    selectedVehicle.id,
                    formData
                );

                setVehicles((previousVehicles) =>
                    previousVehicles.map((vehicle) =>
                        vehicle.id === updatedVehicle.id
                            ? updatedVehicle
                            : vehicle
                    )
                );

                toast.success("Vehicle updated successfully.");
            } else {
                const newVehicle = await addVehicle(formData);

                setVehicles((previousVehicles) => [
                    ...previousVehicles,
                    newVehicle,
                ]);

                toast.success("Vehicle added successfully.");
            }

            setShowFormModal(false);
            setSelectedVehicle(null);
        } catch (error) {
            if (error.response?.status === 400) {
                const errors = error.response.data.errors;

                if (errors) {
                    Object.values(errors).forEach((message) =>
                        toast.error(message)
                    );
                } else {
                    toast.error(error.response.data.message);
                }
            } else if (error.response?.status === 403) {
                toast.error("You are not authorized.");
            } else if (error.response?.status === 404) {
                toast.error(error.response.data.message);
            } else if (error.response?.status === 409) {
                toast.error(error.response.data.message);
            } else {
                toast.error("Operation failed.");
            }
        } finally {
            setSaving(false);
        }
    };

    const confirmDelete = async () => {
        try {
            setActionLoading(true);

            await deleteVehicle(selectedVehicle.id);

            setVehicles((previousVehicles) =>
                previousVehicles.filter(
                    (vehicle) =>
                        vehicle.id !== selectedVehicle.id
                )
            );

            toast.success("Vehicle deleted.");

            setShowDeleteModal(false);
        } catch (error) {
            if (error.response?.status === 404)
                toast.error(error.response.data.message);
            else if (error.response?.status === 403)
                toast.error("Unauthorized.");
            else toast.error("Delete failed.");
        } finally {
            setActionLoading(false);
        }
    };

    const confirmRestock = async (quantity) => {
        try {
            setActionLoading(true);

            const updatedVehicle =
                await restockVehicle(
                    selectedVehicle.id,
                    quantity
                );

            setVehicles((previousVehicles) =>
                previousVehicles.map((vehicle) =>
                    vehicle.id === updatedVehicle.id
                        ? updatedVehicle
                        : vehicle
                )
            );

            toast.success("Vehicle restocked.");

            setShowRestockModal(false);
        } catch (error) {
            if (error.response?.status === 400) {
                const errors = error.response.data.errors;

                if (errors)
                    Object.values(errors).forEach((message) =>
                        toast.error(message)
                    );
                else
                    toast.error(error.response.data.message);
            } else if (error.response?.status === 404)
                toast.error(error.response.data.message);
            else if (error.response?.status === 403)
                toast.error("Unauthorized.");
            else toast.error("Restock failed.");
        } finally {
            setActionLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-slate-100">
            <Navbar />

            <div className="max-w-7xl mx-auto px-6 py-8">
                <div className="flex justify-between items-center mb-8">
                    <div>
                        <h1 className="text-4xl font-bold text-gray-800">
                            Vehicle Management
                        </h1>

                        <p className="text-gray-500 mt-2">
                            Manage dealership inventory.
                        </p>
                    </div>

                    <button
                        onClick={handleAdd}
                        className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-5 py-3 rounded-xl"
                    >
                        <Plus size={18} />
                        Add Vehicle
                    </button>
                </div>

                <div className="bg-white rounded-2xl shadow-lg overflow-hidden">
                    <table className="w-full">
                        <thead className="bg-slate-200">
                        <tr>
                            <th className="p-4 text-left">Make</th>
                            <th className="p-4 text-left">Model</th>
                            <th className="p-4 text-left">Category</th>
                            <th className="p-4 text-left">Price</th>
                            <th className="p-4 text-left">Stock</th>
                            <th className="p-4 text-center">Actions</th>
                        </tr>
                        </thead>

                        <tbody>
                        {loading ? (
                            <tr>
                                <td
                                    colSpan="6"
                                    className="text-center py-10"
                                >
                                    Loading...
                                </td>
                            </tr>
                        ) : vehicles.length === 0 ? (
                            <tr>
                                <td
                                    colSpan="6"
                                    className="text-center py-10"
                                >
                                    No vehicles found.
                                </td>
                            </tr>
                        ) : (
                            vehicles.map((vehicle) => (
                                <tr
                                    key={vehicle.id}
                                    className="border-t hover:bg-slate-50"
                                >
                                    <td className="p-4 font-semibold">
                                        {vehicle.make}
                                    </td>

                                    <td className="p-4">
                                        {vehicle.model}
                                    </td>

                                    <td className="p-4">
                                        {vehicle.category}
                                    </td>

                                    <td className="p-4 font-semibold text-blue-600">
                                        {vehicle.price.toLocaleString("en-IN", {
                                            style: "currency",
                                            currency: "INR",
                                        })}
                                    </td>

                                    <td className="p-4">
                                        {vehicle.quantity}
                                    </td>

                                    <td className="p-4">
                                        <div className="flex justify-center gap-2">
                                            <button
                                                onClick={() => handleEdit(vehicle)}
                                                className="bg-yellow-500 hover:bg-yellow-600 text-white p-2 rounded-lg"
                                            >
                                                <Pencil size={18} />
                                            </button>

                                            <button
                                                onClick={() =>
                                                    handleRestock(vehicle)
                                                }
                                                className="bg-green-600 hover:bg-green-700 text-white p-2 rounded-lg"
                                            >
                                                <PackagePlus size={18} />
                                            </button>

                                            <button
                                                onClick={() =>
                                                    handleDelete(vehicle)
                                                }
                                                className="bg-red-600 hover:bg-red-700 text-white p-2 rounded-lg"
                                            >
                                                <Trash2 size={18} />
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))
                        )}
                        </tbody>
                    </table>
                </div>
            </div>

            <VehicleFormModal
                open={showFormModal}
                vehicle={selectedVehicle}
                loading={saving}
                onClose={() => {
                    setShowFormModal(false);
                    setSelectedVehicle(null);
                }}
                onSave={handleSave}
            />

            <DeleteModal
                open={showDeleteModal}
                vehicle={selectedVehicle}
                loading={actionLoading}
                onClose={() => setShowDeleteModal(false)}
                onConfirm={confirmDelete}
            />

            <RestockModal
                open={showRestockModal}
                vehicle={selectedVehicle}
                loading={actionLoading}
                onClose={() => setShowRestockModal(false)}
                onConfirm={confirmRestock}
            />
        </div>
    );
}