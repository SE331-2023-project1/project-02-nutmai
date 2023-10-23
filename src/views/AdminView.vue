<script lang="ts" setup>

import Navbar from "@/components/Navbar.vue";
import type { UserModel } from "@/models/userModel";
import router from "@/router";
import { getStudentsService } from "@/services/studentService";
import { getTeachersService } from "@/services/teacherService";
import nProgress from "nprogress";
import { ref, type Ref } from "vue";
import { useToast } from "vue-toast-notification";
import { updateAdvisorService } from "@/services/adminService";

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
    $toast.success("Updated Advisor successfully", {position: "top-right"});
    return router.push({ name: "admin" });
  }
  $toast.error("Failed to update Advisor", {position: "top-right"});
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
</template>
