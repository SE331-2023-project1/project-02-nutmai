<script lang="ts" setup>

import Navbar from "@/components/Navbar.vue";
import type { UserModel } from "@/models/userModel";
import router from "@/router";
import { getStudentsService } from "@/services/studentService";
import { getTeachersService } from "@/services/teacherService";
import nProgress from "nprogress";
import { ref, type Ref } from "vue";
import { useToast } from "vue-toast-notification";
import { updateAdvisorService, createUserService } from "@/services/adminService";
import ProfileUpload from "@/components/ProfileUpload.vue";

const $toast = useToast();

const students: Ref<Array<UserModel>> = ref([]);
const advisors: Ref<Array<UserModel>> = ref([]);

nProgress.start();
getStudentsService()
  .then(res => {
    students.value = res.data;
  })
  .catch(() => {
    router.push({ name: "network-error" });
  })
  .finally(() => {
    nProgress.done();
  });

nProgress.start();
getTeachersService()
  .then(res => {
    advisors.value = res.data;
  })
  .catch(() => {
    router.push({ name: "network-error" });
  })
  .finally(() => {
    nProgress.done();
  });

let student = ref<any>("");
let advisor = ref<any>("");

async function handleUpdateAccount() {
  const res = await updateAdvisorService({
    studentId: student.value,
    advisorId: advisor.value,
  });
  if (res.status === 200) {
    return $toast.success("Updated Advisor successfully", {position: "top-right"});
  }
  $toast.error("Failed to update Advisor", {position: "top-right"});
}

let file: File | undefined = undefined;

let studentId: string | undefined = undefined;
let academicPosition: string | undefined = undefined;
let firstName: string | undefined = undefined;
let lastName: string | undefined = undefined;
let department: string | undefined = undefined;
let username: string | undefined = undefined;
let password: string | undefined = undefined;

async function handleCreateAccount() {
  if (!file) {
    $toast.error("Please upload a profile picture", { position: "top-right" });
    return;
  }
  const reader = new FileReader();
  reader.onload = async () => {
    const base64 = reader.result;
    const res = await createUserService({
      id: studentId as string,
      img: base64 as string,
      name: firstName as string,
      surname: lastName as string,
      department: department as string,
      username: username as string,
      password: password as string,
      academicPosition: academicPosition as string,
      title: academicPosition as string,
      role: "TEACHER",
    });
    if (res.status === 200) {
      $toast.success("Created Teacher Account successfully", { position: "top-right" });
      return window.location.reload();
    }
    $toast.error("Failed to create Teacher Account", { position: "top-right" });
  };
  reader.readAsDataURL(file as File);
}

</script>

<template>
  <Navbar />
  <!-- Select Student and Advisor -->
  <div class="container max-w-lg mx-auto flex-1 flex flex-col items-center justify-center px-2">
    <div class="bg-white px-6 py-8 rounded shadow-md text-black w-full">
      <h1 class="text-3xl mb-6 text-center">Select Student and Advisor</h1>
      <div class="flex gap-2">
        <div class="flex flex-col">
          <label class="block text-grey-darker text-sm font-bold mb-2"> Student </label>
          <select
            class="block border border-grey-light w-full p-3 rounded mb-4"
            name="student"
            v-model="student"
            placeholder="Student"
          >
            <option v-for="student in students" :key="student.id" :value="student.id">
              {{ student.name }} {{ student.surname }}
            </option>
          </select>
        </div>
        <div class="flex flex-col">
          <label class="block text-grey-darker text-sm font-bold mb-2"> Advisor </label>
          <select
            class="block border border-grey-light w-full p-3 rounded mb-4"
            name="advisor"
            v-model="advisor"
            placeholder="Advisor"
          >
            <option v-for="advisor in advisors" :key="advisor.id" :value="advisor.id">
              {{ advisor.name }} {{ advisor.surname }}
            </option>
          </select>
        </div>
      </div>
      <button
        type="submit"
        class="w-full text-center py-3 rounded bg-green-500 text-white hover:bg-green-700 focus:outline-none my-1"
        v-on:click="handleUpdateAccount"
      >
        Save
      </button>
    </div>
  </div>

  <!-- Create Teacher Account -->
  <div class="container max-w-lg mx-auto flex-1 flex flex-col items-center justify-center px-2">
    <div class="bg-white px-6 py-8 rounded shadow-md text-black w-full">
      <h1 class="text-3xl text-center">Create Teacher Account</h1>
      <ProfileUpload :file="file" @update:file="file = $event" />
      <input
        type="text"
        class="block border border-grey-light w-full p-3 rounded mb-4"
        name="studentId"
        v-model="studentId"
        placeholder="Teacher ID"
      />
      <input
        type="text"
        class="block border border-grey-light w-full p-3 rounded mb-4"
        name="academicPosition"
        v-model="academicPosition"
        placeholder="Academic Position"
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

      <button
        type="submit"
        class="w-full text-center py-3 rounded bg-green-500 text-white hover:bg-green-700 focus:outline-none my-1"
        v-on:click="handleCreateAccount"
      >
        Create Account
      </button>
    </div>
  </div>

</template>
