<script setup lang="ts">
import type { UserModel, StudentModel, TeacherModel } from "@/models/schoolModel";
import router from "@/router";
import { type PropType } from "vue";

const props = defineProps({
  person: Object as PropType<StudentModel | TeacherModel>,
  handleView: Function as PropType<() => void>,
  type: String as PropType<"student" | "teacher">,
});

</script>

<template>
  <div class="w-full h-full flex flex-col items-center" >
    <div class="flex justify-center h-3/4 w-3/5 cursor-pointer" v-on:click="handleView">
      <img :src="person?.img" alt="" class="w-full h-full object-cover"/>
    </div>
    <div class="flex justify-start flex-col text-xs sm:text-sm w-3/5 mt-2">
      <div class="flex gap-2">
        <span class="font-bold" v-if="type === 'student'">Name:</span>
        <span class="font-bold" v-if="type === 'teacher'">{{ person?.title }}</span>
        <span>{{ person?.name }} {{ person?.surname }}</span>
      </div>
      <div class="flex gap-2" v-if="type === 'student'">
        <span class="font-bold">Student ID:</span><span>{{ person?.id }}</span>
      </div>
      <div class="flex gap-2" v-if="type === 'teacher'">
        <span class="font-bold">Teacher ID:</span><span>{{ person?.id }}</span>
      </div>
      <div class="flex gap-2">
        <span class="font-bold" v-if="type === 'student'">Register to:</span>
        <span class="font-bold" v-if="type === 'teacher'">Teaching in:</span>
        <span>{{ person?.courses.length }} courses</span>
      </div>
    </div>
  </div>
</template>