<script>
import HeaderComp from "@/components/HeaderComp.vue";
//import SearchComp from "@/components/SearchComp.vue";
import VehiculesComp from "@/components/VehiculesComp.vue";

export default {
  name: "HomePage",
  components: {
    VehiculesComp,
    HeaderComp,
    //SearchComp
  },
  data() {
    return {
      images: [
        "1.jpeg",
        "https://via.placeholder.com/800x400?text=Car+1",
        "https://via.placeholder.com/800x400?text=Car+2",
        "https://via.placeholder.com/800x400?text=Car+3",
      ],
      currentIndex: 0,
    };
  },
  methods: {
    nextSlide() {
      this.currentIndex = (this.currentIndex + 1) % this.images.length;
    },
    prevSlide() {
      this.currentIndex = (this.currentIndex - 1 + this.images.length) % this.images.length;
    },
  },
};
</script>

<template>
  <div class="home">
    <main class="main-content">
      <div class="carousel">
        <h1>mkjerhgmkjermkeknjrm</h1>
        <div class="carousel-inner" :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
          <img v-for="(image, index) in images" :key="index" :src="image" class="carousel-item" alt="imgs"/>
        </div>
        <button class="carousel-control prev" @click="prevSlide">❮</button>
        <button class="carousel-control next" @click="nextSlide">❯</button>
      </div>
     <!-- <SearchComp />-->
      <VehiculesComp />
    </main>
  </div>
</template>

<style scoped>
/* Global styles for the Home Page */
.home {
  text-align: center;
  font-family: Arial, sans-serif;
}

.main-content {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: calc(100vh - 70px); /* Adjust based on the header height */
}

/* Carousel styles */
.carousel {
  position: relative;
  width: 80%;
  max-width: 800px;
  overflow: hidden;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.carousel-inner {
  display: flex;
  transition: transform 0.5s ease-in-out;
}

.carousel-item {
  width: 100%;
  flex-shrink: 0;
}

.carousel-control {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: rgba(0, 0, 0, 0.5);
  color: #fff;
  border: none;
  padding: 10px;
  cursor: pointer;
  font-size: 1.5rem;
  z-index: 10;
  border-radius: 50%;
}

.carousel-control.prev {
  left: 10px;
}

.carousel-control.next {
  right: 10px;
}

.carousel-control:hover {
  background: rgba(0, 0, 0, 0.8);
}
</style>
