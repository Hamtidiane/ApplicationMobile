<template>
  <div>
    <h1>Liste des véhicules</h1>
    <div v-if="loading">Chargement des véhicules...</div>
    <ul v-else>
      <li v-for="vehicle in vehicles" :key="vehicle.id">
        {{ vehicle.brand }} - {{ vehicle.type }}
      </li>
    </ul>
    <div v-if="error" class="error">
      Il y a eu une erreur lors du chargement des véhicules.
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import { getAllVehicles } from "@/axios/vehiculeService"; // Votre service Axios

export default {
  setup() {
    const vehicles = ref([]);  // Liste des véhicules
    const loading = ref(true);  // Indicateur de chargement
    const error = ref(null);    // Gestion des erreurs

    // Appeler l'API pour récupérer les véhicules
    onMounted(async () => {
      try {
        vehicles.value = await getAllVehicles(); // Récupérer les véhicules
      } catch (err) {
        error.value = true; // En cas d'erreur
      } finally {
        loading.value = false; // Fin du chargement
      }
    });

    return { vehicles, loading, error }; // Retour des données et états
  }
};
</script>

<style scoped>
/* Vous pouvez ajouter du style ici */
.error {
  color: red;
}
</style>
