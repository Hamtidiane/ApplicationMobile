<template>
  <header class="header">
    <nav class="navbar">
      <h1 class="logo">HLRentals</h1>
      <ul class="nav-links">
        <li><a href="#home">Home</a></li>
        <li><a href="vehicule">Vehicule</a></li>
        <li><a href="#about">A Propos</a></li>
        <li><a href="#contact">Contacts</a></li>
        <li><a href="#login" class="btn btn-primary">Login</a></li>
      </ul>
    </nav>
  </header>
</template>

<script>
export default {
  name: "HeaderComp",
};
</script>

<style scoped>
.header {
  background-color: #333;
  padding: 1rem;
  color: #fff;
  display:block ruby ;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  font-size: 1.5rem;
  font-weight: bold;
  color: #ff6b6b;
}

.nav-links {
  list-style-type: none;
  display: flex;
  gap: 1rem;
}

.nav-links li {
  margin: 0;
}

.nav-links a {
  color: #fff;
  text-decoration: none;
  font-weight: 500;
  padding: 0.5rem 1rem;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.nav-links a:hover {
  background-color: #ff6b6b;
}

.btn {
  padding: 0.5rem 1rem;
  border-radius: 5px;
}

.btn-primary {
  background-color: #ff6b6b;
  color: #fff;
}

.btn-primary:hover {
  background-color: #ff4d4d;
}
</style>
