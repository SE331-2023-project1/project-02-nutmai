<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
</script>

<template>
  <header>
    <nav class="flex fixed top-0 left-0 right-0 justify-center text-2xl py-2 bg-white">
      <RouterLink :to="{name: 'student-list', query:{page:1}}" class="hover:bg-rose-100 px-3 py-1">Student List</RouterLink>|
      <RouterLink :to="{name: 'teacher-list', query:{page:1}}" class="hover:bg-rose-200 px-3 py-1">Teacher List</RouterLink>
    </nav>
  </header>
  <div class="flex h-[100vh] items-center justify-center py-20">
    <Suspense>
      <RouterView />
    </Suspense>
  </div>
</template>