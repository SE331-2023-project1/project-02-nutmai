<script setup lang="ts">
import PersonCard from "@/components/PersonCard.vue";
import type { StudentModel } from "@/models/studentModel";
import { useStore } from "@/stores";
import { getTeacherById } from "@/services/teacherService";
import type { PersonModel } from "@/models/personModel";
import CommentFormVue from "@/components/CommentForm.vue";
import { storeToRefs } from "pinia";

const { commentStore, studentStore, teacherStore } = useStore();
const student = storeToRefs(studentStore).getStudent;
const teacher = storeToRefs(teacherStore).teacher;
const comments = storeToRefs(commentStore).comments;
</script>

<template>
  <div class="flex sm:flex-row flex-col h-full sm:w-[60%]">
    <div class="flex flex-col sm:w-[30%] items-center">
      <div class="grid grid-cols-2 grid-rows-[10%_90%] h-full sm:flex sm:flex-col sm:h-1/2">
        <h4 class="sm:w-3/5 text-center sm:text-start self-center font-semibold mb-2">Student</h4>
        <h4 class="sm:hidden text-center self-center font-semibold mb-2">Advisor</h4>
        <div class="flex">
          <PersonCard :person="(student as PersonModel)" type="student" />
        </div>
        <div class="flex sm:hidden">
          <PersonCard :person="(teacher as PersonModel)" type="teacher" />
        </div>
      </div>
      <div class="sm:w-3/5 h-full sm:h-1/2">
        <h4 class="text-[15px] font-semibold">Comment:</h4>
        <div class="flex flex-col space-y-2">
          <div v-if="student" v-for="comment in comments" :key="comment.id" class="flex flex-col">
            <div class="flex flex-col px-1 border-solid border border-gray-800 relative">
              <span class="text-[12px] font-semibold whitespace-nowrap"
                >{{ comment.teacher.title }} {{ comment.teacher.name }} {{ comment.teacher.surname }}</span
              >
              <span class="text-[12px] font-semibold">{{ comment.date.toLocaleDateString() }}</span>
            </div>
            <span class="text-[12px] px-1 py-1 border-solid border border-gray-800 w-full">{{ comment.content }}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="sm:hidden px-8">
      <table class="min-w-full h-fit bg-white border border-solid border-gray-800">
        <thead class="bg-gray-800 text-white">
          <tr>
            <th class="w-[24%]text-center py-3 uppercase font-semibold text-sm">Course (#)</th>
            <th class="w-[24%] text-center py-3 uppercase font-semibold text-sm">Course ID</th>
            <th class="py-3 uppercase font-semibold text-sm w-[52%] text-center">Course Name</th>
          </tr>
        </thead>
        <tbody class="text-gray-700">
          <tr v-for="(course, index) in student?.courses" :key="course.id">
            <td class="text-center py-3 border border-solid border-gray-800">{{ index }}</td>
            <td class="text-center py-3 border border-solid border-gray-800">{{ course.id }}</td>
            <td class="py-3 text-center border border-solid border-gray-800">
              <a class="hover:text-blue-500">{{ course.name }}</a>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <CommentFormVue :studentID="student.id" v-if="student" />
    <div class="hidden sm:flex flex-col sm:w-[70%]">
      <div class="flex flex-col sm:w-[38%] h-2/5">
        <h4 class="w-3/5 self-center font-semibold mb-2">Advisor</h4>
        <PersonCard :person="(teacher as PersonModel)" type="teacher" />
      </div>
      <div class="h-3/5 pl-[56px] mt-8 hidden sm:flex">
        <table class="min-w-full h-fit bg-white border border-solid border-gray-800">
          <thead class="bg-gray-800 text-white">
            <tr>
              <th class="w-1/6 text-center py-3 uppercase font-semibold text-sm">Course (#)</th>
              <th class="w-1/6 text-center py-3 uppercase font-semibold text-sm">Course ID</th>
              <th class="py-3 uppercase font-semibold text-sm w-4/6 text-center">Course Name</th>
            </tr>
          </thead>
          <tbody class="text-gray-700">
            <tr v-for="(course, index) in student?.courses" :key="course.id">
              <td class="text-center py-3 border border-solid border-gray-800">{{ index }}</td>
              <td class="text-center py-3 border border-solid border-gray-800">{{ course.id }}</td>
              <td class="py-3 text-center border border-solid border-gray-800">
                <a class="hover:text-blue-500">{{ course.name }}</a>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
