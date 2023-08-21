<script setup lang="ts">
import StudentCard from '@/components/StudentCard.vue';
import type { StudentModel } from '@/models/studentModel';
import { useStudentStore } from '@/stores/studentStore';
import { storeToRefs } from 'pinia';

const studentStore = useStudentStore();
const student = storeToRefs(studentStore).student

</script>

<template>
  <div class="flex">
    <StudentCard :student="(student as StudentModel)" />
    <table>
      <tr v-for="course in student?.courses">
        {{ course.id }}
        {{ course.name }}
      </tr>
    </table>
  </div>
</template>