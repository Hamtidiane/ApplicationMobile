<template>
  <div v-if="vehicle">
    <h2>Détails du véhicule</h2>
    <p><strong>Immatriculation :</strong> {{ vehicle.immatriculation }}</p>
    <p><strong>Marque :</strong> {{ vehicle.marque }}</p>
    <p><strong>Modèle :</strong> {{ vehicle.modele }}</p>
    <p><strong>Année :</strong> {{ vehicle.annee }}</p>
    <p><strong>Couleur :</strong> {{ vehicle.couleur }}</p>
    <!-- Ajoutez d'autres informations si nécessaire -->
  </div>
  <p v-else-if="error" style="color: red;">{{ error }}</p>
  <p v-else>Chargement des informations...</p>
</template>

<script>
import axios from 'axios'

export default {
  name: 'VehicleDetail',
  data() {
    return {
      vehicle: null,
      error: null
    }
  },
  methods: {
    async fetchVehicle() {
      const vehicleId = this.$route.params.id // Récupère l'ID depuis les paramètres de l'URL
      try {
        const response = await axios.get(`http://localhost:8081/vehicules/${vehicleId}`)
        this.vehicle = response.data
        this.error = null
      } catch (err) {
        this.vehicle = null
        this.error = "Erreur lors de la récupération du véhicule. Veuillez vérifier l'ID."
      }
    }
  },
  mounted() {
    this.fetchVehicle()
  }
}
</script>

<style scoped>
/* Ajoutez des styles spécifiques au composant ici */
</style>
