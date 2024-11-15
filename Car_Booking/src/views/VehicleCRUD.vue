<template>
  <div>
    <h1>Gestion des Véhicules</h1>

    <!-- Bouton pour créer un véhicule -->
    <button @click="openCreateForm">Créer un véhicule</button>

    <!-- Formulaire de création / modification -->
    <div v-if="showForm">
      <h3>{{ editingVehicle ? "Modifier" : "Ajouter" }} un véhicule</h3>
      <form @submit.prevent="handleSubmit">
        <label for="type">Type de véhicule :</label>
        <input v-model="vehicleForm.type" id="type" type="text" required />

        <label for="registration">Immatriculation :</label>
        <input v-model="vehicleForm.registration" id="registration" type="text" required />

        <label for="brand">Marque :</label>
        <input v-model="vehicleForm.brand" id="brand" type="text" required />

        <label for="model">Modèle :</label>
        <input v-model="vehicleForm.model" id="model" type="text" required />

        <label for="color">Couleur :</label>
        <input v-model="vehicleForm.color" id="color" type="text" required />

        <label for="mileage">Kilométrage :</label>
        <input v-model="vehicleForm.mileage" id="mileage" type="number" step="0.01" required />

        <label for="tax_horses">Puissance fiscale :</label>
        <input v-model="vehicleForm.tax_horses" id="tax_horses" type="number" required />

        <label for="mileagePrice">Prix au kilomètre :</label>
        <input v-model="vehicleForm.mileagePrice" id="mileagePrice" type="number" step="0.01" required />

        <label for="basePrice">Prix de base :</label>
        <input v-model="vehicleForm.basePrice" id="basePrice" type="number" step="0.01" required />

        <label for="unavailableStartDate">Date début indisponibilité :</label>
        <input v-model="vehicleForm.unavailableStartDate" id="unavailableStartDate" type="date" />

        <label for="unavailableEndDate">Date fin indisponibilité :</label>
        <input v-model="vehicleForm.unavailableEndDate" id="unavailableEndDate" type="date" />

        <button type="submit">{{ editingVehicle ? "Mettre à jour" : "Ajouter" }} le véhicule</button>
        <button @click="closeForm">Annuler</button>
      </form>
    </div>

    <!-- Liste des véhicules -->
    <div v-if="vehicles.length">
      <h3>Liste des véhicules</h3>
      <ul>
        <li v-for="vehicle in vehicles" :key="vehicle.id">
          {{ vehicle.type }} - {{ vehicle.registration }} - {{ vehicle.brand }} - {{ vehicle.model }} - {{ vehicle.color }}
          <button @click="editVehicle(vehicle)">Modifier</button>
          <button @click="deleteVehicle(vehicle.id)">Supprimer</button>
        </li>
      </ul>
    </div>

    <div v-else>
      <p>Aucun véhicule à afficher</p>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import { getAllVehicles, createVehicle, updateVehicle, deleteVehicle } from "@/axios/vehiculeService";

export default {
  setup() {
    const vehicles = ref([]); // Liste des véhicules
    const vehicleForm = ref({
      type: "",
      registration: "",
      brand: "",
      model: "",
      color: "",
      mileage: null,
      tax_horses: null,
      mileagePrice: null,
      basePrice: null,
      unavailableStartDate: "",
      unavailableEndDate: "",
    });
    const editingVehicle = ref(null); // Véhicule en cours d'édition
    const showForm = ref(false); // Contrôle de l'affichage du formulaire

    // Charger tous les véhicules
    const loadVehicles = async () => {
      try {
        vehicles.value = await getAllVehicles();
      } catch (error) {
        console.error("Erreur lors du chargement des véhicules :", error);
      }
    };

    // Créer ou mettre à jour un véhicule
    const handleSubmit = async () => {
      try {
        if (editingVehicle.value) {
          await updateVehicle(editingVehicle.value.id, vehicleForm.value); // Mise à jour
        } else {
          await createVehicle(vehicleForm.value); // Création
        }
        await loadVehicles();
        closeForm();
      } catch (error) {
        console.error("Erreur lors de l'enregistrement du véhicule :", error);
      }
    };

    // Ouvrir le formulaire de création
    const openCreateForm = () => {
      resetForm();
      showForm.value = true;
    };

    // Éditer un véhicule
    const editVehicle = (vehicle) => {
      editingVehicle.value = vehicle;
      vehicleForm.value = {...vehicle};
      showForm.value = true;
    };

    // Supprimer un véhicule
    const deleteVehicle = async (id) => {
      try {
        await deleteVehicle(id);
        await loadVehicles();
      } catch (error) {
        console.error("Erreur lors de la suppression du véhicule :", error);
      }
    };

    // Réinitialiser le formulaire
    const resetForm = () => {
      editingVehicle.value = null;
      vehicleForm.value = {
        type: "",
        registration: "",
        brand: "",
        model: "",
        color: "",
        mileage: null,
        tax_horses: null,
        mileagePrice: null,
        basePrice: null,
        unavailableStartDate: "",
        unavailableEndDate: "",
      };
    };

    // Annuler et fermer le formulaire
    const closeForm = () => {
      resetForm();
      showForm.value = false;
    };

    onMounted(loadVehicles);

    return {
      vehicles,
      vehicleForm,
      editingVehicle,
      showForm,
      handleSubmit,
      openCreateForm,
      editVehicle,
      deleteVehicle,
      closeForm,
    };
  },
};
</script>
