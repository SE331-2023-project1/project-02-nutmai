<script lang="ts" setup>
import { loginService } from '@/services/userService';
import { useUserStore } from '@/stores/userStore';
import { useToast } from 'vue-toast-notification';

let username: string | undefined = undefined;
let password: string | undefined = undefined;

const $toast = useToast();
const userStore = useUserStore();

async function handleLogin(){
  const user = await loginService(
    username as string,
    password as string,
  );
  if (user.token) {
    localStorage.setItem("user", JSON.stringify(user));
    userStore.setCurrentUser(user);
    $toast.success("Login successfully", { position: "top-right" });
    return window.location.href = "/student-list?page=1"
  }
  $toast.error("Login failed", { position: "top-right" });
}
</script>

<template>
  <div class="container max-w-lg mx-auto flex-1 flex flex-col items-center justify-center px-2">
    <div class="bg-white px-6 py-8 rounded shadow-md text-black w-full">
      <h1 class="mb-8 text-3xl text-center">Sign in to your account</h1>
      <input
        type="text"
        class="block border border-grey-light w-full p-3 rounded mb-4"
        name="username"
        v-model="username"
        placeholder="Username"
      />
      <input
        type="password"
        class="block border border-grey-light w-full p-3 rounded mb-4"
        name="password"
        v-model="password"
        placeholder="Password"
      />
      <button
        type="submit"
        class="w-full text-center py-3 rounded bg-green-500 text-white hover:bg-green-700 focus:outline-none my-1"
        v-on:click="handleLogin"
      >
        Login
      </button>
    </div>

    <div class="text-grey-dark mt-6">
      Student? Don't have an account?
      <a class="no-underline border-b border-blue text-blue-500" href="./register"> Register </a>.
    </div>
  </div>
</template>
