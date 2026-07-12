import axiosInstance from "../services/axiosInstance";

export const getAllVehicles = async () => {
    const response = await axiosInstance.get("/vehicles");
    return response.data;
};

export const searchVehicles = async (filters) => {
    const params = {};

    if (filters.make) params.make = filters.make;
    if (filters.model) params.model = filters.model;
    if (filters.category && filters.category !== "ALL")
        params.category = filters.category;
    if (filters.minPrice) params.minPrice = filters.minPrice;
    if (filters.maxPrice) params.maxPrice = filters.maxPrice;

    const response = await axiosInstance.get("/vehicles/search", {
        params,
    });

    return response.data;
};

export const purchaseVehicle = async (id, quantity = 1) => {
    const response = await axiosInstance.post(
        `/vehicles/${id}/purchase`,
        {
            quantity,
        }
    );

    return response.data;
};

export const addVehicle = async (vehicle) => {
    const response = await axiosInstance.post("/vehicles", vehicle);
    return response.data;
};

export const updateVehicle = async (id, vehicle) => {
    const response = await axiosInstance.put(
        `/vehicles/${id}`,
        vehicle
    );

    return response.data;
};

export const deleteVehicle = async (id) => {
    await axiosInstance.delete(`/vehicles/${id}`);
};

export const restockVehicle = async (id, quantity) => {
    const response = await axiosInstance.post(
        `/vehicles/${id}/restock`,
        {
            quantity,
        }
    );

    return response.data;
};