import axiosInstance from "@/axios/axiosInstance";

export async function getAllVehicles() {
    try {
        const response = await axiosInstance.get("/vehicles");
        return response.data;
    } catch (error) {
        console.error("Erreur lors de la récupération des véhicules :", error);
        throw error;
    }
}

export async function createVehicle(){
    try {
        const response = await axiosInstance.post("/", vehicle);  // Endpoint pour créer un véhicule
        return response.data;
    } catch (error) {
        console.error("Erreur lors de la création du véhicule", error);
        throw error;
    }
}

export async function updateVehicle(){
    try {
        const response = await axiosInstance.put(`/` + id, updatedVehicle);  // Endpoint pour mettre à jour un véhicule
        return response.data;
    } catch (error) {
        console.error("Erreur lors de la mise à jour du véhicule", error);
        throw error;
    }
}

export async function deleteVehicle()   {
    try {
        await axiosInstance.delete(`/` + id);  // Endpoint pour supprimer un véhicule
    } catch (error) {
        console.error("Erreur lors de la suppression du véhicule", error);
        throw error;
    }
}

export async function getVehicleById(id) {
    try {
        const response = await axiosInstance.get(`/vehicles/${id}`);
        return response.data;
    } catch (error) {
        console.error("Erreur lors de la récupération du véhicule :", error);
        throw error;
    }
}
