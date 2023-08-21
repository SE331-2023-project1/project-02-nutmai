<script setup lang="ts">
import StudentCard from "@/components/StudentCard.vue";
import nProgress from "nprogress";
import { computed, ref, type Ref } from "vue";
import { getStudentsService } from "@/services/studentService";
import type { StudentModel } from "@/models/studentModel";
import router from "@/router";
import { onBeforeRouteUpdate } from "vue-router";

const students: Ref<Array<StudentModel>> = ref([]);

const props = defineProps({
  page: {
    type: Number,
    required: true,
  },
});

const totalStudents = ref<number>(0);
const perPage = ref<number>(6);
const hasNextPage = computed(() => {
  //first calculate the total page
  const totalPages = Math.ceil(totalStudents.value / perPage.value);
  return props.page.valueOf() < totalPages;
});

nProgress.start();
getStudentsService(perPage.value, props.page)
  //@ts-ignore
  .then((res: { data: StudentModel[]; total: number }) => {
    students.value = res.data;
    totalStudents.value = res.total;
  })
  .catch(() => {
    router.push({ name: "Network Error" });
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
</script>

<template>
  <div class="flex flex-col">
    <div class="grid grid-cols-3 grid-rows-2 gap-y-8 sm:gap-y-0 sm:gap-x-4 h-[400px] sm:h-[800px]">
      <StudentCard v-for="student in students" :key="student.id" :student="student" />
    </div>
    <div class="flex items-end justify-end gap-2 mr-8 mt-4 text-sm sm:text-lg">
      <RouterLink
        :to="{ name: 'student-list', query: { page: page - 1 } }"
        rel="prev"
        v-if="page != 1"
        >Prev Page</RouterLink
      >
      <RouterLink
        :to="{ name: 'student-list', query: { page: page + 1 } }"
        rel="next"
        v-if="hasNextPage"
        >Next Page</RouterLink
      >
    </div>
  </div>
</template>
