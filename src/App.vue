<script setup lang="ts">
import { createApp } from "vue";
import { RouterView } from "vue-router";
import ToastPlugin from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';
const app = createApp({});
app.use(ToastPlugin, {position: 'top-right'});
app.mount('#app');
</script>

<template>
  <div class="flex h-[100vh] justify-center py-32 lg:px-20">
    <Suspense>
      <RouterView />
    </Suspense>
  </div>
</template>