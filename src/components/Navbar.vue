<script lang="ts" setup>
import { useUserStore } from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { RouterLink } from "vue-router";

const userStore = useUserStore();

const currentUser = storeToRefs(userStore).getCurrentUser;

const logout = () => {
  userStore.logout()
  window.location.href = "/";
};
</script>

<template>
  <header>
    <nav class="flex fixed top-0 left-0 right-0 justify-center text-2xl py-2 bg-white">
      <RouterLink :to="{ name: 'student-list', query: { page: 1 } }" class="hover:bg-rose-100 px-3 py-1"
        >Student List</RouterLink
      >|
      <RouterLink :to="{ name: 'teacher-list', query: { page: 1 } }" class="hover:bg-rose-200 px-3 py-1"
        >Teacher List</RouterLink
      >|
      <RouterLink :to="{name: 'user-profile'}" class="hover:bg-rose-200 px-3 py-1">Profile</RouterLink>|
      <RouterLink :to="{name: 'admin'}" class="hover:bg-rose-200 px-3 py-1">Admin</RouterLink>|
      <button class="hover:bg-rose-200 px-3 py-1" @click="logout">Logout</button>
    </nav>
  </header>
</template>
