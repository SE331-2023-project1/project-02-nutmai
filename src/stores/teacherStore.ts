import type { TeacherModel } from "@/models/teacherModel";
import { defineStore } from "pinia";

export const useTeacherStore = defineStore("teacher", {
  state: () => ({
    teacher: {} as TeacherModel,
  }),
  actions: {
    setTeacher(teacher: TeacherModel) {
      this.teacher = teacher;
    },
  },
});