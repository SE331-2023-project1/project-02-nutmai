<script lang="ts" setup>
import ProfileUpload from "@/components/ProfileUpload.vue";
import router from "@/router";
import { registerStudentService } from "@/services/userService";
import { useUserStore } from "@/stores/userStore";
import axiosClient from "@/utils/axiosClient";
import { useToast } from "vue-toast-notification";

let file: File | undefined = undefined;

let studentId: string | undefined = undefined;
let firstName: string | undefined = undefined;
let lastName: string | undefined = undefined;
let department: string | undefined = undefined;
let username: string | undefined = undefined;
let password: string | undefined = undefined;
let confirmPassword: string | undefined = undefined;

const $toast = useToast();
const userStore = useUserStore();

async function handleCreateAccount() {
  if (!file) {
    $toast.error("Please upload a profile picture", { position: "top-right" });
    return;
  }
  if (password !== confirmPassword) {
    $toast.error("Passwords do not match", { position: "top-right" });
    return;
  }
  const reader = new FileReader();
  reader.onload = async () => {
    const base64 = reader.result;
    const user = await registerStudentService({
      id: studentId as string,
      img: base64 as string,
      name: firstName as string,
      surname: lastName as string,
      department: department as string,
      username: username as string,
      password: password as string,
      role: "STUDENT",
    });
    if (user.token) {
      localStorage.setItem("user", JSON.stringify(user));
      userStore.setCurrentUser(user);
      $toast.success("Account created successfully", { position: "top-right" });
      return window.location.href = "/student-list?page=1"
    }
    $toast.error("Account creation failed", { position: "top-right" });
  };
  reader.readAsDataURL(file as File);
}
</script>

<template>
  <div class="container max-w-lg mx-auto flex-1 flex flex-col items-center justify-center px-2">
    <div class="bg-white px-6 py-8 rounded shadow-md text-black w-full">
      <h1 class="text-3xl text-center">Create Student Account</h1>
      <ProfileUpload :file="file" @update:file="file = $event" />
      <input
        type="text"
        class="block border border-grey-light w-full p-3 rounded mb-4"
        name="studentId"
        v-model="studentId"
        placeholder="Student ID"
      />
      <div class="flex gap-2">
        <input
          type="text"
          class="block border border-grey-light w-full p-3 rounded mb-4"
          name="firstname"
          v-model="firstName"
          placeholder="First Name"
        />
        <input
          type="text"
          class="block border border-grey-light w-full p-3 rounded mb-4"
          name="lastname"
          v-model="lastName"
          placeholder="Last Name"
        />
      </div>
      <input
        type="text"
        class="block border border-grey-light w-full p-3 rounded mb-4"
        name="department"
        v-model="department"
        placeholder="Department"
      />
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
      <input
        type="password"
        class="block border border-grey-light w-full p-3 rounded mb-4"
        name="confirmpassword"
        v-model="confirmPassword"
        placeholder="Confirm Password"
      />

      <button
        type="submit"
        class="w-full text-center py-3 rounded bg-green-500 text-white hover:bg-green-700 focus:outline-none my-1"
        v-on:click="handleCreateAccount"
      >
        Create Account
      </button>
    </div>

    <div class="text-grey-dark mt-6">
      Already have an account?
      <a class="no-underline border-b border-blue text-blue-500" href="/"> Log in </a>.
    </div>
  </div>
</template>
