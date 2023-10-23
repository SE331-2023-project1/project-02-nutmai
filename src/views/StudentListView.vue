<script setup lang="ts">
import PersonCard from "@/components/PersonCard.vue";
import nProgress from "nprogress";
import { computed, ref, type Ref } from "vue";
import { getStudentsService } from "@/services/studentService";
import router from "@/router";
import { onBeforeRouteUpdate } from "vue-router";
import type { UserModel } from "@/models/userModel";
import NavbarVue from "@/components/Navbar.vue";

const students: Ref<Array<UserModel>> = ref([]);

const props = defineProps({
  page: {
    type: Number,
    required: true,
  },
});

const totalStudents = ref<number>(0);
const perPage = ref<number>(6);
const hasNextPage = computed(() => {
  const totalPages = Math.ceil(totalStudents.value / perPage.value);
  return props.page.valueOf() < totalPages;
});

nProgress.start();
getStudentsService(perPage.value, props.page)
  //@ts-ignore
  .then(res => {
    students.value = res.data;
    totalStudents.value = res.totalElements;
  })
  .catch(() => {
    router.push({ name: "network-error" });
  })
  .finally(() => {
    nProgress.done();
  });

onBeforeRouteUpdate((to, from, next) => {
  const toPage = Number(to.query.page);
  getStudentsService(perPage.value, toPage)
    //@ts-ignore
    .then((res: { data: StudentModel[]; total: number }) => {
      students.value = res.data;
      totalStudents.value = res.total;
      next();
    })
    .catch(() => {
      next({ name: "Network error" });
    });
});

function handlePrevPage() {
  if (props.page === 1) return;
  router.push({ name: "student-list", query: { page: props.page - 1 } });
}

function handleNextPage() {
  if (!hasNextPage.value) return;
  router.push({ name: "student-list", query: { page: props.page + 1 } });
}

function handleView(id: string) {
  router.push({ name: "student-detail", params: { id: id.toString() } });
}
</script>

<template>
  <NavbarVue/>
  <div class="flex flex-col h-full w-full sm:w-[60%]">
    <div class="grid grid-cols-1 sm:grid-cols-3 grid-rows-none sm:grid-rows-2 gap-y-8 sm:gap-y-0 sm:gap-x-4">
      <PersonCard
        v-for="student in students"
        :key="student.id"
        :person="(student as UserModel)"
        :handle-view="() => handleView(student.id)"
        type="student"
      />
    </div>
    <div class="flex items-center justify-between gap-2 mt-12 text-sm sm:text-lg">
      <button
        class="group relative px-4 py-2 overflow-hidden rounded-2xl bg-rose-400 text-xs sm:text-lg font-bold text-white"
        v-on:click="handlePrevPage"
      >
        Previous Page
        <div
          class="absolute inset-0 h-full w-full scale-0 rounded-2xl transition-all duration-300 group-hover:scale-100 group-hover:bg-white/30"
        ></div>
      </button>
      <div class="flex items-center gap-2">
        <span class="text-black">Page</span>
        <span class="text-black">{{ props.page }}</span>
        <span class="text-black">of</span>
        <span class="text-black">{{ Math.ceil(totalStudents / perPage) }}</span>
      </div>
      <button
        class="group relative px-4 py-2 overflow-hidden rounded-2xl bg-rose-400 text-xs sm:text-lg font-bold text-white"
        v-on:click="handleNextPage"
      >
        Next Page
        <div
          class="absolute inset-0 h-full w-full scale-0 rounded-2xl transition-all duration-300 group-hover:scale-100 group-hover:bg-white/30"
        ></div>
      </button>
    </div>
  </div>
</template>