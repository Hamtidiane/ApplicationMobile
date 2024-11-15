<template>
  <div class="search-comp">
    <h2>Rechercher des Véhicules</h2>

    <div class="filters">
      <label for="type">Type :</label>
      <select id="type" v-model="selectedType">
        <option value="">Tous</option>
        <option v-for="type in uniqueTypes" :key="type" :value="type">{{ type }}</option>
      </select>

      <label for="brand">Marque :</label>
      <select id="brand" v-model="selectedBrand">
        <option value="">Toutes</option>
        <option v-for="brand in uniqueBrands" :key="brand" :value="brand">{{ brand }}</option>
      </select>

      <button @click="searchVehicles" class="search-button">Rechercher</button>
    </div>

    <div v-if="filteredVehicles.length > 0" class="results">
      <h3>Résultats :</h3>
      <ul>
        <li v-for="vehicle in filteredVehicles" :key="vehicle.id">
          {{ vehicle.brand }} - {{ vehicle.model }} ({{ vehicle.type }})
        </li>
      </ul>
    </div>
    <div v-else class="no-results">
      <p>Aucun véhicule trouvé pour les filtres sélectionnés.</p>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from "vue";
import { getAllVehicles} from "@/axios/vehiculeService.js";
// Importez ou définissez ici la méthode getAll

export default {
  name: "SearchComp",
  setup() {
    const vehicles = ref([]);
    const selectedType = ref("");
    const selectedBrand = ref("");
    const filteredVehicles = ref([]);

    // Charger les véhicules lors du montage du composant
    onMounted(async () => {
      vehicles.value = await getAllVehicles();
      filteredVehicles.value = vehicles.value;
    });

    // Récupération des types et marques uniques
    const uniqueTypes = computed(() => [...new Set(vehicles.value.map(v => v.type))]);
    const uniqueBrands = computed(() => [...new Set(vehicles.value.map(v => v.brand))]);

    // Filtrer les véhicules en fonction des critères
    const searchVehicles = () => {
      filteredVehicles.value = vehicles.value.filter(vehicle => {
        return (
            (selectedType.value === "" || vehicle.type === selectedType.value) &&
            (selectedBrand.value === "" || vehicle.brand === selectedBrand.value)
        );
      });
    };

    return {
      selectedType,
      selectedBrand,
      uniqueTypes,
      uniqueBrands,
      filteredVehicles,
      searchVehicles,
    };
  },
};
</script>

<style scoped>
.search-comp {
  text-align: center;
  max-width: 600px;
  margin: 0 auto;
}

.filters {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
  align-items: center;
  justify-content: center;
}

.filters label {
  font-weight: bold;
}

.search-button {
  padding: 0.5rem 1rem;
  background-color: #ff6b6b;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.search-button:hover {
  background-color: #ff4d4d;
}

.results, .no-results {
  margin-top: 1rem;
}

.no-results {
  color: #888;
}
</style>
