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