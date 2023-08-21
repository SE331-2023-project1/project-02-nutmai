<script setup lang="ts">
import { type StudentModel } from "@/models/studentModel";
import router from "@/router";
import { type PropType } from "vue";
const props = defineProps({
  student: Object as PropType<StudentModel>,
});

function handleViewStudent(){
  console.log("CWQEQW")
  router.push({name: 'student-detail', params: {id: props.student?.id}})
}

</script>

<template>
  <div class="w-full h-full flex flex-col items-center" v-on:click="handleViewStudent">
    <div class="flex justify-center h-3/4 w-full">
      <img :src="student?.img" alt="" class="object-cover"/>
    </div>
    <div class="flex justify-start flex-col text-xs sm:text-sm">
      <div class="flex gap-2">
        <span class="font-bold">Name:</span><span>{{ student?.name }} {{ student?.surname }}</span>
      </div>
      <div class="flex gap-2">
        <span class="font-bold">Student ID:</span><span>{{ student?.id }}</span>
      </div>
      <div class="flex gap-2">
        <span class="font-bold">Register to:</span><span>{{ student?.courses.length }} courses</span>
      </div>
    </div>
  </div>
</template>
