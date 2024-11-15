<script>
import {defineComponent} from "vue";
import HeaderComp from "@/components/HeaderComp.vue";
import HomeView from "@/views/HomeView.vue";

export default defineComponent({
  components: {
    HeaderComp,
    HomeView

  }
})
</script>

<template>
  <HeaderComp />
  <router-view />
</template>

<style scoped>

</style>
