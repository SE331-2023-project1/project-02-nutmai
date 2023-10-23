<script lang="ts" setup>
import Navbar from "@/components/Navbar.vue";
import type { UserModel } from "@/models/userModel";
import router from "@/router";
import { getTeacherById } from "@/services/teacherService";
import { useUserStore } from "@/stores";
import nProgress from "nprogress";
import { storeToRefs } from "pinia";
import { ref, type Ref } from "vue";
import { useToast } from "vue-toast-notification";

let teacher: Ref<UserModel | undefined> = ref(undefined);
const currentUser = storeToRefs(useUserStore()).getCurrentUser;

nProgress.start();
getTeacherById(currentUser.value.id)
  .then((res) => {
    teacher.value = res;
  })
  .catch(() => {
    router.push({ name: "network-error" });
  })
  .finally(() => {
    nProgress.done();
  });
</script>

<template>
  <Navbar />
  <div class="container mx-auto py-8">
    <h1 class="text-3xl font-semibold mb-4">My Advisees</h1>
    <div v-if="teacher && teacher.advisees && teacher.advisees.length > 0">
      <ul>
        <li v-for="advisee in teacher.advisees" :key="advisee.id">
          <div class="bg-white rounded shadow-md p-4 mb-4">
            <h2 class="text-xl font-semibold">{{ `${advisee.title || ""} ${advisee.name} ${advisee.surname}` }}</h2>
            <p class="text-gray-600">{{ `${advisee.department}` }}</p>
            <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4"
              @click="router.push({ name: 'student-detail', params: { id: advisee.id } })"
            >
              View Advisee
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div v-else>
      <p>No Advisees found.</p>
    </div>
  </div>
</template>
